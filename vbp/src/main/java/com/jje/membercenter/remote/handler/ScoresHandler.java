package com.jje.membercenter.remote.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.common.utils.DateUtils;
import com.jje.common.utils.JaxbUtils;
import com.jje.dto.member.score.MemberScoreDto;
import com.jje.dto.member.score.PartnerType;
import com.jje.dto.member.score.QueryScoreDto;
import com.jje.dto.member.score.ScoreLogDto;
import com.jje.dto.membercenter.ConsumeType;
import com.jje.dto.membercenter.score.CancelTradeAnswerDto;
import com.jje.dto.membercenter.score.CancelTradeReceiverDto;
import com.jje.dto.membercenter.score.RedeemStatus;
import com.jje.dto.membercenter.score.ScoreAnswerDto;
import com.jje.dto.membercenter.score.ScoreFillUpDto;
import com.jje.dto.membercenter.score.ScoreFillUpType;
import com.jje.dto.membercenter.score.ScoreReceiverDto;
import com.jje.dto.membercenter.score.ScoreRedeemDto;
import com.jje.membercenter.remote.crm.datagram.request.CancelTradeReq;
import com.jje.membercenter.remote.crm.datagram.request.GetScoresHistoryReq;
import com.jje.membercenter.remote.crm.datagram.request.RegisterScoresTradeReq;
import com.jje.membercenter.remote.crm.datagram.request.ScoreFillUpReq;
import com.jje.membercenter.remote.crm.datagram.request.ScoreRedeemRequest;
import com.jje.membercenter.remote.crm.datagram.request.ScoresTradeReq;
import com.jje.membercenter.remote.crm.datagram.response.CancelTradeRes;
import com.jje.membercenter.remote.crm.datagram.response.GetScoresHistoryRes;
import com.jje.membercenter.remote.crm.datagram.response.RegisterScoresTradeRes;
import com.jje.membercenter.remote.crm.datagram.response.ScoreFillUpRes;
import com.jje.membercenter.remote.crm.datagram.response.ScoresRedeemResponse;
import com.jje.membercenter.remote.crm.datagram.response.ScoresTradeRes;
import com.jje.membercenter.remote.crm.support.CrmPassage;
import com.jje.membercenter.remote.crm.support.CrmResponse;
import com.jje.membercenter.remote.crm.support.CrmSrType;
import com.jje.membercenter.remote.vo.ScoreRedeemVO;
import com.jje.membercenter.remote.vo.ScoreTransactionVO;
import com.jje.membercenter.score.domain.ScoreFillUp;
import com.jje.membercenter.score.domain.ScoreFillUpResult;
import com.jje.membercenter.service.MemberService;

@Component
public class ScoresHandler {

	public final String SUCCESS_STATUS = "00001";

	@Autowired
	private MemberService memberService;

	@Autowired
	private CrmPassage crmPassage;

	public MemberScoreDto getScoresHistory(QueryScoreDto queryDto) throws Exception {
		if (!queryDto.checkQueryScoreDtoLegal())
			throw new RequiredParamsNullException(" getScoresHistory required Params is Null ,info:["
					+ JaxbUtils.convertToXmlString(queryDto) + "]");
		GetScoresHistoryReq scoreHistoryRequest = getScoreHistoryRequest(queryDto);
		GetScoresHistoryRes response = crmPassage.sendToType(scoreHistoryRequest, GetScoresHistoryRes.class);
		if (response.getHead() != null && response.isStatus(CrmResponse.Status.ERROR)){
			throw new Exception("Call crm happen exception\n"+ JaxbUtils.convertToXmlString(response));
		}
		MemberScoreDto dto = new MemberScoreDto();
		List<ScoreLogDto> list = new ArrayList<ScoreLogDto>();
		if (response.getBody() != null && response.getBody().getListofloytransaction() != null) {
			for (ScoreTransactionVO loytransaction : response.getBody().getListofloytransaction().getLoytransaction()) {
				list.add(getScoreLog(loytransaction));
			}
		}
		dto.setScoreHistory(list);
		return dto;
	}

