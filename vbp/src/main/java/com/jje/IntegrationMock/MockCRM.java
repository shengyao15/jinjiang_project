package com.jje.IntegrationMock;

import java.math.BigInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.jje.membercenter.remote.crm.datagram.request.MemberBaseInfoUpdateReq;
import com.jje.membercenter.remote.crm.datagram.response.*;
import com.jje.membercenter.remote.vo.Contact;
import org.springframework.stereotype.Component;

import com.jje.membercenter.remote.crm.datagram.response.CancelTradeRes;
import com.jje.membercenter.remote.crm.datagram.response.GetScoresHistoryRes;
import com.jje.membercenter.remote.crm.datagram.response.LoypointRes;
import com.jje.membercenter.remote.crm.datagram.response.MemberBaseInfoRes;
import com.jje.membercenter.remote.crm.datagram.response.MemberBaseInfoUpdateRes;
import com.jje.membercenter.remote.crm.datagram.response.MemberQuickRegistRes;
import com.jje.membercenter.remote.crm.datagram.response.ScoreFillUpRes;
import com.jje.membercenter.remote.crm.datagram.response.ScoresRedeemResponse;
import com.jje.membercenter.remote.crm.datagram.response.ScoresTradeRes;
import com.jje.membercenter.remote.crm.support.CrmResponse.DefaultResponseHead;
import com.jje.membercenter.remote.crm.support.CrmTransCode;
import com.jje.membercenter.remote.vo.ScoreRedeemVO;
import com.jje.membercenter.remote.vo.ScoreTransactionVO;
import com.jje.membercenter.xsd.MemberRegisterResponse;

@Path("/eai_anon_chs")
@Component
public class MockCRM {
	
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/start.swe")
    public Response validate(@FormParam("SWEExtSource") String id, @FormParam("SWEExtCmd") String SWEExtCmd,
                             @FormParam("UserName") String UserName, @FormParam("Password") String Password,
                             @FormParam("SWEExtData") String SWEExtData) {
        String code = getCode(SWEExtData);
        if (code.equals(CrmTransCode.SCORE_FILL_UP.getCode())) {
            return Response.status(Status.OK).entity(getScoreFillUpRes()).build();
        }

        if (code.equals(CrmTransCode.GET_MEMBER_INFO.getCode())) {
            return Response.status(Status.OK).entity(getMemberInfoRes()).build();
        }

        if (code.equals(CrmTransCode.GET_SCORES_HISTORY.getCode())) {
            return Response.status(Status.OK).entity(getScoresHistoryRes()).build();
        }

        if (code.equals(CrmTransCode.GET_SCORES_REDEEM_LIST.getCode())) {
            return Response.status(Status.OK).entity(getQueryRedeemRes()).build();
        }

        if (code.equals(CrmTransCode.SCORES_TRADE.getCode())) {
            return Response.status(Status.OK).entity(getScoresTradeRes()).build();
        }

        if (code.equals(CrmTransCode.CANCEL_TRADE.getCode())) {
            return Response.status(Status.OK).entity(getCancelTradeRes()).build();
        }
        if (code.equals(CrmTransCode.MEMBER_AVALIABLE_LOYPOINT.getCode())) {
            return Response.status(Status.OK).entity(getLoypointRes()).build();
        }
        if (code.equals(CrmTransCode.QUICK_REGIST.getCode())) {
            return Response.status(Status.OK).entity(getMemberQuickRegistRes()).build();
        }
        if (code.equals(CrmTransCode.UPDATE_MEMBER_INFO.getCode())) {
            String memberId = getMemberId(SWEExtData);
            if("333".equals(memberId) || "14222".equals(memberId)){
                return Response.status(Status.OK).entity(getMemberBaseInfoUpdateRes(memberId)).build();
            }
            return Response.status(Status.OK).entity(getMemberBaseInfoUpdateRes()).build();
        }
        if (code.equals(CrmTransCode.REGISTER_MEMBER.getCode())) {
        	System.out.println("register member mock crm init...");
            return Response.status(Status.OK).entity(getRegisterMemberRes()).build();
        }
 
        return null;
    }
    
    private String getMemberId(String data) {
        System.out.println("========data:====="+data.substring(data.indexOf("<membid>") + 8, data.indexOf("</membid>"))+"==========");
         return data.substring(data.indexOf("<membid>") + 8, data.indexOf("</membid>"));
    }

    private MemberRegisterResponse getRegisterMemberRes() {
    	MemberRegisterResponse reg = new MemberRegisterResponse();
    	MemberRegisterResponse.Head head = new MemberRegisterResponse.Head();
    	head.setRetcode(new BigInteger("00001"));
    	reg.setHead(head);
    	MemberRegisterResponse.Body body = new MemberRegisterResponse.Body();
    	body.setRecode("00001");
    	body.setMembid("1-fasfas");
    	reg.setBody(body);
		return reg;
	}

	private MemberBaseInfoRes getMemberInfoRes() {
        MemberBaseInfoRes response = new MemberBaseInfoRes();
        response.setHead(new DefaultResponseHead());
        response.getHead().setRetcode("00001");
        MemberBaseInfoRes.ResponseBody body = new MemberBaseInfoRes.ResponseBody();
        body.setLoypoint("100");
        response.setBody(body);
        return response;
    }


    private LoypointRes getLoypointRes() {
        LoypointRes response = new LoypointRes();
        response.setHead(new DefaultResponseHead());
        response.getHead().setRetcode("00001");
        LoypointRes.ResponseBody body = new LoypointRes.ResponseBody();
        body.setPoints("1000");
        response.setBody(body);
        return response;
    }


