package com.jje.membercenter.cardbind;

import com.jje.dto.membercenter.MemberAirLineCompany;
import com.jje.dto.membercenter.MemberScoreType;
import com.jje.dto.membercenter.cardbind.PartnerCardBindDto;
import com.jje.dto.membercenter.score.MemberScoreLevelInfoDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberMemCard;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberScoreLevelInfoRepository;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;


public class CardBindJunitRepositoryTest extends DataPrepareFramework {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberScoreLevelInfoRepository memberScoreLevelInfoRepository;

    private PartnerCardBindDto partnerCardBindDto_SCORE_OK;

    private PartnerCardBindDto partnerCardBindDto_MILEAGE_OK;

    @Before
    public void initMockPartnerCardBindDto_SCORE_OK() {
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
    }


    @Test
    @Transactional
    public void test_memberScoreLevelInfoRepository_updateMemberScoreType() {
        MemberScoreLevelInfoDto scoreLevelInfoDto = new MemberScoreLevelInfoDto();
        scoreLevelInfoDto.setMemberInfoId(201303250L);
        scoreLevelInfoDto.setScoreType(partnerCardBindDto_MILEAGE_OK.getMemberScoreType());
        memberScoreLevelInfoRepository.updateMemberScoreType(scoreLevelInfoDto);
        MemberScoreLevelInfoDto memberScoreLevelInfoDto = memberScoreLevelInfoRepository.getMemberScoreInfo("201303250");
        Assert.assertEquals(1, memberScoreLevelInfoDto.getScoreType());
        Assert.assertEquals(201303250L, memberScoreLevelInfoDto.getMemberInfoId().longValue());
    }


    @Test
    @ExpectedException(DataIntegrityViolationException.class)
    @Transactional
    public void test_memberRepository_storeMemberCard_Fail() throws SQLException {
        MemberMemCard memberCard = new MemberMemCard();
        memberCard.setxCardNum(partnerCardBindDto_MILEAGE_OK.getPartnerCardNo());
        memberCard.setCardTypeCd(partnerCardBindDto_MILEAGE_OK.getPartnerCode().name());
        memberCard.setMemId("1-X123456");
        memberRepository.storeMemberCard(memberCard);
        memberRepository.storeMemberCard(memberCard);
    }
}
