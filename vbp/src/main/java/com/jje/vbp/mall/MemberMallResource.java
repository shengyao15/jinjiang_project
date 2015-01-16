package com.jje.vbp.mall;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.jje.dto.vbp.mall.DeductResDto;
import com.jje.dto.vbp.mall.QueryScoreDeductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.StringUtils;
import com.jje.dto.vbp.coupon.MallScoreDeductDto;
import com.jje.dto.vbp.coupon.MallScoreDeductResultDto;
import com.jje.membercenter.score.domain.MallScoreDeductResult;
import com.jje.vbp.mall.domain.MallScoreDeductResultCode;
import com.jje.vbp.mall.service.MemberMallService;

@Path("mall")
@Component
public class MemberMallResource {

	private static final Logger logger = LoggerFactory.getLogger(MemberMallResource.class);

	@Autowired
	private MemberMallService memberMallService;

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/mallScoreDeduct")
	public Response exchangeScore(MallScoreDeductDto mallScoreDeductDto) {

		MallScoreDeductResultDto mallScoreDeduct;
		try {
			mallScoreDeduct = memberMallService.mallScoreDeduct(mallScoreDeductDto).toDto();
			return Response.ok(mallScoreDeduct).build();
		} catch (Exception e) {
			logger.error("208积分扣减失败：积分扣减异常mallScoreDeduct({}) error!", mallScoreDeductDto, e);
			return Response.ok(new MallScoreDeductResult(MallScoreDeductResultCode.ERROR_208).toDto()).build();
		}

	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/mallScoreDeductRetry")
	public Response exchangeScoreRetry(MallScoreDeductDto mallScoreDeductDto) {

		MallScoreDeductResultDto mallScoreDeduct;
		try {
			mallScoreDeduct = memberMallService.retryMallScoreDeduct(mallScoreDeductDto).toDto();
			return Response.ok(mallScoreDeduct).build();
		} catch (Exception e) {
			logger.error("208积分扣减失败：积分扣减异常retryMallScoreDeduct({}) error!", mallScoreDeductDto, e);
			return Response.ok(new MallScoreDeductResult(MallScoreDeductResultCode.ERROR_208).toDto()).build();
		}

	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/scoreDeductTimeoutByMinute/{timeoutLimit}")
	public Response scoreDeductTimeoutByMinute(@PathParam("timeoutLimit") String timeoutLimit) {
		int timeoutLimitMinutes = StringUtils.toInt(timeoutLimit, 60);
		try {
			memberMallService.scoreDeductTimeoutByMinute(timeoutLimitMinutes);
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.OK).build();
	}

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryScoreDeduct")
    public Response queryScoreDeduct(QueryScoreDeductDto queryDto){
        DeductResDto result = null;
        try {
            if(queryDto!=null&&queryDto.propertyIsNotNull()){
                result= memberMallService.queryScoreDeduct(queryDto);
            }
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            logger.error("",e);
            return Response.ok().entity(result).build();
        }
    }
    
}
