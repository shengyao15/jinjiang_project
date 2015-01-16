package com.jje.vbp.template;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.dto.nbp.TemplateDto;
import com.jje.membercenter.domain.CRMOperationRespository;

@Path("template")
@Component
public class TemplateResource {

    @Autowired
    CRMOperationRespository crmOperationRespository;

    private static final Logger logger = LoggerFactory.getLogger(TemplateResource.class);

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/syncTemplateToCrm")
    public Response syncTemplateToCrm(TemplateDto tempateDto)  {
    	try {
    		boolean flag = crmOperationRespository.syncTemplateToCrm(tempateDto);
        	if(flag)
        		return Response.status(Status.OK).build();
        	return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (Exception e) {
			logger.error("syncTemplateToCrm error:"+e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
    	
    }

}
