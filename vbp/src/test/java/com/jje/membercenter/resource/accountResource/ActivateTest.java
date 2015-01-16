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
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.membercenter.AccountResource;
import com.jje.membercenter.account.xsd.AccountActivationRequest;
import com.jje.membercenter.account.xsd.AccountActivationResponse;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class ActivateTest {
    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Mock
    CRMMembershipProxy spyCRMMembershipProxy;
    
    @Autowired
    CRMMembershipProxy crmMembershipProxy;
    
    @Autowired
    private CRMMembershipRepository crmMembershipRepository;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
   
    private AccountDto getAccountDto() {
        return new AccountDto();
    }

    @Test
    public void should_be_success_when_activate_real_member() throws Exception {
    	Mockito.when(spyCRMMembershipProxy.activateMember(Mockito.any(AccountActivationRequest.class))).thenReturn(getAccountActivationResponse());
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy", spyCRMMembershipProxy);

        InvokeResult<CRMResponseDto> result = resourceInvokeHandler.doPost("accountResource", AccountResource.class, "/account/activate", getAccountDto(), CRMResponseDto.class);
        CRMResponseDto crmResponseDto = result.getOutput();
        Assert.assertEquals("100010", crmResponseDto.getMembid());
        Assert.assertEquals("hello", crmResponseDto.getRetmsg());
        Assert.assertEquals("1", crmResponseDto.getRetcode());
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy", crmMembershipProxy);
    }
    
    private AccountActivationResponse getAccountActivationResponse() {
        AccountActivationResponse accountActivationResponse = new AccountActivationResponse();
        AccountActivationResponse.Body body = new AccountActivationResponse.Body();
        body.setMembid("100010");
        body.setRemsg("hello");
        BigDecimal value = new BigDecimal(1);
        body.setRecode(value.toBigInteger());
        accountActivationResponse.setBody(body);
        return accountActivationResponse;
    }
    
    
}
