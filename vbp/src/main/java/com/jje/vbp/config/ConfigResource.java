package com.jje.vbp.config;

import com.jje.dto.vbp.config.SystemDictDto;
import com.jje.dto.vbp.config.SystemDictDtos;
import com.jje.vbp.config.domain.SystemDict;
import com.jje.vbp.config.domain.SystemDictRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("config")
@Component
public class ConfigResource {
    private static final Logger logger = LoggerFactory.getLogger(ConfigResource.class);
    @Autowired
    private SystemDictRepository repository;


    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryAllConfig")
    public Response queryAllConfig(){
        List<SystemDict> dicts = repository.queryAll();
        List<SystemDictDto> listDictDto = new ArrayList<SystemDictDto>();
        for (SystemDict dict : dicts) {
            listDictDto.add(dict.toDto());
        }
        SystemDictDtos systemDictDtos = new SystemDictDtos();
        systemDictDtos.setSystemDictDtoList(listDictDto);
        return Response.ok().entity(systemDictDtos).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/delete/{id}")
    public Response deleteById(@PathParam("id")Long id){
        repository.deleteById(id);
        return Response.ok().build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/update")
    public Response updateByKey(SystemDictDto systemDictDto){
        repository.update(new SystemDict(systemDictDto));
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/insert")
    public Response insert(SystemDictDto systemDictDto){
        repository.save(new SystemDict(systemDictDto));
        return Response.ok().build();
    }

}
