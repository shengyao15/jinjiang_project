package com.jje.membercenter.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.member.score.MemberScoreDto;
import com.jje.dto.member.score.QueryScoreDto;
import com.jje.dto.member.score.ScoreLogDto;
import com.jje.dto.member.taobao.TaobaoPointsRedeemInDto;
import com.jje.dto.nbp.SendType;
import com.jje.dto.nbp.TemplateDto;
import com.jje.membercenter.crm.CRMOperationProxy;
import com.jje.membercenter.domain.CRMOperationRespository;
import com.jje.membercenter.xsd.ChannelScoreRequest;
import com.jje.membercenter.xsd.ChannelScoreResponse;
import com.jje.membercenter.xsd.MemberScoreRequest;
import com.jje.membercenter.xsd.MemberScoreResponse;
import com.jje.membercenter.xsd.TaobaoQueryScoreRequest;
import com.jje.membercenter.xsd.TaobaoQueryScoreResponse;
import com.jje.membercenter.xsd.TaobaoRedeemRequest;
import com.jje.membercenter.xsd.TaobaoRedeemResponse;
import com.jje.membercenter.xsd.TemplateRequest;
import com.jje.membercenter.xsd.TemplateResponse;
import com.jje.vbp.taobao.domain.TaobaoRepository;

@Component
public class CRMOperationRespositoryImpl implements CRMOperationRespository
{
	
	@Autowired
	CRMOperationProxy crmOperationProxy;
	
