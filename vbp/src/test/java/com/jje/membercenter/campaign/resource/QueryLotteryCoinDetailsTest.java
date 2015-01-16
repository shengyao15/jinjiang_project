package com.jje.membercenter.campaign.resource;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.jje.membercenter.DataPrepareFramework;
import org.hamcrest.core.Is;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.VirtualDispatcherService;
import com.jje.dto.membercenter.campaign.LotteryCoinDetailDto;
import com.jje.dto.membercenter.campaign.LotteryCoinDetailListDto;
import com.jje.dto.membercenter.campaign.LotteryCoinOperateCategory;
import com.jje.dto.membercenter.campaign.LotteryCoinOperateDto;
import com.jje.membercenter.campaign.LotteryCoinResource;

public class QueryLotteryCoinDetailsTest extends DataPrepareFramework {
	
	@Autowired
	private VirtualDispatcherService virtualDispatcherService;
	
	@Test
	public void should_get_coin_details_by_mcMemCode_and_operateCategory() throws Exception{
		String mcMemberCode = "1-19056406";
		LotteryCoinOperateCategory operateCategory = LotteryCoinOperateCategory.RECOMMEND_FRIEND;
		
		LotteryCoinDetailListDto coinDetailListDto = this.doRequest(this.mockCoinOperateCondition(mcMemberCode,operateCategory));
        Assert.assertNotNull(coinDetailListDto);

        List<LotteryCoinDetailDto> coinDetails = coinDetailListDto.getCoinDetails();
        Assert.assertNotNull(coinDetails);

        Assert.assertThat(coinDetails.size(), Is.is(2));

        LotteryCoinDetailDto coinDetails_1 = coinDetails.get(0);
        Assert.assertThat(coinDetails_1.getMcMemberCode(), Is.is(mcMemberCode));
        Assert.assertThat(coinDetails_1.getOperateCategory(), Is.is(operateCategory));
        Assert.assertThat(coinDetails_1.getCoinQuantity(), Is.is(4L));
	}

	private LotteryCoinDetailListDto doRequest(LotteryCoinOperateDto lotteryCoinOperateDto) throws URISyntaxException,UnsupportedEncodingException {
		String content = JaxbUtils.convertToXmlString(lotteryCoinOperateDto);
		
		MockHttpRequest request = MockHttpRequest.post("/lotteryCoin/lotteryCoinDetails");
		request.content(content.getBytes("UTF-8"));
		request.contentType(MediaType.APPLICATION_XML);
		MockHttpResponse response = new MockHttpResponse();
		virtualDispatcherService.getDispatcher("lotteryCoinResource",  LotteryCoinResource.class).invoke(request, response);
		
		LotteryCoinDetailListDto coinDetailListDto =JaxbUtils.convertToObject(response.getContentAsString(), LotteryCoinDetailListDto.class);
		
		return coinDetailListDto;
	}
	
	private LotteryCoinOperateDto mockCoinOperateCondition(String mcMemberCode,LotteryCoinOperateCategory operateCategory){
		LotteryCoinOperateDto condition = new LotteryCoinOperateDto();
		condition.setMcMemberCode(mcMemberCode);
		condition.setLotteryCoinOperateCategory(operateCategory);
		return condition;
	}
	

}
