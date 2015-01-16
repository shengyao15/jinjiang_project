package com.jje.membercenter.score.resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.ScoreResource;

public class QueryChannelScoreResourceTest extends DataPrepareFramework {
    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;
    
    @Test
    public void should_fail_when_queryChannelScore() {
		ResourceInvokeHandler.InvokeResult<String> result = resourceInvokeHandler
				.doPost("scoreResource", 
						ScoreResource.class, 
						"/score/queryChannelScore", 
						"123123", 
						String.class);
		Assert.assertNotSame("OK", result);
    }
}
