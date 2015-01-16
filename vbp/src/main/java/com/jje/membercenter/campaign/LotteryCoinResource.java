package com.jje.membercenter.campaign;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import com.jje.dto.membercenter.campaign.LotteryCoinDetailListDto;
import com.jje.dto.membercenter.campaign.LotteryCoinOperateDto;
import com.jje.dto.membercenter.campaign.LotteryCoinOperateResultDto;
import com.jje.membercenter.campaign.domain.LotteryCoin;
import com.jje.membercenter.campaign.domain.LotteryCoinRepository;
import com.jje.membercenter.campaign.service.LotteryCoinService;

@Path("/lotteryCoin")
@Component("lotteryCoinResource")
public class LotteryCoinResource {
	
	@Autowired
	private LotteryCoinService service;
	
	@Autowired
	private LotteryCoinRepository repo;
	
	private static final Logger logger = LoggerFactory.getLogger(LotteryCoinResource.class);
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/increaseOrDecreaseLotteryCoin")
	public Response increaseOrDecreaseLotteryCoin(LotteryCoinOperateDto operate) {
		LotteryCoinOperateResultDto result = new LotteryCoinOperateResultDto();
		try {
			result = service.increaseOrDecreaseLotteryCoin(operate);
		} catch (Exception e) {
			result.serverError();
			logger.error("--------LotteryCoinResource increaseOrDecreaseLotteryCoin Error: ", e);
		}
		return Response.ok().entity(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/queryAvailableLotteryCoin/{mcMemberCode}")
	public Response queryAvailableLotteryCoin (@PathParam("mcMemberCode") String mcMemberCode) {
		try{
			LotteryCoin coin = repo.getLotteryCoin(new LotteryCoinOperateDto(mcMemberCode));
			if(coin == null) {
				coin = new LotteryCoin(mcMemberCode);
			}
			return Response.ok().entity(coin.toDto()).build();
		} catch (Exception e) {
			logger.error("--------LotteryCoinResource queryAvailableLotteryCoin mcMemberCode is "+mcMemberCode+ "Error: ", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/lotteryCoinDetails")
	public Response queryLotteryCoinDetails (LotteryCoinOperateDto operate) {
		try{
			LotteryCoinDetailListDto coinDetails = service.selectCoinDetailsByMemCodeWithOperateCategory(operate.getMcMemberCode(), operate.getLotteryCoinOperateCategory());
			return Response.ok().entity(coinDetails).build();
		} catch (Exception e) {
			logger.error("do queryLotteryCoinDetails error; mcMemberCode="+operate.getMcMemberCode()+" operateCategory="+operate.getLotteryCoinOperateCategory(), e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	

}
