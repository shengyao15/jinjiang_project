package com.jje.membercenter.resource.memberResource;

import com.jje.common.utils.AopTargetUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.membercenter.BaseResponse;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.domain.MemberCoupon;
import com.jje.membercenter.domain.MemberCouponRepository;
import junit.framework.Assert;
import org.junit.After;
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
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.membercenter.MemberCouponDto;
import com.jje.membercenter.MemberResource;


@Transactional
public class GetMemberCouponCountTest extends DataPrepareFramework {
   
	@Autowired
	MemberResource memberResource;

    @Autowired
    MemberCouponRepository memberCouponRepository;

    @Mock
    MemberCouponRepository spyMemberCouponRepository;

    @Autowired
    private ResourceInvokeHandler handler;

	@Test
	public void validateMemberPassTest() {
        Mockito.when(spyMemberCouponRepository.getCouponCount(Mockito.any(MemberCoupon.class))).thenReturn(1);
		MemberCouponDto memberCoupon = new MemberCouponDto();
        memberCoupon.setMemberId("20130325");
        memberCoupon.setStatus(0);
        ResourceInvokeHandler.InvokeResult<String> formString = handler.doPost("memberResource", MemberResource.class,
                "/member/getMemberCouponCount", memberCoupon, String.class);
        Assert.assertEquals("1",formString.getOutput());
	}

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberResource), "memberCouponRepositoryImpl", spyMemberCouponRepository);
    }

    @After
    public void clearMocks() throws Exception {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberResource), "memberCouponRepositoryImpl", memberCouponRepository);
    }

}
