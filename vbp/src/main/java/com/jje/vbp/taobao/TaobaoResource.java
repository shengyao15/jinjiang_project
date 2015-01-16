package com.jje.vbp.taobao;

import java.math.BigInteger;
import java.security.MessageDigest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.member.taobao.TaobaoCheckDto;
import com.jje.dto.member.taobao.TaobaoErrorMsg;
import com.jje.dto.member.taobao.TaobaoLoginDto;
import com.jje.dto.member.taobao.TaobaoPointsQueryOutDto;
import com.jje.dto.member.taobao.TaobaoPointsRedeemInDto;
import com.jje.dto.member.taobao.TaobaoPointsRedeemOutDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.domain.CRMOperationRespository;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.OpRecordLogDomain;
import com.jje.membercenter.domain.OpRecordLogDomain.EnumOpType;
import com.jje.membercenter.persistence.OpRecordLogRepository;
import com.jje.membercenter.service.MemberService;
import com.jje.membercenter.xsd.TaobaoQueryScoreResponse;
import com.jje.membercenter.xsd.TaobaoRedeemResponse;
import com.jje.vbp.taobao.domain.TaobaoRepository;
import com.jje.vbp.taobao.proxy.TaobaoProxy;

@Path("/taobao")
@Component
public class TaobaoResource {
	private static final String partnerId = "1034991800";

	private static Logger logger = LoggerFactory.getLogger(TaobaoResource.class);

