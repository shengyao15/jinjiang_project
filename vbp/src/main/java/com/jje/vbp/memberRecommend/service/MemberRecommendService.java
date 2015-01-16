package com.jje.vbp.memberRecommend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.jje.data.util.RecommendStatic;
import com.jje.dto.Pagination;
import com.jje.dto.coupon.issue.CouponSysIssueResult;
import com.jje.dto.coupon.issue.CouponSysIssueResult.ResponseMessage;
import com.jje.dto.coupon.rule.issue.register.AcitivityTagEnum;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.membercenter.recommend.MemberRecommendDto;
import com.jje.dto.membercenter.recommend.MemberRecommendOrderDto;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.vbp.handler.CbpHandler;
import com.jje.vbp.memberRecommend.domain.MemberRecommendCoupon;
import com.jje.vbp.memberRecommend.domain.MemberRecommendDomain;
import com.jje.vbp.memberRecommend.domain.MemberRecommendOrderDomain;
import com.jje.vbp.memberRecommend.persistence.MemberRecommendMapper;
import com.jje.vbp.memberRecommend.persistence.MemberRecommendOrderMapper;

@Service
public class MemberRecommendService {

	private static final Logger logger = LoggerFactory.getLogger(MemberRecommendService.class);

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private CbpHandler cbpHandler;

	@Autowired
	private MemberRecommendMapper memberRecommendMapper;

	@Autowired
	private MemberRecommendOrderMapper memberRecommendOrderMapper;

	public void insertRecommend(MemberRecommendDomain recommend) {
		memberRecommendMapper.insert(recommend);
	}

	public List<MemberRecommendDto> getRecommend(String recommendId, int recommendTime, String type) {
		List<MemberRecommendDomain> recList = new ArrayList<MemberRecommendDomain>();
		List<MemberRecommendDto> resList = new ArrayList<MemberRecommendDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("recommenderId", recommendId);
		map.put("recommendTimes", recommendTime);
		map.put("campaign", type);
		recList = memberRecommendMapper.queryTopNByRecommenderId(map);
		for (MemberRecommendDomain domain : recList) {
			resList.add(domain.toDto());
		}
		return resList;
	}

	public List<MemberRecommendOrderDto> getRecommendOrder(String memberId) {
		List<MemberRecommendOrderDomain> recList = new ArrayList<MemberRecommendOrderDomain>();
		List<MemberRecommendOrderDto> resList = new ArrayList<MemberRecommendOrderDto>();
		recList = memberRecommendOrderMapper.queryByMemberId(memberId);
		for (MemberRecommendOrderDomain domain : recList) {
			resList.add(domain.toDto());
		}
		return resList;
	}

	public ResponseMessage recommendRegisterCoupon(int num) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("num", num);
		params.put("campaign", RecommendStatic.RECOMMEND_CAMPAIGN);
		try {
			List<MemberRecommendDomain> memberRecommends = memberRecommendMapper.queryRecommendRegisterCoupon(params);
			for (MemberRecommendDomain recomm : memberRecommends) {
				try {
					String memberId = recomm.getRecommenderId();
					if( StringUtils.isEmpty(memberId)){
						logger.info("recommendRegisterCouponIssue memberId is isEmpty"  );
						continue;
					}
					String mcMemberCode = memberRepository.getMcMemberCodeByMemberId(memberId);
					if(StringUtils.isEmpty(mcMemberCode)){
						logger.info("recommendRegisterCouponIssue mcMemberCode is isEmpty " + memberId );
						continue;
					}
					CouponSysIssueResult issueResult = cbpHandler.recommendRegisterIssue(RegistChannel.Website.name(), mcMemberCode, AcitivityTagEnum.ACTIVITY_1);
					logger.info("recommendRegisterCouponIssue issueResult :" + issueResult == null ? " null " : issueResult.getResponseMessage().getMessage());
					// 优惠券发送成功
					if (issueResult != null && ResponseMessage.SUCCESS.getCode().equals(issueResult.getResponseMessage().getCode())) {
						// 更新发送记录
						MemberRecommendCoupon memberRecommendCoupon = new MemberRecommendCoupon(memberId);
						memberRecommendCoupon.setCampaign(RecommendStatic.RECOMMEND_CAMPAIGN);
						memberRecommendMapper.insertMemberRecommendCoupon(memberRecommendCoupon);
					}
				} catch (Exception e) {
					logger.error("recommendRegisterCouponIssue RecommenderId"+ recomm.getRecommenderId()+"  error :" + e );
					continue;
				}
			}
		} catch (Exception e) {
			logger.error("recommendRegisterCoupon error :" + e);
		}
		return ResponseMessage.SUCCESS;
	}
}
