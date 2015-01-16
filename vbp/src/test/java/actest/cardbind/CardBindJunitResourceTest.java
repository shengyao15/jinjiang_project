package actest.cardbind;


import com.jje.dto.membercenter.MemberAirLineCompany;
import com.jje.dto.membercenter.MemberScoreType;
import com.jje.dto.membercenter.cardbind.CardBindStatusDto;
import com.jje.dto.membercenter.cardbind.PartnerCardBindDto;
import com.jje.membercenter.MemberResource;
import org.junit.Assert;
import org.junit.Before;
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
public class CardBindJunitResourceTest {

     @Autowired
    private MemberResource memberResource;


    PartnerCardBindDto partnerCardBindDto_SCORE_OK;

    PartnerCardBindDto partnerCardBindDto_MILEAGE_OK;

    PartnerCardBindDto partnerCardBindDto_MILEAGE_FAIL;

    PartnerCardBindDto partnerCardBindDto_SCORE_FAIL;

    @Before
    public void initMockPartnerCardBindDto_SCORE_OK(){
        partnerCardBindDto_SCORE_OK = new PartnerCardBindDto();
        partnerCardBindDto_SCORE_OK.setMcMemberCode("3");
        partnerCardBindDto_SCORE_OK.setPartnerCardNo("1234598765");
        partnerCardBindDto_SCORE_OK.setPartnerCode(MemberAirLineCompany.ACDH);
        partnerCardBindDto_SCORE_OK.setPartnerFlag(MemberScoreType.SCORE);

        partnerCardBindDto_MILEAGE_OK = new PartnerCardBindDto();
        partnerCardBindDto_MILEAGE_OK.setMcMemberCode("3");
        partnerCardBindDto_MILEAGE_OK.setPartnerCardNo("7000005512");
        partnerCardBindDto_MILEAGE_OK.setPartnerCode(MemberAirLineCompany.ACDH);
        partnerCardBindDto_MILEAGE_OK.setPartnerFlag(MemberScoreType.MILEAGE);

        partnerCardBindDto_MILEAGE_FAIL = new PartnerCardBindDto();
        partnerCardBindDto_MILEAGE_FAIL.setMcMemberCode("3");
        partnerCardBindDto_MILEAGE_FAIL.setPartnerCardNo("6000005222");
        partnerCardBindDto_MILEAGE_FAIL.setPartnerCode(MemberAirLineCompany.ACDH);
        partnerCardBindDto_MILEAGE_FAIL.setPartnerFlag(MemberScoreType.MILEAGE);

        partnerCardBindDto_SCORE_FAIL = new PartnerCardBindDto();
        partnerCardBindDto_SCORE_FAIL.setMcMemberCode("3");
        partnerCardBindDto_SCORE_FAIL.setPartnerCardNo("6000005222");
        partnerCardBindDto_SCORE_FAIL.setPartnerCode(MemberAirLineCompany.ACDH);
        partnerCardBindDto_SCORE_FAIL.setPartnerFlag(MemberScoreType.SCORE);
    }

     @Test
    public void test_memberResource_partnerCardBind_MILEAGE_OK(){
         CardBindStatusDto statusDto = callPartnerCardBind(partnerCardBindDto_MILEAGE_OK);
         Assert.assertEquals("statusDto is SUCCESS:", statusDto.getStatus().getAlias(), "-1");
     }

    @Test
    public void test_memberResource_partnerCardBind_MILEAGE_FAIL(){
      CardBindStatusDto statusDto = callPartnerCardBind(partnerCardBindDto_MILEAGE_FAIL);
      Assert.assertEquals("statusDto is FAIL:",statusDto.getStatus().getAlias(),"-1");
    }


     @Test
    public void test_memberResource_partnerCardBind_SCORE_OK(){
         CardBindStatusDto statusDto = callPartnerCardBind(partnerCardBindDto_SCORE_OK);
         Assert.assertEquals("statusDto is SUCCESS:",statusDto.getStatus().getAlias(),"-1");
     }

    @Test
    public void test_memberResource_partnerCardBind_SCORE_FAIL(){
      CardBindStatusDto statusDto = callPartnerCardBind(partnerCardBindDto_SCORE_FAIL);
      Assert.assertEquals("statusDto is FAIL:",statusDto.getStatus().getAlias(),"-1");
    }

    private CardBindStatusDto callPartnerCardBind(PartnerCardBindDto partnerCardBindDto) {
        Response response = memberResource.partnerCardBind(partnerCardBindDto);
        Assert.assertEquals("response status is ok:", response.getStatus(), Response.Status.OK.getStatusCode());
        Assert.assertNotNull("response is not null:", response.getEntity());
        return (CardBindStatusDto) response.getEntity();
    }

    
}
