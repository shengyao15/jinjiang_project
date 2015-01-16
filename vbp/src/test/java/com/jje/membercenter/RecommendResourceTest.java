package com.jje.membercenter;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;

public class RecommendResourceTest  extends DataPrepareFramework {

    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Test
    public void test_success_recommendRegisterCoupon() throws Exception {
        @SuppressWarnings("rawtypes")
		ResourceInvokeHandler.InvokeResult result = resourceInvokeHandler.doGet("recommendResource",RecommendResource.class, "/recommend/recommendRegisterCoupon/5",null);
        Assert.assertEquals(Response.Status.OK, result.getStatus());
    }
}
