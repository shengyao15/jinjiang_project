package com.jje.membercenter.cardbind;

import com.jje.dto.membercenter.MemberAirLineCompany;
import com.jje.dto.membercenter.MemberScoreType;
import com.jje.dto.membercenter.cardbind.PartnerCardBindDto;
import com.jje.dto.membercenter.score.MemberScoreLevelInfoDto;
import com.jje.membercenter.domain.MemberMemCard;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberScoreLevelInfoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class IsExistAirAndCardNoRepTest {


    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void test_memberRepository_existAirAndCardNo(){
       Assert.assertTrue(memberRepository.isExistAirAndCardNo("7000005582", "ACDH"));
    }

    @Test
    public void test_memberRepository_notExistAirAndCardNo(){
       Assert.assertFalse(memberRepository.isExistAirAndCardNo("6000005581", "ACDH"));
    }

}
