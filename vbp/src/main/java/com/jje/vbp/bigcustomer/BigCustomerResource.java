package com.jje.vbp.bigcustomer;

import com.jje.dto.membercenter.bigcustomer.BigCustomerDto;
import com.jje.dto.membercenter.bigcustomer.BigCustomersDto;
import com.jje.dto.membercenter.bigcustomer.CustomerAccountDto;
import com.jje.vbp.bigcustomer.domain.BigCustomer;
import com.jje.vbp.bigcustomer.domain.CustomerAccount;
import com.jje.vbp.bigcustomer.repository.CustomerAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("bigCustomer")
public class BigCustomerResource {
	private final Logger logger = LoggerFactory.getLogger(BigCustomerResource.class);
	
	@Autowired
	private CustomerAccountRepository cusAccRespository;

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/query")
	public Response query(CustomerAccountDto dto) {
		logger.warn("[BigCustomerResource]request query, dto :{}", dto);
		List<BigCustomer> bigCustomers = cusAccRespository.queryBigCustomerByAccount(new CustomerAccount(dto));
		BigCustomersDto bigCustomersDto = new BigCustomersDto();
		for (BigCustomer big : bigCustomers) {
			bigCustomersDto.add(big.toDto());
		}
		logger.warn("[BigCustomerResource]response result , dtos: {}", bigCustomersDto);
		return Response.ok().entity(bigCustomersDto).build();
	}

	
	@GET
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/find/{mcMemberCode}")
	public Response find(@PathParam("mcMemberCode")String mcMemberCode) {
		logger.warn("[BigCustomerResource]request find, mcMemberCode :{}", mcMemberCode);
		BigCustomer bigCustomer = cusAccRespository.findByMcCode(mcMemberCode);
		BigCustomerDto bigCustomerDto = new BigCustomerDto();
		if(bigCustomer!=null){
		   bigCustomerDto=bigCustomer.toDto();
		}
		logger.warn("[BigCustomerResource]response result , dtos: {}", bigCustomerDto);
		return Response.ok().entity(bigCustomerDto).build();
	}
	
	
	@GET
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/queryAll")
	public Response queryAll() {
		List<BigCustomer> bigCustomers = cusAccRespository.queryAll();
		BigCustomersDto bigCustomersDto = new BigCustomersDto();
		for (BigCustomer big : bigCustomers) {
			bigCustomersDto.add(big.toDto());
		}
		logger.info("[queryAll] response result , dtos: {}", bigCustomersDto);
		return Response.ok().entity(bigCustomersDto).build();
	}
	
	
	@POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/update")
    public Response update(BigCustomerDto dto){
        BigCustomer bigCustomer = new BigCustomer(dto);
        cusAccRespository.update(bigCustomer);
        return Response.ok().build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/find/{id}")
    public Response findById(@PathParam("id")String id) {
        logger.warn("[BigCustomerResource]request find, id :{}", id);
        BigCustomer bigCustomer = cusAccRespository.findById(id);
        BigCustomerDto bigCustomerDto = new BigCustomerDto();
        if(bigCustomer!=null){
            bigCustomerDto=bigCustomer.toDto();
        }
        logger.warn("[BigCustomerResource]response result , dtos: {}", bigCustomerDto);
        return Response.ok().entity(bigCustomerDto).build();
    }
	
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/findByCrmId/{crmId}")
    public Response findByCrmId(@PathParam("crmId")String crmId) {
        logger.info("[BigCustomerResource]request findByCrmId, crmId :{}", crmId);
        BigCustomer bigCustomer = cusAccRespository.findByCrmId(crmId);
        BigCustomerDto bigCustomerDto = null;
        if(bigCustomer!=null){
            bigCustomerDto=bigCustomer.toDto();
        }
        logger.info("[BigCustomerResource]response result , dtos: {}", bigCustomerDto);
        return Response.ok().entity(bigCustomerDto).build();
    }
}
