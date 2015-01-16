package actest.cardbind;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.membercenter.MemberAirLineCompany;
import com.jje.dto.membercenter.MemberScoreType;
import com.jje.dto.membercenter.cardbind.CardBindStatusDto;
import com.jje.dto.membercenter.cardbind.PartnerCardBindDto;
import com.jje.dto.membercenter.score.MemberScoreLevelInfoDto;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.domain.MemberMemCard;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberScoreLevelInfoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class CrmJunitTest {

    @Autowired
    private MemberRepository memberRepository;

    PartnerCardBindDto partnerCardBindDto_MILEAGE_OK;

    PartnerCardBindDto partnerCardBindDto_MILEAGE_FAIL;

    @Before
    public void initMockPartnerCardBindDto_SCORE_OK(){
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
    }

    @Test
    public void test_memberRepository_saveToCrm_SUCCESS(){
        try {
            System.out.println("=========partnerCardBindDto_OK xml========="+JaxbUtils.convertToXmlString(partnerCardBindDto_MILEAGE_OK));
            Assert.assertEquals("the result is True:",memberRepository.partnerCardBind(partnerCardBindDto_MILEAGE_OK),Boolean.FALSE.booleanValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_memberRepository_saveToCrm_FAIL(){
        try {
            Assert.assertEquals("the result is False:",memberRepository.partnerCardBind(partnerCardBindDto_MILEAGE_FAIL),Boolean.FALSE.booleanValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
