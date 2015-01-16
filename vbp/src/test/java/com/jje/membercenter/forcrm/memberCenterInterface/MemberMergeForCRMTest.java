package com.jje.membercenter.forcrm.memberCenterInterface;

import com.jje.common.utils.AopTargetUtils;
import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.membercenter.AccountMergeDto;
import com.jje.dto.membercenter.MergeMembersDto;
import com.jje.dto.membercenter.forcrm.MemberForCRMRespDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberVerfy;
import com.jje.membercenter.forcrm.MemberCenterInterface;
import com.jje.membercenter.service.MemberService;
import com.jje.membercenter.service.MergeOrderSerive;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 1 合并会员
 *
 */
public class MemberMergeForCRMTest extends DataPrepareFramework {
    @Autowired
    private ResourceInvokeHandler handler;

    @Autowired
    private MemberRepository memberRepository;

    @Mock
    private MergeOrderSerive spyMergeOrderSerive;
    
    @Autowired
    private MergeOrderSerive mergeOrderSerive;

    @Autowired
    private MemberService memberService;

    @Before
    public void initMocks() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.doNothing().when(spyMergeOrderSerive).mergeOrderMcMemberCode(Mockito.any(AccountMergeDto.class));
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberService), "mergeOrderSerive", spyMergeOrderSerive);
    }
    
    @After
    public void clearMocks() throws Exception {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberService), "mergeOrderSerive", mergeOrderSerive);
    }

    private MergeMembersDto mock_crm_merge_data(XmlType type) {
        StringBuilder memberMergeData = new StringBuilder();
        String str = "";
        try {
            InputStream inputStream = null;
            if(type.equals(XmlType.exist)){
                inputStream = this.getClass().getResourceAsStream(
                        "mock_member_merge_data.xml");
            }else{
                inputStream = this.getClass().getResourceAsStream(
                        "mock_member_merge_data_not_exist_verify.xml");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream, "UTF-8"));
            while ((str = reader.readLine()) != null) {
                memberMergeData.append(str);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return JaxbUtils.convertToObject(memberMergeData.toString(), MergeMembersDto.class);
    }
 enum XmlType{
     exist,not_exist
 }
    @Ignore
    public void should_be_merge_member_success_when_given_member_list_and_verify_exist() {
        List<MemberVerfy> mergedMemberVerfyBefore = memberRepository.getMemberVerfyByMemberCode("3-B57184046");
        Assert.assertEquals(3, mergedMemberVerfyBefore.size());
        Member mergeMemberBefore = memberRepository.getMemberByMcMemberCode("10783");
        Assert.assertEquals("Activiate", mergeMemberBefore.getStatus());

        ResourceInvokeHandler.InvokeResult<MemberForCRMRespDto> postResult = handler.doPost("memberCenterInterface", MemberCenterInterface.class,
                "/membercenterinterface/mergeMember", mock_crm_merge_data(XmlType.exist), MemberForCRMRespDto.class);

        Assert.assertEquals(Response.Status.OK, postResult.getStatus());
        Member mergeMember = memberRepository.getMemberByMcMemberCode("10783");
        Assert.assertNull(mergeMember);

        List<MemberVerfy> mergedMemberVerfyAfter = memberRepository.getMemberVerfyByMemberCode("3-B57184046");
        Assert.assertTrue(mergedMemberVerfyAfter.isEmpty());

        List<MemberVerfy> activeMemberVerfyAfter = memberRepository.getMemberVerfyByMemberCode("3-A57184046");
        Assert.assertEquals(3,activeMemberVerfyAfter.size());
        Member activeMemberAfter = memberRepository.getMemberByMcMemberCode("20783");

        Assert.assertTrue(activeMemberAfter.getStatus().equals("Active"));
    }


    @Test
    public void should_be_merge_member_success_when_given_member_list_and_web_member_not_exist_verify() {
        List<MemberVerfy> mergedMemberVerfyBefore = memberRepository.getMemberVerfyByMemberCode("4-A57184046");
        Assert.assertTrue(mergedMemberVerfyBefore.isEmpty());
        Member mergeMemberBefore = memberRepository.getMemberByMcMemberCode("30783");
        Assert.assertNull(mergeMemberBefore);

        ResourceInvokeHandler.InvokeResult<MemberForCRMRespDto> postResult = handler.doPost("memberCenterInterface", MemberCenterInterface.class,
                "/membercenterinterface/mergeMember", mock_crm_merge_data(XmlType.not_exist), MemberForCRMRespDto.class);

        Assert.assertEquals(Response.Status.OK, postResult.getStatus());
        Member mergeMember = memberRepository.getMemberByMcMemberCode("30783");
        Assert.assertEquals("Active", mergeMember.getStatus());
        List<MemberVerfy> activeMemberVerfyAfter = memberRepository.getMemberVerfyByMemberCode("4-A57184046");
        Assert.assertEquals(5,activeMemberVerfyAfter.size());

        Assert.assertEquals("E10ADC3949BA59ABBE56E057F20F883E",activeMemberVerfyAfter.get(0).getPassword());

    }
}
