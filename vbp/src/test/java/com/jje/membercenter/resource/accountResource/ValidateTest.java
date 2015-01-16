package com.jje.membercenter.resource.accountResource;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.jje.common.utils.AopTargetUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.AccountDto;
import com.jje.dto.membercenter.CRMActivationRespDto;
import com.jje.membercenter.AccountResource;
import com.jje.membercenter.account.xsd.AccountValidationRequest;
import com.jje.membercenter.account.xsd.AccountValidationResponse;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class ValidateTest {
    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Mock
    CRMMembershipProxy crmMembershipProxySpy;

    
    @Autowired
    CRMMembershipProxy crmMembershipProxy;
    
    
    @Autowired
    private CRMMembershipRepository crmMembershipRepository;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    
    @Test
    public void should_be_success_when_validate_real_member() throws Exception {
        Mockito.when(crmMembershipProxySpy.validateActivationMember(Mockito.any(AccountValidationRequest.class))).thenReturn(getAccountValidationResponse());
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy", crmMembershipProxySpy);

        InvokeResult<CRMActivationRespDto> result = resourceInvokeHandler.doPost("accountResource", AccountResource.class, "/account/validate", getAccountDto(), CRMActivationRespDto.class);
        CRMActivationRespDto crmActivationRespDto = result.getOutput();
        Assert.assertEquals("100010", crmActivationRespDto.getMembrowid());
        Assert.assertEquals("hello", crmActivationRespDto.getRetmsg());
        Assert.assertEquals("1", crmActivationRespDto.getRetcode());

        Mockito.verify(crmMembershipProxySpy).validateActivationMember(Mockito.any(AccountValidationRequest.class));
        
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy", crmMembershipProxy);
    }

    private AccountDto getAccountDto() {
        return new AccountDto();
    }

    private AccountValidationResponse getAccountValidationResponse() {
        AccountValidationResponse accountValidationResponse = new AccountValidationResponse();
        AccountValidationResponse.Body body = new AccountValidationResponse.Body();
        body.setMembrowid("100010");
        body.setRemsg("hello");
        BigDecimal value = new BigDecimal(1);
        body.setRecode(value.toBigInteger());
        accountValidationResponse.setBody(body);
        return accountValidationResponse;
    }

}
