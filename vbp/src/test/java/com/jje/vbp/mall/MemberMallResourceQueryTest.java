package com.jje.vbp.mall;

import com.jje.common.utils.AopTargetUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.coupon.issue.CouponSysIssueResult;
import com.jje.dto.coupon.issue.exchange.ExchangeIssueDto;
import com.jje.dto.vbp.coupon.MallScoreDeductDto;
import com.jje.dto.vbp.coupon.MallScoreDeductResultDto;
import com.jje.dto.vbp.mall.DeductResDto;
import com.jje.dto.vbp.mall.QueryScoreDeductDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.remote.crm.support.CrmPassage;
import com.jje.vbp.handler.CbpHandler;
import com.jje.vbp.mall.domain.DeductReturned;
import com.jje.vbp.mall.service.MemberMallService;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;


public class MemberMallResourceQueryTest extends DataPrepareFramework {

    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Autowired
    private MemberMallService mallService;

    @Mock
    private CrmPassage crm;

    @Mock
    private CbpHandler cbpHandler;

    @Before
    public void initMocks() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Ignore
    public void should_be_query_success() throws Exception {
        ResourceInvokeHandler.InvokeResult<DeductResDto> result = resourceInvokeHandler.doPost("memberMallResource", MemberMallResource.class,
                "/mall/queryScoreDeduct", createQueryDto(), DeductResDto.class);
        Assert.assertEquals(result.getOutput().getReCode(), "00001");
    }

    @Test
    public void should_be_query_fail() throws Exception {
        ResourceInvokeHandler.InvokeResult<DeductResDto> result = resourceInvokeHandler.doPost("memberMallResource", MemberMallResource.class,
                "/mall/queryScoreDeduct", createQueryFailDto(), DeductResDto.class);
        Assert.assertEquals(result.getOutput(), null);
    }

    private Object createQueryFailDto() {
        QueryScoreDeductDto dto = new QueryScoreDeductDto();
        dto.setOrderId("ORDERNO222");
        dto.setMemberId("1-756502101");
        return dto;
    }

    private Object createQueryDto() {
        QueryScoreDeductDto dto = new QueryScoreDeductDto();
        dto.setOrderId("ORDERNO");
        dto.setMemberId("1-75650210");
        return dto;
    }

    @After
    public void after() {
        resourceInvokeHandler.resetField(mallService, "crm", CrmPassage.class);
        resourceInvokeHandler.resetField(mallService, "cbpHandler", CbpHandler.class);
    }



}
