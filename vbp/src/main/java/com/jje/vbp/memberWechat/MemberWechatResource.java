package com.jje.vbp.memberWechat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

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
import com.jje.vbp.memberWechat.domain.MemberWechatDomain;
import com.jje.vbp.memberWechat.service.MemberWechatService;
import com.jje.vbp.order.domain.CardOrder;
import com.jje.vbp.order.service.CardOrderService;
import com.jje.vbp.order.service.bean.OrderException;

@Path("/memberWechat")
@Component
public class MemberWechatResource {

	private static Logger logger = LoggerFactory
			.getLogger(MemberWechatResource.class);

	@Autowired
	private MemberWechatService memberWechatService;

	@GET
	@Path("/getMemberWechatState/{mcCode}")
	public Response getMemberWechatState(@PathParam("mcCode") String mcCode) {
		try {
			if (mcCode == null || mcCode.equals("null")) {
				throw new Exception();
			}
			List<MemberWechatDomain> memberWechatDomains = memberWechatService
					.getMemberWechatByMcCode(mcCode);
			Boolean result = memberWechatDomains == null
					|| memberWechatDomains.size() == 0 ? false : true;
			return Response.ok().entity(result.toString()).build();
		} catch (Exception ex) {
			logger.error("getOrderForPay({}) error!", mcCode, ex);
			return Response.status(Status.NOT_ACCEPTABLE)
					.entity(getErrorStack(ex, -1)).build();
		}

	}

	public static String getErrorStack(Exception e, int length) {
		String error = null;
		if (e != null) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream ps = new PrintStream(baos);
				e.printStackTrace(ps);
				error = baos.toString();
				if (length > 0) {
					if (length > error.length()) {
						length = error.length();
					}
					error = error.substring(0, length);
				}
				baos.close();
				ps.close();
			} catch (Exception e1) {
				error = e.toString();
			}
		}

		return error;
	}
}
