package com.jje.vbp.points;


import com.jje.dto.vbp.MemberPointsDto;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.service.MemberService;
import com.jje.vbp.points.domain.MemberPoints;
import com.jje.vbp.points.domain.MemberPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Component
@Path("points")
public class PointsResource {

    @Autowired
    private MemberPointsRepository repository;

    @Autowired
    private MemberService memberService;

    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryMemberPoints/{mcCode}")
    public Response queryMemberPoints(@PathParam("mcCode") String mcCode){
        try{
            Member member = memberService.getMemberByMcMemberCode(mcCode);
            if(member!=null){
                String memNum = member.getMemberCode();
                MemberPoints memberPoints = repository.queryPointsAtPrevMonth(memNum);
                if(memberPoints!=null){
                    return Response.ok().entity(memberPoints.toDto()).build();
                }
            }
        }catch (Exception e){
            return Response.ok().entity(new MemberPointsDto()).build();
        }
        return Response.ok().entity(new MemberPointsDto()).build();
    }



}