	private ScoreLogDto getScoreLog(ScoreTransactionVO loytransaction) throws Exception {
		ScoreLogDto sDto = new ScoreLogDto();
		sDto.setStartDate(DateUtils.parseDate(loytransaction.getStartdate(), "MM/dd/yyyy"));
		sDto.setEndDate(DateUtils.parseDate(loytransaction.getEnddate(), "MM/dd/yyyy"));
		if (StringUtils.isNotBlank(loytransaction.getTransactiondate()))
			sDto.setConsumeDate(DateUtils.parseDate(loytransaction.getTransactiondate(), "MM/dd/yyyy HH:mm:ss"));
		sDto.setConsumeType(loytransaction.getTransactiontype());
		if (getLoyPoint(loytransaction) != null)
			sDto.setScore(Long.parseLong(getLoyPoint(loytransaction)));
		sDto.setProductName(loytransaction.getProductname());
		
		sDto.setHotelId(loytransaction.getHotelid());
		sDto.setHotelName(loytransaction.getHotelname());
		sDto.setOrderNumber(loytransaction.getOrdernumber());
		sDto.setTravelAgency(loytransaction.getTravelagency());
		sDto.setGroupCode(loytransaction.getGroupcode());
		sDto.setPartnerName(loytransaction.getPartnername());
		
		if("星级酒店".equals(loytransaction.getPartnername()) || "百时快捷".equals(loytransaction.getPartnername()) || "锦江之星".equals(loytransaction.getPartnername())) {
    		sDto.setPartnerType(PartnerType.PARTENER_HOTEL);
    	} else if("锦江旅游".equals(loytransaction.getPartnername())) {
    		sDto.setPartnerType(PartnerType.PARTENER_TRAVEL);
    	} else {
    		sDto.setPartnerType(PartnerType.PARTENER_ELSE);
    	}
		
		return sDto;
	}

