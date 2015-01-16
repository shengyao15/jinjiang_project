package com.jje.membercenter.persistence;

import junit.framework.Assert;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.domain.MemberVerfy;
import com.jje.membercenter.domain.WebMember;

public class WebMemberRepositoryTest extends DataPrepareFramework{

	@Autowired
	private WebMemberRepository webMemberRepository;
	
	@Test
	public void queryTest() {
		MemberVerfy me=webMemberRepository.queryRegisterByCellphoneOrEmail("test");
		Assert.assertNull(me);
	}

    @Test
    public void should_webMember_exist_by_cardNo(){
        String tempCardNo = "10086";
        WebMember webMember = webMemberRepository.getWebMemberByCardNo(tempCardNo);
        Assert.assertNotNull(webMember);
        org.junit.Assert.assertThat(webMember, Is.is(WebMember.class));
        org.junit.Assert.assertThat(webMember.getMcMemberCode(), Is.is("10086"));
        org.junit.Assert.assertThat(webMember.getTempCardNo(), Is.is("10086"));
        org.junit.Assert.assertThat(webMember.getEmail(), Is.is("10086@126.com"));
        org.junit.Assert.assertThat(webMember.getPhone(), Is.is("10086"));
        org.junit.Assert.assertThat(webMember.getMemType(), Is.is("QUICK_REGIST"));
    }
    
}
