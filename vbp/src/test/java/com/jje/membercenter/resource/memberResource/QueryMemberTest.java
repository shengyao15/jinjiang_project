package com.jje.membercenter.resource.memberResource;

import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.jje.common.utils.AopTargetUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.MemberJREZQueryDto;
import com.jje.dto.membercenter.MemberJREZResultsDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.remote.handler.MemberHandler;
import com.jje.membercenter.service.MemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class QueryMemberTest extends DataPrepareFramework {


	
	@Autowired
    private MemberService memberService;

	@Mock
	private MemberHandler memberHandler;
    
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test   
	public void should_be_success_when_query_member() throws Exception {
		Mockito.when(memberHandler.getAddress(Mockito.any(String.class)))
				.thenReturn("");
		resourceInvokeHandler.setField(memberService, "memberHandler",memberHandler);

		InvokeResult<MemberJREZResultsDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/queryMember",mockMemberJREZQueryDto(), MemberJREZResultsDto.class);
		
		Assert.assertEquals(result.getStatus(), Status.OK);
		resourceInvokeHandler.setField(memberService, "memberHandler",MemberHandler.class);
	}
	
	
	private MemberJREZQueryDto mockMemberJREZQueryDto() {
		MemberJREZQueryDto query = new MemberJREZQueryDto();
		return query;
	}
	
	
}