    private GetScoresHistoryRes getScoresHistoryRes() {
        GetScoresHistoryRes testRes = new GetScoresHistoryRes();
        GetScoresHistoryRes.ResponseBody body = new GetScoresHistoryRes.ResponseBody();
        ScoreTransactionVO scoreTransactionVO = new ScoreTransactionVO();
        scoreTransactionVO.setComments("comments");
        scoreTransactionVO.setStartdate("12/23/2011");
        scoreTransactionVO.setEnddate("01/23/2012");
        scoreTransactionVO.setTransactiondate("12/23/2011 14:00:00");
        scoreTransactionVO.setTransactiontype("REDEMPTION");
        scoreTransactionVO.setPoints("100");
        scoreTransactionVO.setProductname("productName");
        GetScoresHistoryRes.Listofloytransaction list = new GetScoresHistoryRes.Listofloytransaction();
        list.getLoytransaction().add(scoreTransactionVO);
        body.setListofloytransaction(list);
        testRes.setBody(body);
        return testRes;
    }

    private ScoreFillUpRes getScoreFillUpRes() {
        ScoreFillUpRes response = new ScoreFillUpRes();
        ScoreFillUpRes.ResponseBody body = new ScoreFillUpRes.ResponseBody();
        body.setRecode(new BigInteger("00001"));
        response.setBody(body);
        return response;
    }

    private String getCode(String data) {
        return data.substring(data.indexOf("<transcode>") + 11, data.indexOf("</transcode>"));
    }

    public ScoresRedeemResponse getQueryRedeemRes() {
        ScoresRedeemResponse scoresRedeemResponse = new ScoresRedeemResponse();
        scoresRedeemResponse.getHead().setRetcode("00001");
        ScoresRedeemResponse.ResponseBody body = new ScoresRedeemResponse.ResponseBody();
        ScoreRedeemVO scoreRedeemVO = new ScoreRedeemVO();
        scoreRedeemVO.setOwner("system");
        scoreRedeemVO.setStatus("open");
        scoreRedeemVO.setSrnumber("1-24579462");
        scoreRedeemVO.setStartdate("03/01/2012 19:26:58");
        scoreRedeemVO.setSrtype("Credit Readd");
        scoreRedeemVO
                .setSrdetail("消费类型:旅游" + "\n" + "购买时间:2012年02月27日" + "\n" + "购买门店:锦江店" + "\n" + "线路名称:马尔代夫Robinson罗宾逊岛6日4晚(直飞*含三餐*含税12年5/1-6/25)" + "\n" + " 团号:null" + "\n" + "出发日期:2012年02月27日" + "\n" + "结束日期:2012年02月27日" + "\n" + "消费金额(元):800" + "\n" + "订单号:WF0010" + "\n" + "发票号:I000001");
        ScoresRedeemResponse.Listofsr listofsr = new ScoresRedeemResponse.Listofsr();
        listofsr.getSr().add(scoreRedeemVO);
        body.setListofsr(listofsr);
        scoresRedeemResponse.setBody(body);
        return scoresRedeemResponse;
    }

    private ScoresTradeRes getScoresTradeRes() {

        ScoresTradeRes res = new ScoresTradeRes();
        ScoresTradeRes.ResponseBody body = new ScoresTradeRes.ResponseBody();
        body.setMembid("1-18120148");
        body.setRemainpoint("121");
        body.setTransid("1-END8Y");
        res.setBody(body);
        res.getHead().setRetcode("00001");
        res.getHead().setRetmsg("ssssssssss");
        return res;

    }

    private CancelTradeRes getCancelTradeRes() {
        CancelTradeRes res = new CancelTradeRes();
        CancelTradeRes.ResponseBody body = new CancelTradeRes.ResponseBody();
        body.setMembid("1-18120148");
        body.setLoypoint("345");
        body.setTxnid("E-ADC12");
        res.setBody(body);
        res.getHead().setRetcode("00001");
        res.getHead().setRetmsg("交易成功");
        return res;

    }


    MemberBaseInfoUpdateRes getMemberBaseInfoUpdateRes(){
    	
    	MemberBaseInfoUpdateRes res =new MemberBaseInfoUpdateRes();
    	MemberBaseInfoUpdateRes.ResponseBody body=new MemberBaseInfoUpdateRes.ResponseBody();
    	res.setBody(body);
    	res.getHead().setRetcode("00001");
        res.getHead().setRetmsg("交易成功");
        body.setMembid("1-18120148");
		return res;
        
    }
    
    MemberQuickRegistRes getMemberQuickRegistRes(){
    	MemberQuickRegistRes res=new MemberQuickRegistRes();
    	MemberQuickRegistRes.ResponseBody body=new MemberQuickRegistRes.ResponseBody();
    	res.setBody(body);
    	res.getHead().setRetcode("00001");
        res.getHead().setRetmsg("交易成功");
        body.setMembid("1-18120148");
		return res;
    }

    private MemberBaseInfoUpdateRes getMemberBaseInfoUpdateRes(String memberId) {
        MemberBaseInfoUpdateRes res = new MemberBaseInfoUpdateRes();
        MemberBaseInfoUpdateRes.ResponseBody body = new MemberBaseInfoUpdateRes.ResponseBody();
        System.out.println("========memberId:"+memberId+"==========");
        if(!"333".equals(memberId)){
            res.getHead().setRetcode("00002");
            res.getHead().setRetmsg("处理失败");
        }else {
            res.getHead().setRetcode("00001");
            res.getHead().setRetmsg("处理成功");
        }
        res.setBody(body);
        return res;
    }

}