	private String getLoyPoint(ScoreTransactionVO loytransaction) throws Exception {
		ConsumeType type = ConsumeType.getInstance(loytransaction.getTransactiontype());
		if (type != null) {
			Method[] methods = ScoreTransactionVO.class.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equalsIgnoreCase("get" + type.getAttName())) {
					return (method.invoke(loytransaction) == null || StringUtils.isEmpty(method.invoke(loytransaction)
							.toString())) ? "0" : (String) method.invoke(loytransaction);
				}
			}
		}
		return "0";
	}


	private GetScoresHistoryReq getScoreHistoryRequest(QueryScoreDto queryDto) {
		GetScoresHistoryReq req = new GetScoresHistoryReq();
		if (StringUtils.isNotBlank(queryDto.getMcMemberCode())) {
			//String memberId= memberService.getMemberCodeByMcMemberCode(queryDto.getMcMemberCode());
			String memberId= memberService.getMemberIDByMcMemberCode(queryDto.getMcMemberCode());
			queryDto.setMemberId(memberId);
		}else if (StringUtils.isNotBlank(queryDto.getMemberId())){
			queryDto.setMemberId(queryDto.getMemberId());
		}
		req.getBody().setMembid(queryDto.getMemberId());
		req.getBody().setStartdate(DateUtils.formatDate(queryDto.getStartDate(), "MM/dd/yyyy"));
		req.getBody().setEnddate(DateUtils.formatDate(queryDto.getEndDate(), "MM/dd/yyyy") + " 23:59:59");
		req.getBody().setPointtype(queryDto.getScoreType());
		req.getBody().setPartnername(queryDto.getConsumeSource());
		req.getBody().setProductname(queryDto.getProductName());
		return req;
	}

	public ScoreFillUpResult scoreFillUp(ScoreFillUp score) throws Exception {
		if (!score.checkScoreFillUpLegal())
			throw new RequiredParamsNullException(" scoreFillUp required Params is Null ,info:[detail :"
					+ score.getDetail() + ",mc:" + score.getMcMemberCode() + "]");
		ScoreFillUpRes response = crmPassage.sendToType(getScoreFillUpCRMRequest(score), ScoreFillUpRes.class);
		return toResult(response);
	}

	private ScoreFillUpReq getScoreFillUpCRMRequest(ScoreFillUp score) {
		ScoreFillUpReq request = new ScoreFillUpReq();
		request.setBody(getRequestBody(score));
		return request;
	}

	private ScoreFillUpReq.RequestBody getRequestBody(ScoreFillUp score) {
		ScoreFillUpReq.RequestBody body = new ScoreFillUpReq.RequestBody();
		if(!StringUtils.isEmpty(score.getMcMemberCode())){
			body.setMembid(memberService.getMemberIDByMcMemberCode(score.getMcMemberCode()));
		} else{
			body.setMembid(score.getMemberId());
		}
		body.setSrtype(CrmSrType.SCORE_FILL_UP.getCode()); // 积分补登
//		body.setSrdetail(score.getDetail()); 
		setScoreFillUpReqBody(body, score);
		return body;
	}
	
	private void setScoreFillUpReqBody(ScoreFillUpReq.RequestBody body, ScoreFillUp score) {
		ScoreFillUpType type = score.getBusinessType();
    	body.setType(type);
    	// 酒店
		if(type.equals(ScoreFillUpType.HOTEL)) {
	    	body.setCity(score.getCheckInCity());
	    	body.setRetailername(score.getHotelName());
	    	body.setRoom(score.getRoomNo());
	    	body.setStartdate(DateUtils.formatDate(score.getCheckInTime(), "yyyy/MM/dd"));
	    	body.setEnddate(DateUtils.formatDate(score.getCheckOutTime(), "yyyy/MM/dd"));
	    	body.setAmount(score.getAmount());
	    	body.setOrdernumber(score.getOrderNo());
	    	body.setInvoicenumber(score.getInvoiceNo());
		}
		// 旅游
		else if(type.equals(ScoreFillUpType.TRAVEL)) {
			body.setRetailername(score.getStoreName());
			body.setBuydate(DateUtils.formatDate(score.getPayTime(), "yyyy/MM/dd"));
			body.setLineName(score.getLineName());
			body.setGroupnum(score.getGroupCode());
			body.setStartdate(DateUtils.formatDate(score.getDepartDate(), "yyyy/MM/dd"));
			body.setEnddate(DateUtils.formatDate(score.getReturnDate(), "yyyy/MM/dd"));
	    	body.setAmount(score.getAmount());
	    	body.setOrdernumber(score.getOrderNo());
	    	body.setInvoicenumber(score.getInvoiceNo());
		}
		// 租车 
		else if(type.equals(ScoreFillUpType.AUTO)) {
			body.setBuydate(DateUtils.formatDate(score.getPayTime(), "yyyy/MM/dd"));
			body.setRetailername(score.getStoreName());
			body.setStartdate(DateUtils.formatDate(score.getCarStartTime(), "yyyy/MM/dd"));
			body.setEnddate(DateUtils.formatDate(score.getCarEndTime(), "yyyy/MM/dd"));
			body.setAmount(score.getAmount());
	    	body.setOrdernumber(score.getOrderNo());
	    	body.setInvoicenumber(score.getInvoiceNo());
		}
	}

	private ScoreFillUpResult toResult(ScoreFillUpRes response) {
		ScoreFillUpResult result = new ScoreFillUpResult();
		result.fromSoreFillUpCRMResponse(response);
		return result;
	}

	public ScoreAnswerDto trade(ScoreReceiverDto receiverDto) throws Exception {
		if (!receiverDto.checkScoreReceiverDtoLegal()) {
			throw new RequiredParamsNullException(" scoreTrade required Params is Null ,info:["
					+ JaxbUtils.convertToXmlString(receiverDto) + "]");
		}
		return wrapScoreAnswerFromRes(crmPassage.sendToType(getScoresTradeReq(receiverDto), ScoresTradeRes.class));
	}

	private ScoreAnswerDto wrapScoreAnswerFromRes(ScoresTradeRes res) {
		ScoreAnswerDto answerDto = new ScoreAnswerDto();
		answerDto.setMcMemberCode(memberService.getMemberByMemberNum(res.getBody().getMembid()).getMcMemberCode());
		answerDto.setRecode(res.getHead().getRetcode());
		answerDto.setRemainpoint(res.getBody().getRemainpoint());
		answerDto.setRemsg(res.getHead().getRetmsg());
		answerDto.setTransid(res.getBody().getTransid());
		return answerDto;
	}

	private ScoresTradeReq getScoresTradeReq(ScoreReceiverDto receiverDto) {
		ScoresTradeReq req = new ScoresTradeReq();
		req.getBody().setMembid(memberService.getMemberCodeByMcMemberCode(receiverDto.getMcMemberCode()));
		req.getBody().setPoints(receiverDto.getPoints());
		req.getBody().setRedeemproduct(receiverDto.getRedeemproduct());
		if (StringUtils.isNotEmpty(receiverDto.getTranstype()))
			req.getBody().setTranstype(receiverDto.getTranstype());
		req.getBody().setTransdate(DateUtils.formatDate(receiverDto.getTransdate(), "MM/dd/yyyy"));
		req.getBody().setProductname(receiverDto.getProductname());
		req.getBody().setOrdernumber(receiverDto.getOrderNO());
		req.getBody().setSource(receiverDto.getOrderOriginPart());
		return req;
	}

	public CancelTradeAnswerDto cancelTrade(CancelTradeReceiverDto receiver) throws Exception {
		if (!receiver.checkCancelTradeRequestRequiredParamsExists())
			throw new RequiredParamsNullException(" cancelTrade required Params is Null ,info:["
					+ JaxbUtils.convertToXmlString(receiver) + "]");
		return wrapCancelTradeAnswerDtoFromRes(crmPassage.sendToType(getCancelTradeReq(receiver), CancelTradeRes.class));
	}

	private CancelTradeAnswerDto wrapCancelTradeAnswerDtoFromRes(CancelTradeRes res) {
		CancelTradeAnswerDto answer = new CancelTradeAnswerDto();
		answer.setLoypoint(res.getBody().getLoypoint());
		answer.setMcMemberCode(memberService.getMemberByMemberNum(res.getBody().getMembid()).getMcMemberCode());
		answer.setTxnid(res.getBody().getTxnid());
		answer.setRecode(res.getHead().getRetcode());
		answer.setRemsg(res.getHead().getRetmsg());
		return answer;
	}

	private CancelTradeReq getCancelTradeReq(CancelTradeReceiverDto receiver) {
		CancelTradeReq req = new CancelTradeReq();
		req.getBody().setMembid(memberService.getMemberCodeByMcMemberCode(receiver.getMcMemberCode()));
		req.getBody().setPunishpoints(receiver.getPunishpoints());
		req.getBody().setTxnid(receiver.getTxnid());
		return req;
	}

	public List<ScoreFillUpDto> queryRedeemList(ScoreRedeemDto queryDto) throws Exception {
		if (!queryDto.checkScoreRedeemRequiredParamsExists())
			throw new RequiredParamsNullException(" queryRedeemList required Params is Null ,info:[" + JaxbUtils.convertToXmlString(queryDto) + "]");
		ScoresRedeemResponse response = crmPassage.sendToType(getScoreRedeemRequest(queryDto),ScoresRedeemResponse.class);
		
		List<ScoreFillUpDto> scoreFillUpList = new ArrayList<ScoreFillUpDto>();
		if (response.isStatus(CrmResponse.Status.ERROR)) {
			throw new InterruptedException();
		} else if(response.isStatus(CrmResponse.Status.SUCCESS)){
			scoreFillUpList = getScoreFillUpList(response);
		}
		return scoreFillUpList;
	}

	private ScoreRedeemRequest getScoreRedeemRequest(ScoreRedeemDto queryDto) {
		ScoreRedeemRequest request = new ScoreRedeemRequest();
		ScoreRedeemRequest.RequestBody body = request.getBody();
		if(!StringUtils.isEmpty(queryDto.getMcMemberCode())){
			body.setMembid(memberService.getMemberIDByMcMemberCode(queryDto.getMcMemberCode()));
		} else{
			body.setMembid(queryDto.getMemberId());
		}
		body.setSrtype(CrmSrType.SCORE_FILL_UP.getCode());
		return request;
	}

	private List<ScoreFillUpDto> getScoreFillUpList(ScoresRedeemResponse response) {
		List<ScoreFillUpDto> scoreFillUpList = new ArrayList<ScoreFillUpDto>();
		if (response.getBody() != null && response.getBody().getListofsr().getSr() != null)
			for (ScoreRedeemVO scoreRedeemVO : response.getBody().getListofsr().getSr()) {
				ScoreFillUp scoreFillUp = null;
				String srdetail = scoreRedeemVO.getSrdetail();
				String type = scoreRedeemVO.getType();
//				if (srdetail.indexOf("\n") != -1) {
				if(!StringUtils.isEmpty(type)) {
					scoreFillUp = new ScoreFillUp();
					if (!response.getHead().getRetcode().equals(SUCCESS_STATUS)) {
						scoreFillUpList.add(scoreFillUp.toDto());
						return scoreFillUpList;
					}
//					scoreFillUp.setDetail(srdetail);
					// 消费金额
					scoreFillUp.setAmount(scoreRedeemVO.getAmount());
					// 消费项目

					// 开始时间  结束时间
					if(type.equals(ScoreFillUpType.HOTEL.getCnName())) {
						scoreFillUp.setCheckInTime(DateUtils.parseDate(scoreRedeemVO.getStartdate(), "MM/dd/yyyy hh:mm:ss"));
						scoreFillUp.setCheckOutTime(DateUtils.parseDate(scoreRedeemVO.getEnddate(), "MM/dd/yyyy hh:mm:ss"));
						scoreFillUp.setBusinessType(ScoreFillUpType.HOTEL);
					} else if(type.equals(ScoreFillUpType.TRAVEL.getCnName())) {
						scoreFillUp.setDepartDate(DateUtils.parseDate(scoreRedeemVO.getStartdate(), "MM/dd/yyyy hh:mm:ss"));
						scoreFillUp.setReturnDate(DateUtils.parseDate(scoreRedeemVO.getEnddate(), "MM/dd/yyyy hh:mm:ss"));
						scoreFillUp.setBusinessType(ScoreFillUpType.TRAVEL);
					} else {
						scoreFillUp.setCarStartTime(DateUtils.parseDate(scoreRedeemVO.getStartdate(), "MM/dd/yyyy hh:mm:ss"));
						scoreFillUp.setCarEndTime(DateUtils.parseDate(scoreRedeemVO.getEnddate(), "MM/dd/yyyy hh:mm:ss"));
						scoreFillUp.setBusinessType(ScoreFillUpType.AUTO);
					}

					// 申请状态
					scoreFillUp.setRedeemStatus(RedeemStatus.valueOf(scoreRedeemVO.getStatus().replace(" ", "")
							.toUpperCase().trim()));
					// 申请日期
					scoreFillUp.setApplyDate(DateUtils.parseDate(scoreRedeemVO.getApplydate(), "MM/dd/yyyy HH:mm:ss"));
					scoreFillUpList.add(scoreFillUp.toDto());
				}
			}
		return scoreFillUpList;
	}

	public ScoreAnswerDto scoreTrade(ScoreReceiverDto receiverDto) {
		RegisterScoresTradeReq req = buildRegisterScoresTradeReq(receiverDto);
		RegisterScoresTradeRes res = crmPassage.sendToType(req, RegisterScoresTradeRes.class);
		return buildScoreAnswerDto(res);
	}

	private ScoreAnswerDto buildScoreAnswerDto(RegisterScoresTradeRes res) {
		ScoreAnswerDto answerDto = new ScoreAnswerDto();
		//crm中的memberid对应vbp中的member num
		answerDto.setMcMemberCode(memberService.getMemberByMemberNum(res.getBody().getMembid()).getMcMemberCode());
		answerDto.setRecode(res.getHead().getRetcode());
		answerDto.setRemsg(res.getHead().getRetmsg());
		answerDto.setTransid(res.getBody().getTransid());
		return answerDto;
	}

	private RegisterScoresTradeReq buildRegisterScoresTradeReq(ScoreReceiverDto receiverDto) {
		RegisterScoresTradeReq req = new RegisterScoresTradeReq();
		//vbp中的member num 对应crm中memberid
		req.getBody().setMembid(memberService.getMemberCodeByMcMemberCode(receiverDto.getMcMemberCode()));
		req.getBody().setTransdate(DateUtils.formatDate(receiverDto.getTransdate(), "MM/dd/yyyy"));
		req.getBody().setProduct(receiverDto.getProductname());
		return req;
	}
	
}