	@Autowired
	private TaobaoRepository taobaoRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 public String queryChannelScore(String orderNum){
	    	
	    	ChannelScoreRequest request = new ChannelScoreRequest();
	    	ChannelScoreResponse response = null;
	    	ChannelScoreRequest.Head head = new ChannelScoreRequest.Head();
	    	ChannelScoreRequest.Body body = new ChannelScoreRequest.Body();
	    	
	    	head.setTranscode("40012");
			head.setSystype("JJ000");
			head.setReqtime(new String(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
			
			body.setOrdernum(orderNum);
			
			request.setHead(head);
			request.setBody(body);
			
			String retMsg = "";
			String retCode = "";
			try {
				response = crmOperationProxy.getChannelScore(request);
				
				retCode = response.getHead().getRetcode();
				
				if("00001".equals(retCode)){
					retMsg = "OK";
				}else{
					retMsg = response.getHead().getRetmsg();
				}
				
				logger.info("orderNum: "+ orderNum +" retCode: " + retCode + " retMsg: " + retMsg);
				
			} catch (Exception e) {
				logger.error("queryChannelScore({}) error!", JaxbUtils.convertToXmlString(request), e);
			}
			
			return retMsg;
	    	
	    }
	 
	public MemberScoreDto queryScore(QueryScoreDto queryScoreDto) throws Exception
	{
		MemberScoreRequest request = new MemberScoreRequest();
		MemberScoreResponse response = new MemberScoreResponse();
		MemberScoreRequest.Head head = new MemberScoreRequest.Head();
		MemberScoreRequest.Body body = new MemberScoreRequest.Body();
		head.setReqtime(new SimpleDateFormat("MMddyyyy").format(new Date()));
		head.setTranscode("20011");
		head.setSystype("JJ000");
		//body.setMembid(queryScoreDto.getMemberId());
		body.setStartdate(new SimpleDateFormat("MM/dd/yyyy").format(queryScoreDto.getStartDate()));
		body.setEnddate(new SimpleDateFormat("MM/dd/yyyy").format(queryScoreDto.getEndDate()));
		body.setPointtype(queryScoreDto.getScoreType());
		body.setPartnername(queryScoreDto.getConsumeSource());
		request.setHead(head);
		request.setBody(body);
		response = crmOperationProxy.findScoreLog(request);
		MemberScoreDto dto = new MemberScoreDto();
		List<ScoreLogDto> list = new ArrayList<ScoreLogDto>();
		if (response.getBody()!=null &&response.getBody().getListofloytransaction()!=null)
		{
			for (MemberScoreResponse.Body.Listofloytransaction.Loytransaction loytransaction : response.getBody().getListofloytransaction().getLoytransaction())
			{
				ScoreLogDto sDto = new ScoreLogDto();
				sDto.setConsumeDate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse((loytransaction.getTransactiondate().toString())));
				sDto.setConsumeType(loytransaction.getTransactiontype());
				sDto.setScore(Long.parseLong(loytransaction.getPoints()));
//				sDto.setTotalScore(Long.parseLong(loytransaction.getPointtotal()));
				list.add(sDto);
			}
		}
		dto.setScoreHistory(list);
		return dto;
	}

	@Override
	public boolean syncTemplateToCrm(TemplateDto dto) throws Exception {
		TemplateRequest request = new TemplateRequest();
		TemplateResponse response = new TemplateResponse();
		TemplateRequest.Head head = new TemplateRequest.Head();
		TemplateRequest.Body body = new TemplateRequest.Body();
		TemplateRequest.Body.Record record = new TemplateRequest.Body.Record();
		
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode("30023");
		head.setSystype("JJ000");
		
		record.setName(dto.getTemplateNo());
		record.setSerialnum(dto.getSerialNumber());
		record.setSubjecttext(dto.getSubject());
		record.setTemplatetext(dto.getJsonContent());
		record.setSentTime(new SimpleDateFormat("yyyyMMdd").format(dto.getStartDate())+dto.getStartHour()+dto.getStartMinute());
		record.setAction(dto.getAction());
		if(dto.getIsBill()){
			record.setBillflg("Y");
		}else{
			record.setBillflg("N");
		}
		if(SendType.PRODUCT.equals(dto.getSendType())){
			record.setSendtype("Product");
		}else if(SendType.SALE.equals(dto.getSendType())){
			record.setSendtype("Sales");
		}
		if("MAIL".equals(dto.getTemplateType().name())){
			record.setMediatype("Email");
		}else{
			record.setMediatype(dto.getTemplateType().name());
		}
		record.setComments("");
		
		body.setRecord(record);
		request.setHead(head);
		request.setBody(body);
		logger.warn("syncTemplateToCrm  send crm  Request---> requestInfo =" + JaxbUtils.convertToXmlString(request));
		response = crmOperationProxy.SyncTemplate(request);
		logger.warn("syncTemplateToCrm  send crm  Response---> requestInfo =" + JaxbUtils.convertToXmlString(response));
		if (response.getBody()!=null && "00001".equals(response.getBody().getRecode()) ){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	 public TaobaoQueryScoreResponse queryTaobaoScore(String membid){
	    	
	    	TaobaoQueryScoreRequest request = new TaobaoQueryScoreRequest();
	    	TaobaoQueryScoreResponse response = null;
	    	TaobaoQueryScoreRequest.Head head = new TaobaoQueryScoreRequest.Head();
	    	TaobaoQueryScoreRequest.Body body = new TaobaoQueryScoreRequest.Body();
	    	
	    	head.setTranscode("28001");
			head.setSystype("JJ000");
			head.setReqtime(new String(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
			
			body.setMembid(membid);
			
			request.setHead(head);
			request.setBody(body);
			
			try {
				response = crmOperationProxy.getTaobaoScore(request);
				
			} catch (Exception e) {
				logger.error("queryTaobaoScore({}) error!", JaxbUtils.convertToXmlString(request), e);
			}
			
			return response;
	    	
	    }
	 
	 
	@Override
	public TaobaoRedeemResponse redeem(TaobaoPointsRedeemInDto inDto, String source) {
		String crmID = inDto.getUserID();
		
		String type = inDto.getType();
		
		//根据taobao的userID 在mapping表中找到membid
		String membid = crmID;
		String productname = "";
		String redeemproduct = "";
		if("1".equals(type)){
			productname = "POINTS_TO_MILES";
			redeemproduct = "电商积分兑换1000淘宝里程";
		}else if("2".equals(type)){
			productname = "MILES_TO_POINTS";
			redeemproduct = "淘宝里程兑换1000积分";
		}
		
		String points = String.valueOf(inDto.getRedeemPoints());
		
		TaobaoRedeemRequest request = new TaobaoRedeemRequest();
		TaobaoRedeemResponse response = null;
    	TaobaoRedeemRequest.Head head = new TaobaoRedeemRequest.Head();
    	TaobaoRedeemRequest.Body body = new TaobaoRedeemRequest.Body();
    	
    	head.setTranscode("18001");
		head.setSystype("JJ000");
		head.setReqtime(new String(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
		
		body.setMembid(membid);
		body.setProductname(productname);
		body.setPoints(points);
		body.setRedeemproduct(redeemproduct);
		body.setSource(source);
		
		request.setHead(head);
		request.setBody(body);
		
		try {
			response = crmOperationProxy.redeemTaobaoScore(request);
			
		} catch (Exception e) {
			logger.error("redeem({}) error!", JaxbUtils.convertToXmlString(request), e);
		}
		
		return response;
		
	}
	
}
