package actest.member;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.payment.PayResultForBizDto;
import com.jje.membercenter.MemberXmlResource;
import com.jje.membercenter.domain.MemberXml;
import com.jje.membercenter.domain.MemberXmlRepository;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
//@Ignore
public class MemberXmlResourceTest {

    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Autowired
    private MemberXmlRepository memberXmlRepository;

    @Test
    public void should_pay_success_but_regist_fail_when_regist_info_exists() throws Exception {
        ResourceInvokeHandler.InvokeResult result = resourceInvokeHandler.doPost("memberXmlResource", MemberXmlResource.class, "/member/pay?uuid=1001", mockPayResultForBizDto("11223344"), String.class);
        Assert.assertEquals(Response.Status.OK, result.getStatus());
        MemberXml xml = memberXmlRepository.getXml("1001");
        Assert.assertEquals("F", xml.getCallBackFlag());
    }

    @Test
    public void should_pay_success_and_regist_success_when_regist_info_not_exists() throws Exception {
        ResourceInvokeHandler.InvokeResult result = resourceInvokeHandler.doPost("memberXmlResource", MemberXmlResource.class, "/member/pay?uuid=2001", mockPayResultForBizDto("11223344"), String.class);
        Assert.assertEquals(Response.Status.OK, result.getStatus());
        MemberXml xml = memberXmlRepository.getXml("1001");
        Assert.assertEquals("E", xml.getCallBackFlag());
    }

    private PayResultForBizDto mockPayResultForBizDto(String orderNo) {
        PayResultForBizDto payResultForBizDto = new PayResultForBizDto();
        payResultForBizDto.setResult(PayResultForBizDto.Result.TRUE);
        payResultForBizDto.setOrderNo(orderNo);
        payResultForBizDto.setPaymentType(PayResultForBizDto.PaymentType.ALIPAY);
        payResultForBizDto.setPayType(PayResultForBizDto.PayType.ONLINE);
        return payResultForBizDto;
    }
}
