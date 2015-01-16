package com.jje.vbp.order;

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

import com.jje.common.bam.BamDataCollector;
import com.jje.common.bam.BamMessage;
import com.jje.common.bam.StatusCode;
import com.jje.common.utils.ExceptionToString;
import com.jje.common.utils.JaxbUtils;
import com.jje.dto.vbp.order.CardOrderDto;
import com.jje.dto.vbp.order.CardOrderResponseCode;
import com.jje.dto.vbp.order.CardOrderResponseDto;
import com.jje.membercenter.domain.MemberCardOrder;
import com.jje.vbp.order.domain.CardOrder;
import com.jje.vbp.order.service.CardOrderService;
import com.jje.vbp.order.service.bean.OrderException;

@Path("/cardOrder")
@Component
public class CardOrderResource {

	private static Logger logger = LoggerFactory.getLogger(CardOrderResource.class);

	@Autowired
	private CardOrderService cardOrderService;
	
	@Autowired
  private BamDataCollector bamDataCollector;    

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/create")
	public Response createOrder(CardOrderDto order) {

		logger.debug("createOrder({}) - start", order);
		try {

			CardOrderResponseDto response = cardOrderService.createOrder(new CardOrder(order));

			logger.debug("createOrder({}) - end", order);
			bamDataCollector.sendMessage(new BamMessage("vbp.cardOrder.create", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(order), JaxbUtils.convertToXmlString(response)));

			return Response.ok().entity(response).build();
		} catch (OrderException e) {
			logger.error("order[{}] 数据校验异常!", order, e);
			bamDataCollector.sendMessage(new BamMessage("vbp.cardOrder.create", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(order), ExceptionToString.toString(e)));
			return Response.ok().entity(new CardOrderResponseDto(e.getCode(), e.getMessage(), null)).build();
		} catch (Exception e) {
			logger.error("order[{}] 下单异常!", order, e);
			bamDataCollector.sendMessage(new BamMessage("vbp.cardOrder.create", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(order), ExceptionToString.toString(e)));
			return Response.ok().entity(new CardOrderResponseDto(CardOrderResponseCode.ERROR, "下单异常", null)).build();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/getOrderForPay/{orderNo}")
	public Response getOrderForPay(@PathParam("orderNo") String orderNo) {

		logger.debug("getOrderForPay({}) - start", orderNo);
		try {

			MemberCardOrder cardOrder = cardOrderService.getMemberCardOrderForPayByOrderNo(orderNo);
			if (cardOrder == null) {
				logger.warn("getOrderForPay({}) NOT_FOUND", orderNo);
				return Response.status(Status.NOT_FOUND).build();
			}
			logger.debug("getOrderForPay({}) - end", orderNo);

			return Response.ok().entity(cardOrder.toDto()).build();
		} catch (Exception e) {
			logger.error("getOrderForPay({}) error!", orderNo, e);
			return Response.status(Status.NOT_ACCEPTABLE).build();
		}

	}

}
