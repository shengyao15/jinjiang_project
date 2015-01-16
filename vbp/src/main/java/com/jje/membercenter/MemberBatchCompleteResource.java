package com.jje.membercenter;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.dto.Pagination;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.membercenter.RegistResponse;
import com.jje.dto.membercenter.RegistResponseStatus;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.WebMember;
import com.jje.membercenter.persistence.WebMemberRepository;
import com.jje.membercenter.remote.handler.MemberHandler;


@Path("batchComplete")
@Component
public class MemberBatchCompleteResource {

    private final Logger logger = LoggerFactory.getLogger(MemberBatchCompleteResource.class);


    @Autowired
    WebMemberRepository webMemberRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberHandler memberHandler;
    
	@GET
	@Path("/batchCompleteMemberInfo/{sleepTime}/{num}")
	public Response allBatchCompleteMemberInfo(@PathParam("sleepTime") Long sleepTime, @PathParam("num") Integer num) {
		try {
			Pagination page = new Pagination();
			page.setRows(100);
			page.countRecords(webMemberRepository.getWebMemberCount());
			logger.error("查询临时会员  page : "+page.getPage() +"  total : " + page.getTotal());
			for (int i = page.getPage(); i <= page.getTotal(); i++) {
				page.setPage(i);
				List<Long> memberIdList = webMemberRepository.getWebMemberIdList(page);
				if (memberIdList.size() == 0) {
					return Response.status(Response.Status.OK).entity("成功清洗会员").build();
				}
				batchCompleteMemberInfo(memberIdList, sleepTime, num);
			}
		} catch (Exception e) {
			logger.error("查询临时会员时异常{}", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("查询临时会员时异常,请检查时间格式").build();
		}
		return Response.status(Response.Status.OK).entity("会员清洗进行中...").build();
	}

	private void batchCompleteMemberInfo(List<Long> memberIdList, Long sleepTime, Integer num) {
		logger.error("webmember count is:" + memberIdList.size());
		ExecutorService executorService = Executors.newFixedThreadPool(num);
		for (final Long id : memberIdList) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			executorService.execute(new Runnable() {
				public void run() {
					logger.error("start-completeWebMemberInfo--{}", id);
					completeWebMemberInfo(id);
					logger.error("end-completeWebMemberInfo--{}", id);
				}
			});
		}
	}

    @GET
    @Path("/singleCompleteMemberInfo/{id}")
    public Response batchCompleteMemberInfo(@PathParam("id")Long id){
        if(completeWebMemberInfo(id)){
            return Response.ok().build();
        }
        logger.error("清洗会员失败:{}",id);
        return Response.serverError().build();
    }

    private Boolean completeWebMemberInfo(Long id) {
        try {
            logger.info("快速注册会员清洗 webMemberID-->"+id);
           // WebMember webMember=webMemberRepository.getWebMemberInfoById(id);
            WebMember webMember=webMemberRepository.getWebMember(id);
            Member member = new Member(webMember);
            member.setMemberID(member.getCardNo());
            member.setFullName(StringUtils.isNotBlank(member.getEmail()) ? member.getEmail() : member.getCellPhone());
            member.setIdentityType("Others");
            member.setIdentityNo("_"+member.getCardNo());
            RegistResponse result = toFullMember(member);
            if(result.getStatus().equals(RegistResponseStatus.OK)){
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("========= completeWebMemberInfo error:{}==========", e);
            return false;
        }
    }


    private RegistResponse toFullMember(Member member) throws Exception {
        member.setActiveChannel(RegistChannel.Website.name());
        RegistResponse result = null;
        List<Member> memberResultList = memberRepository.checkIdentifyInfo(member);
        logger.warn("toFullMember(Member member) memberResultList=" ,memberResultList);
        if (memberResultList != null && !memberResultList.isEmpty()) {
            result = migrateWebMemberInfo(member, memberResultList);
            logger.warn("toFullMember(Member member) result=" ,result);
        } else {
            result = memberHandler.batchRegist(member);
        }
        return result;
    }

    private RegistResponse migrateWebMemberInfo(Member member, List<Member> memberResultList) throws Exception {
        RegistResponse result = new RegistResponse();
        result.setStatus(RegistResponseStatus.MEMBER_ALREDY_REGIST);
        if (!isAcitiveStatus(memberResultList))
            result = memberHandler.updateMemberBaseInfo(getUpdateMember(member, memberResultList));
        return result;
    }



    private boolean isAcitiveStatus(List<Member> memberResultList) {
        boolean isAcitiveStatus = false;
        for (Member m : memberResultList) {
            if (!m.getActivateCode().equals("Not Activiate")) {
                isAcitiveStatus = true;
                break;
            }
        }
        return isAcitiveStatus;
    }

    private Member getUpdateMember(Member member, List<Member> memberResultList) {
        Member m = getValidMember(memberResultList);
        logger.error("========= getUpdateMember======cardNo===="+m.getCardNo());
        m.setEmail(member.getEmail());
        m.setCellPhone(member.getCellPhone());
        m.setPassword(member.getPassword());
        m.setRemindQuestion(member.getRemindQuestion());
        m.setRemindAnswer(member.getRemindAnswer());
        m.setActiveChannel(member.getActiveChannel());
        m.setActiveTag(member.getRegisterTag());
        return m;
    }
    private Member getValidMember(List<Member> memberResultList) {
        Member m = memberResultList.get(0);
        for (Member mem : memberResultList) {
            logger.error("========= getValidMember======cardNo===="+mem.getCardNo());
            String cardNo=mem.getCardNo().toUpperCase();
            //G,X,J,Y,U,H
            if ((cardNo.startsWith("H"))||(cardNo.startsWith("G"))||
                    (cardNo.startsWith("X"))||(cardNo.startsWith("J"))||
                    (cardNo.startsWith("Y"))||(cardNo.startsWith("U"))) {
                m = mem;
                break;
            }
        }
        return m;
    }


    private MemberDto convertMemberDto(WebMember webMember) {
        MemberDto memberDto = new MemberDto();
        memberDto.setCellPhone(webMember.getPhone());
        memberDto.setEmail(webMember.getEmail());
        memberDto.setCardNo(webMember.getTempCardNo());
        memberDto.setId(webMember.getId());
        memberDto.setPassword(webMember.getPwd());
        memberDto.setIdentityType(webMember.getIdentityType());
        memberDto.setIdentityNo(webMember.getIdentityNo());
        memberDto.setMemberCode(webMember.getMemberCode());
        if(StringUtils.isNotBlank(webMember.getEmail())){
            memberDto.setFullName(webMember.getEmail());
        }else{
            memberDto.setFullName(webMember.getPhone());
        }
        memberDto.setRegisterDate(webMember.getRegistTime());
        memberDto.setMemType(webMember.getMemType());
        memberDto.setMcMemberCode(webMember.getMcMemberCode());
        memberDto.setIsWebMember(true);
        return memberDto;
    }


}
