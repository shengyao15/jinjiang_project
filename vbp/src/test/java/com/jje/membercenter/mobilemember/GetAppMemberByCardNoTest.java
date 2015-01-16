package com.jje.membercenter.mobilemember;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.membercenter.AppMemberDto;
import com.jje.dto.membercenter.score.DataConstant;
import com.jje.dto.membercenter.score.MemberScoreLevelInfoDto;
import com.jje.membercenter.AppMemberResource;
import com.jje.membercenter.DataPrepareFramework;

public class GetAppMemberByCardNoTest extends DataPrepareFramework{
	
	@Autowired
    private AppMemberResource appMemberResource;
	
	@Test
	public void test_fullMember_getAppMemberByCardNo() {
		Response res = appMemberResource.getAppMemberByCode("10086", false);
		
		Assert.assertTrue(res.getStatus() == Status.OK.getStatusCode());
		Assert.assertTrue(res.getEntity() != null);
		AppMemberDto dto = (AppMemberDto) res.getEntity();
		System.out.println(JaxbUtils.convertToXmlString(dto));
		MemberScoreLevelInfoDto memberScoreLevelInfoDto=dto.getMemberScoreLevelInfoDto();
		String val=dto.getMemberScoreLevelInfoDto().getUpgradeScores( DataConstant.PLATINUM_UPGRADE_SCORES.name());
		System.out.println("val="+val);
		Assert.assertNotNull(dto.getFullName());
		Assert.assertTrue(dto.getCardLevel().equals("经典礼卡"));
	}
	
	@Test
	public void test_proMember_getAppMemberByCardNo() {
		Response res = appMemberResource.getAppMemberByCode("10086", true);
		Assert.assertTrue(res.getStatus() == Status.OK.getStatusCode());
		Assert.assertTrue(res.getEntity() != null);
		AppMemberDto dto = (AppMemberDto) res.getEntity();
		Assert.assertNull(dto.getFullName());
	}
	
	@Test
	public void test_not_found_getAppMemberByCardNo() {
		Response res = appMemberResource.getAppMemberByCode("11", true);
		Assert.assertTrue(res.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}
}
