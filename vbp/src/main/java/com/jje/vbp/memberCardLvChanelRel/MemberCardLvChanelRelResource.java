package com.jje.vbp.memberCardLvChanelRel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jje.dto.member.MemberCardLvChanelRelDto;
import com.jje.dto.member.MemberCardLvChanelRelDtos;
import com.jje.vbp.memberCardLvChanelRel.service.MemberCardLvChanelRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Path("memberCardLvChanelRel")
@Component
public class MemberCardLvChanelRelResource {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberCardLvChanelRelResource.class);
	@Autowired
    private MemberCardLvChanelRelService service;

    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryChannelByKey/{key}")
    public Response queryChannelByKey(@PathParam("key")String key) {
    	logger.info("queryChannelByKey request ",key);
        List<String> channels =  service.queryChannelByKey(key);
        MemberCardLvChanelRelDto dto = new MemberCardLvChanelRelDto();
        dto.setChannel(channels);
        dto.setCardLevel(key);
        logger.info("queryChannelByKey response ",dto);
        return Response.ok().entity(dto).build();
    }

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/saveAndUpdate")
	public Response saveAndUpdate(MemberCardLvChanelRelDto memberCardLvChanelRelDto) {
        logger.info("saveAndUpdate request ",memberCardLvChanelRelDto);
        service.saveAndUpdate(memberCardLvChanelRelDto);
        return Response.ok().build();
	}


}
