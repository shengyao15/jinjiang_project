package com.jje.vbp.coupon;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.vbp.coupon.ExchangeScoreDto;
import com.jje.vbp.coupon.service.MemberCouponService;

/**
 * Aready use in product evn;
 * 2013-1-31
 */
@Path("coupon")
@Component
public class MemberCouponResource {

	private final Logger logger = LoggerFactory.getLogger(MemberCouponResource.class);
	
    @Autowired
    private MemberCouponService memberCouponService;
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/exchangeScore")
    public Response exchangeScore(ExchangeScoreDto exchangeScoreDto) {
    	logger.warn("MemberCouponResource.exchangeScore request:" + JaxbUtils.convertToXmlString(exchangeScoreDto));
        String result = memberCouponService.exchangeScore(exchangeScoreDto);
        return Response.ok(result).build();
    }
    
}
