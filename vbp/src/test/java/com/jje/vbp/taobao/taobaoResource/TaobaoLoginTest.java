package com.jje.vbp.taobao.taobaoResource;

import com.jje.common.utils.AopTargetUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.member.taobao.TaobaoErrorMsg;
import com.jje.dto.member.taobao.TaobaoLoginDto;
import com.jje.dto.member.taobao.TaobaoNotifyDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.vbp.taobao.TaobaoResource;
import com.jje.vbp.taobao.domain.TaobaoRepository;
import com.jje.vbp.taobao.proxy.TaobaoProxy;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import javax.ws.rs.core.Response;

public class TaobaoLoginTest extends DataPrepareFramework {
    @Autowired
	private ResourceInvokeHandler resourceInvokeHandler;
    @Autowired
    private TaobaoProxy taobaoProxy;
    @Mock
    private TaobaoProxy spyTaobaoProxy;
    @Autowired
    private TaobaoRepository taobaoRepository;

    @Before
	public void initMocks() throws Exception {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(taobaoRepository), "taobaoProxy", spyTaobaoProxy);
        Mockito.when(spyTaobaoProxy.notify(Mockito.any(TaobaoNotifyDto.class))).thenReturn(Boolean.TRUE);
	}

	@After
	public void clearMocks() throws Exception {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(taobaoRepository), "taobaoProxy", taobaoProxy);
	}

    @Test
    public void should_success_when_login() {
        ResourceInvokeHandler.InvokeResult<String> result = resourceInvokeHandler.doPost("taobaoResource", TaobaoResource.class,
				"/taobao/login", buildSuccessTaobaoLoginDto(), String.class);
		Assert.assertEquals(result.getStatus(), Response.Status.OK);
        Assert.assertEquals(TaobaoErrorMsg.SUCCESS.toString(),result.getOutput());
    }

    @Test
    public void should_success_when_renotiry() {
        ResourceInvokeHandler.InvokeResult<String> result = resourceInvokeHandler.doPost("taobaoResource", TaobaoResource.class,
				"/taobao/login", buildRenotifyDto(), String.class);
		Assert.assertEquals(result.getStatus(), Response.Status.OK);
        Assert.assertEquals(TaobaoErrorMsg.SUCCESS.toString(),result.getOutput());
    }

    @Test
    public void should_fail_when_TBID_USED() {
        ResourceInvokeHandler.InvokeResult<String> result = resourceInvokeHandler.doPost("taobaoResource", TaobaoResource.class,
				"/taobao/login", buildTBID_USEDDto(), String.class);
		Assert.assertEquals(result.getStatus(), Response.Status.OK);
        Assert.assertEquals(TaobaoErrorMsg.TBID_USED.toString(),result.getOutput());
    }

    @Test
    public void should_fail_when_BINDED() {
        ResourceInvokeHandler.InvokeResult<String> result = resourceInvokeHandler.doPost("taobaoResource", TaobaoResource.class,
				"/taobao/login", buildBINDEDDto(), String.class);
		Assert.assertEquals(result.getStatus(), Response.Status.OK);
        Assert.assertEquals(TaobaoErrorMsg.BINDED.toString(),result.getOutput());
    }

    private TaobaoLoginDto buildBINDEDDto() {
        TaobaoLoginDto dto = new TaobaoLoginDto();
        dto.setLoginName("memtest@jinjiang.com");
        dto.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
        dto.setTbLoginId("102");
        return dto;
    }

    private TaobaoLoginDto buildTBID_USEDDto() {
        TaobaoLoginDto dto = new TaobaoLoginDto();
        dto.setLoginName("1157863310");
        dto.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
        dto.setTbLoginId("101");
        return dto;
    }

    private TaobaoLoginDto buildRenotifyDto() {
        TaobaoLoginDto dto = new TaobaoLoginDto();
        dto.setLoginName("chenyongne@126.com");
        dto.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
        dto.setTbLoginId("100");
        return dto;
    }

    private TaobaoLoginDto buildSuccessTaobaoLoginDto() {
        TaobaoLoginDto dto = new TaobaoLoginDto();
        dto.setLoginName("13585909081");
        dto.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
        dto.setTbLoginId("10001");
        return dto;
    }

}
