package com.jje.membercenter.persistence;

import com.jje.membercenter.DataPrepareFramework;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.membercenter.domain.MemberXml;
import com.jje.membercenter.domain.MemberXmlRepository;

@Transactional
public class MemberXmlRepositoryImplTest extends DataPrepareFramework {

	@Autowired
	private MemberXmlRepository repository;

	@Test
	public void testPersistMemberXml() {
		MemberXml memberXml = new MemberXml();
		memberXml.setId("1");
		memberXml.setCallBackFlag("N");
		memberXml.setCertificateNo("123456");
		memberXml.setCertificateType("");
		MemberRegisterDto regDto = new MemberRegisterDto();
		regDto.setStauts(0);
		regDto.setNeedMailCard(true);
        repository.saveXml(memberXml);
        MemberXml memberXmlFromDataBase = repository.getXml("1");
        Assert.assertEquals("123456",memberXmlFromDataBase.getCertificateNo());
        Assert.assertEquals("N",memberXmlFromDataBase.getCallBackFlag());
	}

	@Test
	public void testUpdateCallBackFlag() {
        MemberXml memberXml = new MemberXml();
        memberXml.setId("20130325");
        memberXml.setCallBackFlag("N");
        memberXml.setCertificateNo("123456");
        memberXml.setCertificateType("");
        MemberRegisterDto regDto = new MemberRegisterDto();
        regDto.setStauts(0);
        regDto.setNeedMailCard(true);
        repository.saveXml(memberXml);
        MemberXml memberXmlBefore = repository.getXml("20130325");
        Assert.assertEquals("N",memberXmlBefore.getCallBackFlag());
		repository.updateCallBackFlag("20130325");
        MemberXml memberXmlFromAfter = repository.getXml("20130325");
        Assert.assertEquals("Y",memberXmlFromAfter.getCallBackFlag());
	}

}
