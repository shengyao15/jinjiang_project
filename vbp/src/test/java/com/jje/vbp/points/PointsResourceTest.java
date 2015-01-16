package com.jje.vbp.points;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.vbp.MemberPointsDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.vbp.points.domain.MemberPoints;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;

public class PointsResourceTest extends DataPrepareFramework {

    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;


    @Test
    public void query_member_number_return_member_name_success() {
        ResourceInvokeHandler.InvokeResult<MemberPointsDto> result =
                resourceInvokeHandler.doGet("pointsResource",PointsResource.class,"/points/queryMemberPoints/987654",MemberPointsDto.class);

        System.out.println(JaxbUtils.convertToXmlString(result.getOutput()));
        Assert.assertEquals(Response.Status.OK, result.getStatus());
        Assert.assertEquals("黃維哲", result.getOutput().getName());
    }

}
