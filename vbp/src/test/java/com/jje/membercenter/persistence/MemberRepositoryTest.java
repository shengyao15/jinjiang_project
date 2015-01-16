package com.jje.membercenter.persistence;

import java.util.Date;

import junit.framework.Assert;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.dto.vbp.sns.ThirdpartyBindType;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class MemberRepositoryTest extends DataPrepareFramework{

    @Autowired
    private MemberRepository memberRepository;
    
    @Test
    public void should_member_exist_by_card_no(){
        String cardNo="1157863308";
        Member member = memberRepository.getMemberByCardNo(cardNo);
        Assert.assertNotNull(member);
        org.junit.Assert.assertThat(member, Is.is(Member.class));
        org.junit.Assert.assertThat(member.getMcMemberCode(), Is.is("3"));
        org.junit.Assert.assertThat(member.getMemberID(), Is.is("1-EZ4C9"));
        org.junit.Assert.assertThat(member.getCardNo(), Is.is("1157863308"));
        org.junit.Assert.assertThat(member.getEmail(), Is.is("chenyongne@126.com"));
        org.junit.Assert.assertThat(member.getCellPhone(), Is.is("13585909080"));
        org.junit.Assert.assertThat(member.getCardList(),IsNull.<Object>notNullValue());
        org.junit.Assert.assertThat(member.getCardType(), IsNull.<Object>nullValue());
    }
    
    @Test 
    public void should_reset_mcCode_success_when_mc_exists(){
    	memberRepository.resetMcMemberCode("3231663103");
    	Assert.assertNull(memberRepository.getMemberByMcMemberCode("3231663103"));
    }

    @Test
	public void storeMemberInfo_test(){
		Member member = new Member();
		member.setActivateCode("Activiated");
		member.setActiveChannel("Website");
		member.setActiveDate(new Date());
		member.setCardLevel("1");
		member.setCardNo("123456");
		member.setCellPhone("112134545");
		member.setEmail("21@q.com");
		member.setFullName("test");
		member.setIdentityNo("12112");
		member.setIdentityType("ID");
		member.setMcMemberCode("10086");
		member.setMemberCode("1231-1");
		member.setMemberID("2222f");
		member.setMemberHierarchy("1");
		member.setStatus("Active");
		member.setCardLevel("1");
		member.setThirdpartyType(ThirdpartyBindType.TENPAY.name());
		memberRepository.storeMemberInfo(member);
		
		Member result = memberRepository.getMemberByCardNo("123456");
		Assert.assertNotNull(result);
		Assert.assertEquals(ThirdpartyBindType.TENPAY.name(), result.getThirdpartyType());
	}
    
    @Test  
    public void should_singleMember_exist_by_card_no(){
    	String cardNo="1157863308";
    	Assert.assertNotNull(memberRepository.querySingleMemberByCardNo(cardNo));
    }
}
