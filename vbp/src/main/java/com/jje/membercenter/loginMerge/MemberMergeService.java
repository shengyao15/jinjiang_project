package com.jje.membercenter.loginMerge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.common.utils.SHA1Utils;
import com.jje.dto.membercenter.MemberDegree;
import com.jje.dto.vbp.sns.ThirdpartyBindType;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberVerfy;
import com.jje.membercenter.persistence.MemberMapper;
import com.jje.vbp.memberCardLvChanelRel.service.MemberCardLvChanelRelService;

@Component
public class MemberMergeService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private MemberCardLvChanelRelService memberCardLvChanelRelService;
	
	
	public Member queryMemberByLoginName1(String usernameOrCellphoneOrEmail) {
		Member member = memberMapper.queryByUsernameOrCellphoneOrEmail(usernameOrCellphoneOrEmail);
        if(member == null){
            return member;
        }
        member.addChannels(getMemberLevelRateChannels(member.getNewMemberHierarchy()));
		return member;
	}

	public Member queryMemberByLoginName2(String usernameOrCellphoneOrEmail) {
		Member member = memberMapper.queryMemberByLoginName2(usernameOrCellphoneOrEmail);
        if(member == null){
            return member;
        }
        member.addChannels(getMemberLevelRateChannels(member.getNewMemberHierarchy()));
		return member;
	}
	
	public List<Member> queryMemberByLoginName3(String usernameOrCellphoneOrEmail) {
		List<Member> list = memberMapper.queryMemberByLoginName3(usernameOrCellphoneOrEmail);
		return list;
	}
	
	private List<String> getMemberLevelRateChannels(String memberHierarchy) {
        MemberDegree md = MemberDegree.getMemberDegree(memberHierarchy);
        if(md == null){
            return new ArrayList<String>();
        }
        return memberCardLvChanelRelService.queryChannelByKey("Channel_"+md.toString());
    }
	
	public boolean updateVerifyStatus(String userName, String pwd) {
		
		//  查询出v2表中的对应的infoID  已有
		long infoID;
		List<MemberVerfy> list = memberMapper.queryRegisterByCellphoneOrEmailOrCardNo2(userName);
		if(list!=null && list.size()>0){
			infoID = list.get(0).getMemInfoId();
		}else{
			return false;
		}
		
		//  通过infoID 更新v2表中的状态     新建
		int updateFlag = memberMapper.updateMemberVerifyMemNameByLoginName(infoID);
		if(updateFlag==0){
			return false;
		}
		
		
		//  通过infoID 查询出所有的v2表中的记录 list 已有
		List<MemberVerfy> list2 = memberMapper.queryVerifyInfoByInfoId2(infoID);
		
		//  通过list 插入v1表
		for (MemberVerfy memberVerfy : list2) {
			memberVerfy.setPassword(pwd);
			memberMapper.insertMemberVerify(memberVerfy);
		}
		return true;
	}

	public void prepareData1(String loginName, String password) {
		
		Random r = new Random();
		int num = 5000+r.nextInt(999);
		String s = String.valueOf(num);
		
		Member member = new Member();
		member.setActivateCode("Activiated");
		member.setActiveChannel("Website");
		member.setActiveDate(new Date());
		member.setCardLevel("1");
		member.setCardNo(loginName+"card");
		member.setCellPhone(loginName);
		member.setEmail(loginName+"@qq.com");
		member.setFullName("test");
		member.setIdentityNo(s);
		member.setIdentityType("ID");
		member.setMcMemberCode(s);
		member.setMemberCode(s);
		member.setMemberID(s);
		member.setMemberHierarchy("1");
		member.setStatus("Active");
		member.setCardLevel("1");
		member.setThirdpartyType(ThirdpartyBindType.TENPAY.name());
		
		memberMapper.insertMemberInfo(member);
		
		member.getId();
		
		MemberVerfy v = new MemberVerfy();
		v.setMemId(s);
		v.setMemInfoId(member.getId());
		v.setMemNum(s);
		v.setMenName(loginName);
		v.setPassword(SHA1Utils.digestSha1JJ(password));

		memberMapper.insertMemberVerifyJJZX(v);
		
	}
	
	
public void prepareData2(String loginName, String password) {
		
		Random r = new Random();
		int num = 5000+r.nextInt(999);
		String s = String.valueOf(num);
		
		Member member = new Member();
		member.setActivateCode("Activiated");
		member.setActiveChannel("Website");
		member.setActiveDate(new Date());
		member.setCardLevel("1");
		member.setCardNo(loginName+"card");
		member.setCellPhone(loginName);
		member.setEmail(loginName+"@qq.com");
		member.setFullName("test");
		member.setIdentityNo(s);
		member.setIdentityType("ID");
		member.setMcMemberCode(s);
		member.setMemberCode(s);
		member.setMemberID(s);
		member.setMemberHierarchy("1");
		member.setStatus("Active");
		member.setCardLevel("1");
		member.setThirdpartyType(ThirdpartyBindType.TENPAY.name());
		
		memberMapper.insertMemberInfo(member);
		
		member.getId();
		
		MemberVerfy v = new MemberVerfy();
		v.setMemId(s);
		v.setMemInfoId(member.getId());
		v.setMemNum(s);
		v.setMenName(loginName);
		v.setPassword(SHA1Utils.digestSha1JJ(password));

		memberMapper.insertMemberVerifyJJZXConflict(v);
		
	}
	
	
}
