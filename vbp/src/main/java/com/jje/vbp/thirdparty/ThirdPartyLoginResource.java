package com.jje.vbp.thirdparty;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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

import com.jje.dto.member.thirdParty.ThirdPartyLoginDto;
import com.jje.dto.member.thirdParty.ThirdPartyLoginDto.ThirdPartyLoginResult;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.membercenter.SSOResource;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;

@Path("/member/thirdPartyLogin")
@Component("thirdPartyLoginResource")
public class ThirdPartyLoginResource {
	
	private static Logger logger = LoggerFactory.getLogger(ThirdPartyLoginResource.class);
	
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private CRMMembershipRepository crmRepository;
	
	@Autowired
	private SSOResource ssoResource;
	    
	
	@GET
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/query/{crsId}/{phone}")
	public Response query(@PathParam("crsId") String crsId, @PathParam("phone") String phone) {
		logger.warn(String.format("锦江之星联合登陆查询请求，crsId = %s , phone = %s", crsId, phone));
		ThirdPartyLoginDto result = null;
		String ticket ;
		try {
			CRMResponseDto dto = crmRepository.queryByCrsId(crsId, phone);
			if (!dto.isExecSuccess()) {
				logger.warn("锦江之星联合登陆，CRM查询请求查询失败，错误信息: {}", dto.getRetmsg());
				result = new ThirdPartyLoginDto(ThirdPartyLoginResult.FAIL, null);
				return Response.ok().entity(result).build();
			}
			Member member = memberRepository.getMemberByMemberID(dto.getMembid());
			if (member == null) {
				logger.warn("锦江之星联合登陆，VBP根据memberId查询会员返回结果为空，memberId = {}", dto.getMembid());
				result = new ThirdPartyLoginDto(ThirdPartyLoginResult.NOT_FOUND, null);
				return Response.ok().entity(result).build();
			}
			ticket = (String) ssoResource.genTicket(member.toDto()).getEntity();
		} catch (Exception e) {
			logger.error("锦江之星联合登陆查询请求发生异常，crsId = {}", crsId, e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		logger.warn("------锦江之星联合登陆查询成功-------~");
		result = new ThirdPartyLoginDto(StringUtils.isBlank(ticket)?ThirdPartyLoginResult.FAIL:ThirdPartyLoginResult.SUCCESS, ticket);
		return Response.ok().entity(result).build();
	}
	
}
