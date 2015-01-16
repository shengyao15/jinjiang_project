package com.jje.vbp.levelBenefit;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.dto.member.MemberLevelBenefitDto;
import com.jje.membercenter.service.MemberService;
import com.jje.vbp.levelBenefit.domain.MemberLevelBenefitDomain;
import com.jje.vbp.levelBenefit.service.MemberLevelBenefitRepository;

@Path("memberLevelBenefit")
@Component
public class MemberLevelBenefitResource {
	
	private final Logger logger = LoggerFactory.getLogger(MemberLevelBenefitResource.class); 
	
	@Autowired
	private MemberLevelBenefitRepository memberLevelBenefitRepository;
	
	@Autowired
	private MemberService memberService;
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
    @Path("/query/{mcMemberCode}")
    public Response query(@PathParam("mcMemberCode") String mcMemberCode) {
    	logger.debug("memberLevelBenefit/query({})", mcMemberCode);
    	// 查询上一个月的记录 for Mobile
    	try {
    		if(StringUtils.isEmpty(mcMemberCode)) {
            	logger.warn("memberLevelBenefit/query({}): mcMemberCode is empty!", mcMemberCode);
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    		}
        	String memberId = memberService.getMemberIDByMcMemberCode(mcMemberCode);
            if (null == memberId || "".equals(memberId)) {
            	logger.warn("memberLevelBenefit/query({}): mcMemberCode not found!", mcMemberCode);
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
        	List<MemberLevelBenefitDomain>  list = memberLevelBenefitRepository.query(memberId);
        	MemberLevelBenefitDto dto = new MemberLevelBenefitDto();
        	if(list == null || list.size() < 1) {
        		 return Response.ok(dto).build();
        	}else if(list.size() > 1) {
            	logger.warn("memberLevelBenefit/query({}): multipile records found!", mcMemberCode);
        	}
        	MemberLevelBenefitDomain domain = list.get(0);
    		BeanUtils.copyProperties(domain, dto);
            return Response.ok(dto).build();
    	} catch(Exception e) {
    		logger.error("memberLevelBenefit query({}) error!", mcMemberCode, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    	}
    }
    
}
