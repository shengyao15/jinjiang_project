package com.jje.membercenter.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.membercenter.VipCardInfoDto;
import com.jje.dto.membercenter.ViphistoryDto;
import com.jje.membercenter.crm.CRMUpdateRightCardProxy;
import com.jje.membercenter.domain.CRMUpdateRightCardRepository;
import com.jje.membercenter.xsd.QueryRightCardInfoRequest;
import com.jje.membercenter.xsd.QueryRightCardInfoResponse;
import com.jje.membercenter.xsd.QueryRightCardInfoResponse.Body.Rightcard;
import com.jje.membercenter.xsd.QueryRightCardInfoResponse.Body.Rightcard.Listofhistory.Viphistory;

@Component
@Transactional
public class CRMUpdateRightCardRepositoryImpl implements CRMUpdateRightCardRepository
{

	@Autowired
	CRMUpdateRightCardProxy crmUpdateRightCardProxy;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<VipCardInfoDto> queryVIPCardInfo(String memberId) throws Exception
	{
		List<VipCardInfoDto> results = new ArrayList<VipCardInfoDto>();
		QueryRightCardInfoRequest request = new QueryRightCardInfoRequest();
		QueryRightCardInfoResponse response = new QueryRightCardInfoResponse();
		QueryRightCardInfoRequest.Head head = new QueryRightCardInfoRequest.Head();
		QueryRightCardInfoRequest.Body body = new QueryRightCardInfoRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode("20010");
		head.setSystype("JJ000");
		body.setMembid(memberId);
		request.setHead(head);
		request.setBody(body);
		try
		{
			response = crmUpdateRightCardProxy.queryVIPCardInfo(request);
			List<Rightcard> listRecords = response.getBody().getRightcard();
			for (int i = 0; i < listRecords.size(); i++)
			{
				VipCardInfoDto dto = new VipCardInfoDto();
				Rightcard rightcard = listRecords.get(i);
				dto.setMembid(rightcard.getMembid());
				dto.setMemcardno(rightcard.getMemcardno());
				dto.setJjmemcardno(rightcard.getJjmemcardno());
				dto.setMembcdtype(rightcard.getMembcdtype());
				// 日期字符取10位,保证取出的字符不包含时间
				if (rightcard.getStartdate() != null && !rightcard.getStartdate().equals(""))
				{
					dto.setStartdate(rightcard.getStartdate().substring(0, 10));
				}
				if (rightcard.getEnddate() != null && !rightcard.getEnddate().equals(""))
				{
					dto.setEnddate(rightcard.getEnddate().substring(0, 10));
				}
				dto.setMembcdsour(rightcard.getMembcdsour());
				dto.setMembcdstat(rightcard.getMembcdstat());
				List<ViphistoryDto> listofhistoryDto = new ArrayList<ViphistoryDto>();
				if (rightcard.getListofhistory() != null && rightcard.getListofhistory().getViphistory() != null)
				{
					for (int j = 0; j < rightcard.getListofhistory().getViphistory().size(); j++)
					{
						ViphistoryDto viphistoryDto = new ViphistoryDto();
						Viphistory viphistory = rightcard.getListofhistory().getViphistory().get(j);
						viphistoryDto.setCdoptype(viphistory.getCdoptype());
						viphistoryDto.setCdbuydt(viphistory.getCdbuydt());
						viphistoryDto.setCdsalechnl(viphistory.getCdsalechnl());
						viphistoryDto.setSalesamount(viphistory.getSalesamount());
						listofhistoryDto.add(viphistoryDto);
					}
				}
				dto.setListofhistory(listofhistoryDto);
				results.add(dto);
			}
		}
		catch (Exception e)
		{
			logger.error("调用权益卡查询接口出错",e);
			throw e;
		}
		return results;
	}

}
