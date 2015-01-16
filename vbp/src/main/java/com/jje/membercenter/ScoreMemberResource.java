package com.jje.membercenter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.remote.handler.MemberHandler;
import com.jje.membercenter.service.MemberService;

@Path("scoreMember")
@Component
public class ScoreMemberResource {
	private static final Logger log = LoggerFactory.getLogger(MemberResource.class);

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private MemberHandler memberHandler;
	@Autowired
	private MemberService memberService;
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/getMemberInfo/{cardOrPhoneOrEmail}")
	public Response getMemberInfoByEamilOrTelOrCardNo(@PathParam("cardOrPhoneOrEmail") String cardOrPhoneOrEmail) {
		try {
			log.warn("-----ScoreMemberResource.getMemberInfoByEamilOrTelOrCardNo() cardOrPhoneOrEmail="+cardOrPhoneOrEmail);
			Member member = memberRepository.queryByUsernameOrCellphoneOrEmail(cardOrPhoneOrEmail);
			return Response.status(Status.OK).entity(member.toDto()).build();
		} catch (Exception e) {
			log.error("---------ScoreMemberResource.getMemberInfoByEamilOrTelOrCardNo()---------error-----",e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * @param mcMemberCode
	 *            :会员ID
	 * @return javax.ws.rs.core.Response {entity: 会员积分
	 *         status:Status.Ok,Status.INTERNAL_SERVER_ERROR}
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getMemberScoreById/{mcMemberCode}")
	public Response getScore(@PathParam("mcMemberCode") String mcMemberCode) {
		try {
            if(log.isDebugEnabled()) log.debug("ScoreMemberResource.getScore({})",mcMemberCode);
			return Response.status(Status.OK).entity(memberHandler.getMemberScore(memberService.getMemberCodeByMcMemberCode(mcMemberCode))).build();
		} catch (Exception e) {
			log.error("---------ScoreMemberResource.getMemberScoreById({})---------error-----" ,mcMemberCode, e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getMemberScoreByMcMemberCode/{mcMemberCode}")
	public Response getMemberScoreByMcMemberCode(@PathParam("mcMemberCode") String mcMemberCode) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("ScoreMemberResource.getMemberScoreByMcMemberCode({})", mcMemberCode);
			}
			String memID = memberService.getMemberCodeByMcMemberCode(mcMemberCode);
			if (memID == null) {
				return Response.status(Status.NOT_ACCEPTABLE).entity("02:会员ID错误").build();
			}
			Integer memberScore = memberHandler.getMemberScore(memID);
			if (memberScore == null || memberScore < 0) {
				memberScore = 0;
			}
			return Response.status(Status.OK).entity(memberScore).build();
		} catch (Exception e) {
			log.error("---------ScoreMemberResource.getMemberScoreByMcMemberCode({})---------error-----", mcMemberCode, e);
			return Response.status(Status.NOT_ACCEPTABLE).entity("02:会员ID错误").build();
		}
	}

}