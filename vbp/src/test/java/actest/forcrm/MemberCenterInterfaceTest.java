package actest.forcrm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberMemCardDto;
import com.jje.dto.membercenter.MergeMembersDto;
import com.jje.dto.membercenter.forcrm.MemberForCRMReqDto;
import com.jje.dto.membercenter.forcrm.MemberForCRMRespDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.forcrm.MemberCenterInterface;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class MemberCenterInterfaceTest extends DataPrepareFramework {

	@Autowired
	private MemberCenterInterface memberCenterInterface;
	
	@Autowired
	private MemberRepository memberRepository;
	
	
	
	
	@Test
	public void should_mergeMember_success () {		
		Response response =	memberCenterInterface.mergeMember(mockMergeMembersDto());
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		
	}
	
	private MergeMembersDto mockMergeMembersDto(){
		MergeMembersDto dtos=new  MergeMembersDto();
		MemberDto dto=new MemberDto();
		dto.setActivateCode("Activiated");
		dto.setStatus("Active");
		dto.setEmail("_-e@jinjiang-_");
		dto.setCardNo("5600000895");
		dto.setMemberCode("8000183");
		dto.setMemberID("1-XZ2-6");
		dto.setCellPhone("12996300235");
		dtos.getMembers().add(dto);
		MemberDto dto1=new MemberDto();
		dto1.setActivateCode("Activiated");
		dto1.setStatus("Merged");
		dto1.setCellPhone("12996300254");
		dto1.setCardNo("7700017384");
		dto1.setMemberCode("1-46407122");
		dto1.setMemberID("1-RMNYQ");
		dtos.getMembers().add(dto1);
		MemberDto dto2=new MemberDto();
		dto2.setActivateCode("Activiated");
		dto2.setStatus("Merged");
		dto2.setEmail("_-ng-_");
		dto2.setCardNo("5600000873");
		dto2.setMemberCode("8000222");
		dto2.setMemberID("1-NA7-33");
		dtos.getMembers().add(dto2);
		return dtos;
		
	}
	
	
	@Test
	public void storeMemberPassTest() {
		List<Member>  cc = memberRepository.queryByCellphoneOrEmail("baijie_lixx@dell.com");
		MemberForCRMReqDto memberForCRMReqDto = this.initData2();
		Response response =	memberCenterInterface.storeMember(memberForCRMReqDto);
		
//		List<Member>  aa = memberRepository.queryByCellphoneOrEmail("baijie_lixx@dell.com");
//		System.out.println("******************************************");
//		Assert.assertTrue(aa == null || aa.size() == 0);
	}
	
	
	@Test
	public void storeMemberVerifyPassTest() {
		MemberDto memberDto = new MemberDto();
		memberDto.setId(1);
		memberDto.setMemberID("1-18038621");
		memberDto.setMemberCode("1212121212");
		memberDto.setCardNo("1234569991");
		memberDto.setCellPhone("13901010101");
		memberDto.setEmail("username@dell.com");
		memberDto.setPassword("password");
        memberDto.setRegisterTag("1|2|3|");
		//Response response =	memberCenterInterface.storeMemberVerify(memberDto);
		//Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void deleteMemberPassTest() {
		MemberForCRMReqDto memberForCRMReqDto = this.initData();
		Response response =	memberCenterInterface.deleteMember(memberForCRMReqDto);
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void updateMemberInfoTest() {
		MemberForCRMReqDto memberForCRMReqDto = this.updateMemberInfoData();
		Response response =	memberCenterInterface.updateMember(memberForCRMReqDto);
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	private MemberForCRMReqDto updateMemberInfoData(){
		MemberForCRMReqDto memberForCRMReqDto = new MemberForCRMReqDto();
		MemberDto memberDto = new MemberDto();
		memberDto.setMemberID("1-XOZ6I");
		memberDto.setCardNo("3333333336");//电商卡号		
		memberDto.setEmail("lele.cai@jinjiang.com");
		memberDto.setFullName("蔡乐2");		
		memberDto.setMemberCode("1-56592666");
		memberDto.setRegisterDate(new Date());
		memberDto.setScoreType(1);
		memberDto.setLastUpd(new Date());	
		memberDto.setId(233l);
        memberDto.setRegisterTag("1|2|3|");
		memberForCRMReqDto.setMember(memberDto);
		memberForCRMReqDto.setAuthorizationUserName("jinjiangvbp");
		memberForCRMReqDto.setAuthorizationPassword("123456");
		memberForCRMReqDto.setMember(memberDto);
		return memberForCRMReqDto;
	}
	
	
	@Test
	public void updateMemberForActiveTest() {
		MemberForCRMReqDto memberForCRMReqDto = this.updateMemberInfoForActivateData();
		Response response =	memberCenterInterface.updateMember(memberForCRMReqDto);
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	private MemberForCRMReqDto updateMemberInfoForActivateData(){
		MemberForCRMReqDto memberForCRMReqDto = new MemberForCRMReqDto();
		MemberDto memberDto = new MemberDto();
		memberDto.setMemberID("1-XZ2-14");
		memberDto.setCardNo("11578656004");//电商卡号
		memberDto.setCellPhone("193333355456");
		memberDto.setEmail("test2@jinjiang.com");
		memberDto.setFullName("memtest4");
		memberDto.setCardLevel("cardLevel");
		memberDto.setIdentityNo("identityNo");
		memberDto.setIdentityType("identityType");
		memberDto.setMemberHierarchy("1");
		memberDto.setMemberCode("1212121212");
		memberDto.setActivateCode("2323232323");
		memberDto.setMemberType("memberType");
		memberDto.setPassword("password");
		memberDto.setRegisterDate(new Date());
		memberDto.setRegisterSource("regSrc");
		memberDto.setRemindAnswer("remindAnswer");
		memberDto.setRemindQuestion("remindQuestion");
		memberDto.setStatus("status");
		memberDto.setTitle("title");
		memberDto.setScoreType(1);
		memberDto.setLastUpd(new Date());	
		memberDto.setId(10000l);
		List<MemberMemCardDto> cardList = new ArrayList<MemberMemCardDto>();
		MemberMemCardDto memberMemCardDto1 = new MemberMemCardDto();
		memberMemCardDto1.setCardTypeCd("J2 Benefit Card");
		memberMemCardDto1.setxCardNum("H12345869991");
		//memberMemCardDto1.setSource("source");
		memberMemCardDto1.setValidDate(new Date());
		memberMemCardDto1.setDueDate(new Date());
		memberMemCardDto1.setStatus("status");
		cardList.add(memberMemCardDto1);
		memberDto.setCardList(cardList);
		memberForCRMReqDto.setMember(memberDto);
		memberForCRMReqDto.setAuthorizationUserName("jinjiangvbp");
		memberForCRMReqDto.setAuthorizationPassword("123456");
		memberForCRMReqDto.setMember(memberDto);
		return memberForCRMReqDto;
	}	
	
	
	
	@Test
	public void upgradeOrResumePassTest() {//升级续会
		MemberForCRMReqDto memberForCRMReqDto = this.initData();
		Response response =	memberCenterInterface.upgradeOrResume(memberForCRMReqDto);
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void deleteMemberPailTest() {
		MemberForCRMReqDto memberForCRMReqDto = this.initData();
		Response response =	memberCenterInterface.deleteMember(memberForCRMReqDto);
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	private MemberForCRMReqDto initData(){
		MemberForCRMReqDto memberForCRMReqDto = new MemberForCRMReqDto();
		MemberDto memberDto = new MemberDto();
		memberDto.setMemberID("1-XZ2-2");
		memberDto.setCardNo("1234569991CardNo");//电商卡号
		memberDto.setCellPhone("18221094798");
		memberDto.setEmail("baijie_li@dell.com");
		memberDto.setFullName("Jenny");
		memberDto.setCardLevel("cardLevel");
		memberDto.setIdentityNo("identityNo");
		memberDto.setIdentityType("identityType");
		memberDto.setMemberHierarchy("1");
		memberDto.setMemberCode("1212121212");
		memberDto.setActivateCode("2323232323");
		memberDto.setMemberType("memberType");
		memberDto.setPassword("password");
		memberDto.setRegisterDate(new Date());
		memberDto.setRegisterSource("regSrc");
		memberDto.setRemindAnswer("remindAnswer");
		memberDto.setRemindQuestion("remindQuestion");
		memberDto.setStatus("status");
		memberDto.setTitle("title");
		memberDto.setScoreType(1);
		memberDto.setLastUpd(new Date());
		
		List<MemberMemCardDto> cardList = new ArrayList<MemberMemCardDto>();
		MemberMemCardDto memberMemCardDto1 = new MemberMemCardDto();
		memberMemCardDto1.setCardTypeCd("J2 Benefit Card");
		memberMemCardDto1.setxCardNum("1234569991");
		//memberMemCardDto1.setSource("source");
		memberMemCardDto1.setValidDate(new Date());
		memberMemCardDto1.setDueDate(new Date());
		memberMemCardDto1.setStatus("status");
		cardList.add(memberMemCardDto1);
		
		MemberMemCardDto memberMemCardDto2 = new MemberMemCardDto();
		memberMemCardDto2.setCardTypeCd("cardType");
		memberMemCardDto2.setxCardNum("1234569992");
		memberMemCardDto2.setSource("source");
		memberMemCardDto2.setValidDate(new Date());
		memberMemCardDto2.setDueDate(new Date());
		memberMemCardDto2.setStatus("status");
		cardList.add(memberMemCardDto2);
		
		memberDto.setCardList(cardList);
		
		memberForCRMReqDto.setAuthorizationUserName("jinjiangvbp");
		memberForCRMReqDto.setAuthorizationPassword("123456");
		memberForCRMReqDto.setMember(memberDto);
		return memberForCRMReqDto;
	}
	
	private MemberForCRMReqDto initData2(){
		MemberForCRMReqDto memberForCRMReqDto = new MemberForCRMReqDto();
		MemberDto memberDto = new MemberDto();
		memberDto.setMemberID("1-1112-4546781");
		memberDto.setCardNo("12345100001811CardNo");//电商卡号
		memberDto.setCellPhone("18221111487914");
		memberDto.setEmail("baijie_li@dell84314.com");
		memberDto.setFullName("Jenny");
		memberDto.setCardLevel("cardLevel");
		memberDto.setIdentityNo("identityNo");
		memberDto.setIdentityType("identityType");
		memberDto.setMemberHierarchy("1");
		memberDto.setMemberCode("1278787882121121");
		memberDto.setActivateCode("2323232323");
		memberDto.setMemberType("memberType");
		memberDto.setPassword("password");
		memberDto.setRegisterDate(new Date());
		memberDto.setRegisterSource("regSrc");
		memberDto.setRemindAnswer("remindAnswer");
		memberDto.setRemindQuestion("remindQuestion");
		memberDto.setStatus("status");
		memberDto.setTitle("title");
		memberDto.setScoreType(0);
		memberDto.setLastUpd(new Date());
		
		List<MemberMemCardDto> cardList = new ArrayList<MemberMemCardDto>();
		MemberMemCardDto memberMemCardDto1 = new MemberMemCardDto();
		memberMemCardDto1.setCardTypeCd("J2 Benefit Card");
		memberMemCardDto1.setxCardNum("12345869991");
		//memberMemCardDto1.setSource("source");
		memberMemCardDto1.setValidDate(new Date());
		memberMemCardDto1.setDueDate(new Date());
		memberMemCardDto1.setStatus("status");
		cardList.add(memberMemCardDto1);
		
		MemberMemCardDto memberMemCardDto2 = new MemberMemCardDto();
		memberMemCardDto2.setCardTypeCd("cardType");
		memberMemCardDto2.setxCardNum("1234569992");
		memberMemCardDto2.setSource("source");
		memberMemCardDto2.setValidDate(new Date());
		memberMemCardDto2.setDueDate(new Date());
		memberMemCardDto2.setStatus("status");
		cardList.add(memberMemCardDto2);
		
		MemberMemCardDto memberMemCardDto3 = new MemberMemCardDto();
		memberMemCardDto3.setCardTypeCd("ACDH");
		memberMemCardDto3.setxCardNum("1112384444");
		memberMemCardDto3.setSource("source");
		memberMemCardDto3.setValidDate(new Date());
		memberMemCardDto3.setDueDate(new Date());
		memberMemCardDto3.setStatus("status");
		//cardList.add(memberMemCardDto3);
		
		memberDto.setCardList(cardList);
		
		memberForCRMReqDto.setAuthorizationUserName("jinjiangvbp");
		memberForCRMReqDto.setAuthorizationPassword("123456");
		memberForCRMReqDto.setMember(memberDto);
		return memberForCRMReqDto;
	}
/*	public static void main(String[] args){
		MemberCenterInterfaceTest test = new MemberCenterInterfaceTest();
		MemberForCRMReqDto memberForCRMReqDto = test.initData();
		MemberForCRMRespDto MemberForCRMRespDto = new MemberForCRMRespDto("","");
		System.out.println(JaxbUtils.convertToXmlString(memberForCRMReqDto));
		System.out.println(JaxbUtils.convertToXmlString(MemberForCRMRespDto));
	}*/

    @Ignore
	@Test
	public void should_transferMember_success() {
		MemberForCRMReqDto memberForCRMReqDto = this.initData();
		memberForCRMReqDto.getMember().setMemberID("1-XZ9-0");
		memberForCRMReqDto.getMember().setMemberCode("1212121499");
		memberForCRMReqDto.getMember().setCardNo("1234569979CardNo");
		memberForCRMReqDto.getMember().setCellPhone("18221096798");
		memberForCRMReqDto.getMember().setEmail("baijie_li000@dell.com");
		Response response =	memberCenterInterface.addMember(memberForCRMReqDto);
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		MemberForCRMRespDto entity = (MemberForCRMRespDto)response.getEntity();
		Assert.assertEquals("数据插入成功", entity.getStatus());
	}

    @Ignore
	@Test
	public void should_addMember_success() {
		MemberForCRMReqDto memberForCRMReqDto = this.initData();
		memberForCRMReqDto.getMember().setMemberID("1-XZ9-1");
		memberForCRMReqDto.getMember().setMemberCode("1212121500");
		memberForCRMReqDto.getMember().setCardNo("1234569980CardNo");
		memberForCRMReqDto.getMember().setCellPhone("18221095790");
		memberForCRMReqDto.getMember().setEmail("baijie_li0@dell.com");
		Response response =	memberCenterInterface.addMember(memberForCRMReqDto);
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		MemberForCRMRespDto entity = (MemberForCRMRespDto)response.getEntity();
		Assert.assertEquals("数据插入成功", entity.getStatus());
	}
	
	@Test
	public void should_addMember_cardNo_existed_error() {
		MemberForCRMReqDto memberForCRMReqDto = this.initData();
		memberForCRMReqDto.getMember().setMemberID("1-XZ9-2");
		memberForCRMReqDto.getMember().setMemberCode("1212121501");
		memberForCRMReqDto.getMember().setCardNo("1157863310");
		memberForCRMReqDto.getMember().setCellPhone("18221095791");
		memberForCRMReqDto.getMember().setEmail("baijie_li1@dell.com");
		Response response =	memberCenterInterface.addMember(memberForCRMReqDto);
		Assert.assertEquals(Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
		MemberForCRMRespDto entity = (MemberForCRMRespDto)response.getEntity();
		Assert.assertEquals("数据已存在,无法插入", entity.getStatus());
	}
	@Ignore
	@Test
	public void should_addMember_email_existed_error() {
		MemberForCRMReqDto memberForCRMReqDto = this.initData();
		memberForCRMReqDto.getMember().setMemberID("1-XZ9-3");
		memberForCRMReqDto.getMember().setMemberCode("1212121502");
		memberForCRMReqDto.getMember().setCardNo("1234569982CardNo");
		memberForCRMReqDto.getMember().setCellPhone("18221095792");
		memberForCRMReqDto.getMember().setEmail("baijie_li@dell.com");
		Response response =	memberCenterInterface.addMember(memberForCRMReqDto);
		Assert.assertEquals(Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
		MemberForCRMRespDto entity = (MemberForCRMRespDto)response.getEntity();
		Assert.assertEquals("数据已存在,无法插入", entity.getStatus());
	}
	
	@Test
	public void should_addMember_phone_existed_error() {
		MemberForCRMReqDto memberForCRMReqDto = this.initData();
		memberForCRMReqDto.getMember().setMemberID("1-XZ9-4");
		memberForCRMReqDto.getMember().setMemberCode("1212121503");
		memberForCRMReqDto.getMember().setCardNo("1234569983CardNo");
		memberForCRMReqDto.getMember().setCellPhone("18221094798");
		memberForCRMReqDto.getMember().setEmail("baijie_li3@dell.com");
		Response response =	memberCenterInterface.addMember(memberForCRMReqDto);
		Assert.assertEquals(Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
		MemberForCRMRespDto entity = (MemberForCRMRespDto)response.getEntity();
		Assert.assertEquals("数据已存在,无法插入", entity.getStatus());
	}
	
}
