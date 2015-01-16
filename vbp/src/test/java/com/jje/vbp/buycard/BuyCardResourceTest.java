package com.jje.vbp.buycard;

import java.math.BigDecimal;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.membercenter.SaleCardChannel;
import com.jje.dto.membercenter.buyCard.BuyCardRequest;
import com.jje.dto.membercenter.buyCard.BuyCardResponse;
import com.jje.membercenter.DataPrepareFramework;

@Ignore
public class BuyCardResourceTest extends DataPrepareFramework {
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test
	public void should_success_when_exchangeScore() {
		ResourceInvokeHandler.InvokeResult<BuyCardResponse> result;
		try {
			BuyCardRequest buyCardRequest=new BuyCardRequest();
			buyCardRequest=this.buildbuyCardRequest();
			System.out.println(
					JaxbUtils.convertToXmlString(buyCardRequest));
			result =resourceInvokeHandler.doPost("buyCardService", BuyCardResource.class, "/card/prepareBuyCard", buyCardRequest, BuyCardResponse.class);
		Assert.assertEquals(Response.Status.OK, result.getStatus());                 
		Assert.assertEquals("success", result.getOutput());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private BuyCardRequest buildbuyCardRequest() {
		BuyCardRequest buyCardRequest=new BuyCardRequest();
		buyCardRequest.setChannel(SaleCardChannel.Mobile.name());
		buyCardRequest.setAddress("黄陂南路");
		buyCardRequest.setAmount(BigDecimal.valueOf(198));
		buyCardRequest.setCertificateNo("310110198001020012");
		buyCardRequest.setCertificateType("OTHER");
		buyCardRequest.setCityId("1");
		buyCardRequest.setDistrictId("2");
		buyCardRequest.setMcMemberCode("11275");
		buyCardRequest.setMemberType("UPGRADE");
		buyCardRequest.setProvinceId("2");
		buyCardRequest.setUserName("aa");
		buyCardRequest.setTitle("Mr.");
		buyCardRequest.setEmail("aaa@jljl.com");
		buyCardRequest.setPostcode("200000");
		buyCardRequest.setPhone("13521048665");
		buyCardRequest.setMailInvoice(false);
		return buyCardRequest;
	}

}
