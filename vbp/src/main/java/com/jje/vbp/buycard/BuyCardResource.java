package com.jje.vbp.buycard;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.common.bam.BamDataCollector;
import com.jje.common.bam.BamMessage;
import com.jje.common.bam.StatusCode;
import com.jje.common.utils.ExceptionToString;
import com.jje.common.utils.JaxbUtils;
import com.jje.dto.membercenter.SaleCardChannel;
import com.jje.dto.membercenter.buyCard.BuyCardRequest;
import com.jje.dto.membercenter.buyCard.BuyCardResponse;
import com.jje.dto.vbp.buyCard.BuyCardDto;
import com.jje.dto.vbp.buyCard.BuyCardResultDto;
import com.jje.dto.vbp.buyCard.GetCardPriceDto;
import com.jje.dto.vbp.buyCard.GetCardPriceResultDto;
import com.jje.dto.vbp.buyCard.BuyCardResultDto.StatusMessage;
import com.jje.vbp.buycard.service.BuyCardService;
import com.jje.vbp.handler.CbpHandler;




@Path("card")
@Component
public class BuyCardResource {
	
	@Autowired
	private BuyCardService buyCardService;
    
	@Autowired 
	private CbpHandler cbpHandler;
	
	@Autowired
  private BamDataCollector bamDataCollector;
	
    private final Logger logger = LoggerFactory.getLogger(BuyCardResource.class);
	
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@POST
	@Path("/buyCard")
	public Response buyCard(BuyCardRequest buyCardRequest){
		logger.info("BuyCardResource.buyCard , request : "+JaxbUtils.convertToXmlString(buyCardRequest));
		//根据渠道进行跳转
		BuyCardResponse buyCardResponse = null;
		if(SaleCardChannel.JDDR.name().equals(buyCardRequest.getChannel())){
			buyCardResponse=buyCardService.jddrSaleCard(buyCardRequest);
		}else if(SaleCardChannel.Mobile.name().equals(buyCardRequest.getChannel())){
//			buyCardResponse=buyCardService.mobileSaleCard(buyCardRequest);
		}else{
			buyCardResponse = new BuyCardResponse(BuyCardResponse.FAILURE, "销售渠道不正确", "");
		}
		
		if (buyCardResponse != null && BuyCardResponse.SUCCESS.equals(buyCardResponse.getCode())) {
			bamDataCollector.sendMessage(new BamMessage("vbp.card.buyCard", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(buyCardRequest), JaxbUtils.convertToXmlString(buyCardResponse)));
		} else {
			bamDataCollector.sendMessage(new BamMessage("vbp.card.buyCard", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(buyCardRequest), buyCardResponse == null ? "" : JaxbUtils.convertToXmlString(buyCardResponse)));
		}
		return Response.ok(buyCardResponse).build();
	}
	
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@POST
	@Path("/prepareBuyCard")
	public Response prepareBuyCard(BuyCardRequest buyCardRequest){
        logger.debug("------start send buyCardRequest({}) to device--------", buyCardRequest);
		//根据渠道进行跳转
		BuyCardResponse buyCardResponse = new BuyCardResponse();
		if(SaleCardChannel.Mobile.name().equals(buyCardRequest.getChannel())){
			buyCardResponse=buyCardService.mobilePrepareSaleCard(buyCardRequest);
		}else{
			buyCardResponse = new BuyCardResponse(BuyCardResponse.FAILURE, "销售渠道不正确", "");
		}
		
		if (buyCardResponse != null && BuyCardResponse.SUCCESS.equals(buyCardResponse.getCode())) {
			bamDataCollector.sendMessage(new BamMessage("vbp.card.prepareBuyCard", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(buyCardRequest), JaxbUtils.convertToXmlString(buyCardResponse)));
		} else {
			bamDataCollector.sendMessage(new BamMessage("vbp.card.prepareBuyCard", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(buyCardRequest), buyCardResponse == null ? "" : JaxbUtils.convertToXmlString(buyCardResponse)));
		}
		return Response.ok(buyCardResponse).build();
	}
	
	
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@POST
	@Path("/saveCardOrder")
	public Response saveCardOrder(BuyCardDto buyCardDto){
		logger.info("saveCardOrder 传入的参数为 {}",buyCardDto);
		BuyCardResultDto buyCardResultDto = null;
		try {
		    //创建订单
			buyCardResultDto = buyCardService.createOrder(buyCardDto);
			
			if(!StatusMessage.ok.equals(buyCardResultDto.getMessage())){
			  cbpHandler.unlockCoupon(buyCardDto.getCouponCode()); 
			  bamDataCollector.sendMessage(new BamMessage("vbp.card.saveCardOrder", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(buyCardDto), JaxbUtils.convertToXmlString(buyCardResultDto)));
			}else{
				bamDataCollector.sendMessage(new BamMessage("vbp.card.saveCardOrder", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(buyCardDto), JaxbUtils.convertToXmlString(buyCardResultDto)));
			}
		} catch (Exception e) {
			 logger.error("saveCardOrder({})", buyCardDto==null?null:JaxbUtils.convertToXmlString(buyCardDto), e);
			 //判断释放优惠券
			 if(StringUtils.isNotBlank(buyCardDto.getCouponCode())){
				cbpHandler.unlockCoupon(buyCardDto.getCouponCode()); 
			 }
			 bamDataCollector.sendMessage(new BamMessage("vbp.card.saveCardOrder", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(buyCardDto), ExceptionToString.toString(e)));
			 return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	    return Response.ok(buyCardResultDto).build();
	}
	
	
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@POST
	@Path("/getCardPrice")
	public Response getCardPrice(GetCardPriceDto getCardPriceDto){
		GetCardPriceResultDto getCardPriceResultDto = null;
		try {
			getCardPriceResultDto = buyCardService.getCardPrice(getCardPriceDto);
		} catch (Exception e) {
			 logger.error(e.getMessage());
			 return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	    return Response.ok(getCardPriceResultDto).build();
	}
	
	
	
}
