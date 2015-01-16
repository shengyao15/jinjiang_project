package actest.member;

import com.jje.common.utils.DateUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.membercenter.BaseResponse;
import com.jje.dto.membercenter.MemberUpdateDto;
import com.jje.dto.membercenter.SellChannel;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response.Status;
import java.util.Date;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class UpgradeVIPCardInfoForGatewayTest extends DataPrepareFramework {
	
	@Autowired
    ResourceInvokeHandler handler;
	
	@Test
	public void should_upgradeVIP_success() {
        ResourceInvokeHandler.InvokeResult<BaseResponse> postResult = handler.doPost("memberResource", MemberResource.class, "/member/upgradeVIPCardInfoForGateway", mockUpdateDto(), BaseResponse.class);
		BaseResponse baseResponse = postResult.getOutput();
        Assert.assertTrue(postResult.getStatus() == Status.OK);
        Assert.assertThat(baseResponse, IsNull.<BaseResponse>notNullValue());
        Assert.assertThat(baseResponse.getStatus(),Is.is(BaseResponse.Status.SUCCESS));
	}

    private MemberUpdateDto mockUpdateDto() {
        MemberUpdateDto dto = new MemberUpdateDto("", "7000009586", "", DateUtils.formatDate(new Date(),"yyyyMMdd-HHmmss"), SellChannel.Ikamobile.name(), "", "小黑", "250", "升级了", "Y", "N", "221042", "JJ Card", "J Benefit Card");
        return dto;
    }

}