    @Autowired
    CRMOperationRespository crmOperationRespository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private TaobaoRepository taobaoRepository;
	@Autowired
	private OpRecordLogRepository opRecordLogService;
	@Autowired
	private MemberResource memberResource;
	@Autowired
	private MemberService memberService;
    @Autowired
    private TaobaoProxy taobaoProxy;
    
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/login")
	public Response login(TaobaoLoginDto loginDto) {
		final Member member = memberRepository.queryByUsernameOrCellphoneOrEmail(loginDto.getLoginName());
		if (member == null || !member.validate(loginDto.getPassword())) {
			logger.warn("taobao login fail with loginDto:{}", loginDto);
			return Response.ok(TaobaoErrorMsg.LOGIN_FAILED.toString()).build();
		}
		final TaobaoCheckDto taobaoCheckDto = taobaoRepository.checkCanBinding(loginDto.getTbLoginId(), member);
		logger.info("taobao login check result taobaoCheckDto:{}", taobaoCheckDto);
		if (!taobaoCheckDto.isBinded()) {
			return Response.ok(taobaoRepository.bindAndNotify(loginDto.getTbLoginId(), loginDto.getTbLevel(), member, true).toString()).build();
		} else if (taobaoCheckDto.isBinded() && taobaoCheckDto.isCanReNotiry()) {
			return Response.ok(taobaoRepository.notify(loginDto.getTbLoginId(), member).toString()).build();
		} else {
			logger.error("taobao login error taobaoCheckDto:{}", taobaoCheckDto);
			return Response.ok(taobaoCheckDto.getRetCode().toString()).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/regist")
	public Response taobaoMembership(MemberRegisterDto dto) {
		try {
			TaobaoErrorMsg errorMsg = null;
			if (null == dto.getMemberInfoDto() 
					|| StringUtils.isBlank(dto.getMemberInfoDto().getTaobaoId())
					|| StringUtils.isBlank(dto.getMemberInfoDto().getTaobaoLevel())) {
				OpRecordLogDomain opRecord = new OpRecordLogDomain();
				opRecord.setOpType(EnumOpType.MEMBER_REGISTER);
				opRecord.setContent(JaxbUtils.convertToXmlString(dto));
				opRecord.setMessage("taobaoId is empty or memberInfoDto is empty.");
				opRecordLogService.insert(opRecord);
				errorMsg = TaobaoErrorMsg.NOT_ACCEPTABLE;
				return Response.ok(errorMsg.toString()).build();
			}
			String taobaoId = dto.getMemberInfoDto().getTaobaoId();
			// 检查淘宝用户是否已经绑定
			final TaobaoCheckDto taobaoCheckDto = taobaoRepository.checkCanBinding(taobaoId, null);
			if (taobaoCheckDto.isBinded()) {
				errorMsg = taobaoCheckDto.getRetCode();
				return Response.ok(errorMsg.toString()).build();
			}
			// 完整注册
			Response response = memberResource.addVIPMembership(dto);
			CRMResponseDto crmDto = (CRMResponseDto) response.getEntity();
			if (!crmDto.isExecSuccess()) {
				errorMsg = TaobaoErrorMsg.CRM_FAIL;
				logger.error("==TaobaoResource.taobaoMembership, msg:{}", crmDto.getRetmsg());
				return Response.ok(errorMsg.toString()).build();
			}
			logger.warn("regist result:{},  mcCode:{}", crmDto.isExecSuccess(),crmDto.getMcMemberCode());
			// 绑定taobao账号
			Member member = memberService.getMemberByMcMemberCode(crmDto.getMcMemberCode());
			errorMsg = taobaoRepository.bindAndNotify(taobaoId, dto.getMemberInfoDto().getTaobaoLevel(), member, false);
            if (TaobaoErrorMsg.SUCCESS.equals(errorMsg)) {
                errorMsg = taobaoRepository.autoUpgrade(member,dto);
            }
			return Response.ok(errorMsg.toString()).build();
		} catch (Exception e) {
			logger.error("----MemberResource.taobaoMembership()----register error : ", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/queryPoints")
	public Response queryPoints(String taobaoID) {

		logger.info("queryPoints start " + taobaoID);
		
		try {
			String crmID = taobaoRepository.queryCRMID(taobaoID);
			
			TaobaoQueryScoreResponse response = crmOperationRespository.queryTaobaoScore(crmID);
			
			TaobaoPointsQueryOutDto dto = new TaobaoPointsQueryOutDto();
			String points = response.getBody().getPoints();
			String balance = response.getBody().getBalance();
			
			dto.setAllPoints(Long.valueOf(points));
			dto.setLimitPoints(Long.valueOf(balance));
			dto.setValidate(convertMD5(points+""+balance));
			
			logger.info("queryPoints end");
			
			return Response.ok().entity(dto).build();
		} catch (Exception e) {
			logger.error("taobao queryPoints error：  " + taobaoID + "  " + e.getMessage(), e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}	
	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/queryPointsFromJj")
	public Response queryPointsFromJj(String crmID) {

		logger.info("queryPointsFromJj start " + crmID);
		
		try {
			TaobaoQueryScoreResponse response = crmOperationRespository.queryTaobaoScore(crmID);
			
			TaobaoPointsQueryOutDto dto = new TaobaoPointsQueryOutDto();
			String points = response.getBody().getPoints();
			String balance = response.getBody().getBalance();
			
			dto.setAllPoints(Long.valueOf(points));
			dto.setLimitPoints(Long.valueOf(balance));
			dto.setValidate(convertMD5(points+""+balance));
			
			logger.info("queryPointsFromJj end");
			
			return Response.ok().entity(dto).build();
		} catch (Exception e) {
			logger.error("taobao queryPointsFromJj error：  " + crmID + "  " + e.getMessage(), e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}	
	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/redeemPoints") 
	public Response redeemPoints(TaobaoPointsRedeemInDto inDto) {
		
		logger.info("redeemPoints start ");
		String userID = inDto.getUserID();
		try {
			
			String taobaoID = inDto.getUserID();
			String crmID = taobaoRepository.queryCRMID(taobaoID);
			inDto.setUserID(crmID);
			
			TaobaoRedeemResponse response = crmOperationRespository.redeem(inDto, "TaoBao");
			
			TaobaoPointsRedeemOutDto outDto = new TaobaoPointsRedeemOutDto();
			String status = response.getHead().getRetcode();
			if("00001".equals(status)){
				outDto.setStatus("1");
				outDto.setValidate(convertMD5("1"));
			}else{
				outDto.setStatus("2");
				String errMsg = response.getHead().getRetmsg();
				outDto.setErrMsg(errMsg);
				outDto.setValidate(convertMD5("2"));
			}
		
			logger.info("redeemPoints end "  + userID);
			
			return Response.ok().entity(outDto).build();
		} catch (Exception e) {
			logger.error("taobao redeem error：  " + userID + "  " + e.getMessage(), e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/checkRedeemLink/{mcCode}") 
	public Response checkRedeemLink(@PathParam("mcCode") String mcCode) {
		String taobaoID = taobaoRepository.queryTaobaoID(mcCode);
		return Response.ok().entity(taobaoID).build();
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/redeemPointsFromJj/{mcCode}") 
	public Response redeemPointsFromJj(@PathParam("mcCode") String mcCode, TaobaoPointsRedeemInDto inDto) {
		
		logger.info("redeemPointsFromJj start ");
		String crmID = inDto.getUserID();
		
		try {
			//1,  扣减CRM积分
			TaobaoRedeemResponse response = crmOperationRespository.redeem(inDto, "Website");
			
			TaobaoPointsRedeemOutDto outDto = new TaobaoPointsRedeemOutDto();
			String retCode = response.getHead().getRetcode();
			if("00001".equals(retCode)){
				outDto.setStatus("1");
			}else{
				outDto.setStatus("2");
				String errMsg = response.getHead().getRetmsg();
				outDto.setErrMsg(errMsg);
				return Response.ok().entity(outDto).build();
			}
		
			//2, 增加taobao里程
			//2.1  根据mc_code 
			String  points = String.valueOf(inDto.getRedeemPoints());
			String taobaoID = taobaoRepository.queryTaobaoID(mcCode);
			
			boolean taobaoStatus = taobaoProxy.taobaoAddPoints(taobaoID, points);
			
			logger.error("TaoBao add miles status " + taobaoStatus + " taobaoID " + taobaoID);
			
			if(!taobaoStatus){
				outDto.setStatus("2");
				outDto.setErrMsg("淘宝增加里程失败");
			}
			
			
			logger.info("redeemPointsFromJj end "  + crmID);
			
			return Response.ok().entity(outDto).build();
		} catch (Exception e) {
			logger.error("redeemPointsFromJj error：  " + crmID + "  " + e.getMessage(), e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/queryCRMID/{taobaoID}")  //我们扣减积分
	public Response queryCRMID(@PathParam("taobaoID") String taobaoID) {
		
		String crmID = taobaoRepository.queryCRMID(taobaoID);
		
		return Response.ok(crmID).build();
	}
	
	private static String convertMD5(String key){
		key = key+partnerId;
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = key.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
	}
}
