package com.jje.membercenter.xsd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberMemCardDto;
import com.jje.dto.membercenter.MemberVerfyDto;
import com.jje.membercenter.xsd.MemberBasicInfoUpdateResponse.Head;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "head", "body" })
@XmlRootElement(name = "response")
public class MemberInfoUpdateResponse{
	
	@XmlElement(required = true)
	protected MemberBasicInfoUpdateResponse.Head head;
	@XmlElement(required = true)
	protected MemberInfoUpdateResponse.Body body;
	
	public MemberBasicInfoUpdateResponse.Head getHead() {
		return head;
	}

	public void setHead(MemberBasicInfoUpdateResponse.Head head) {
		this.head = head;
	}

	public MemberInfoUpdateResponse.Body getBody() {
		return body;
	}

	public void setBody(MemberInfoUpdateResponse.Body body) {
		this.body = body;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "membid", "recode", "remsg", "member"})
	public static class Body {
		
		@XmlElement(required = true)
		protected String membid;
		@XmlElement(required = true)
		protected String recode;
		@XmlElement(required = true)
		protected String remsg;
		@XmlElement(required = true)
		protected MemberDto member;
		
		public String getMembid() {
			return membid;
		}
		public void setMembid(String membid) {
			this.membid = membid;
		}
		public String getRecode() {
			return recode;
		}
		public void setRecode(String recode) {
			this.recode = recode;
		}
		public String getRemsg() {
			return remsg;
		}
		public void setRemsg(String remsg) {
			this.remsg = remsg;
		}
		public MemberDto getMember() {
			return member;
		}
		public void setMember(MemberDto member) {
			this.member = member;
		}


	}

	@Override
	public String toString() {
		return JaxbUtils.convertToXmlString(this);
	}

	public static void main(String[] args) {
		MemberInfoUpdateResponse response = new MemberInfoUpdateResponse();
		response.setHead(new Head());
		response.getHead().setResptime("test");
		response.getHead().setRetcode("test");
		response.getHead().setRetmsg("test");
		response.getHead().setSystype("test");
		response.getHead().setTranscode("test");
		response.setBody(new Body());
		response.getBody().setMembid("test");
		response.getBody().setRecode("test");
		response.getBody().setRemsg("test");
		MemberDto memberDto = new MemberDto();
		memberDto.setActivateCode("test");
		memberDto.setActiveChannel("test");
		memberDto.setActiveDate(new Date());
		memberDto.setActiveTag("test");
		memberDto.setCardLevel("test");
		memberDto.setCardLevelName("悦享卡");
		List<MemberMemCardDto> cardList = new ArrayList<MemberMemCardDto>();
		MemberMemCardDto memberMemCardDto = new MemberMemCardDto();
		memberMemCardDto.setCardTypeCd("test");
		memberMemCardDto.setCrmKey("test");
		memberMemCardDto.setDtMsg("test");
		memberMemCardDto.setDtStatus("test");
		memberMemCardDto.setDtUpd(new Date());
		memberMemCardDto.setDueDate(new Date());
		memberMemCardDto.setId(1);
		memberMemCardDto.setMemId("test");
		memberMemCardDto.setMemInfoId(1);
		memberMemCardDto.setSource("test");
		memberMemCardDto.setStatus("test");
		memberMemCardDto.setValidDate(new Date());
		memberMemCardDto.setxCardNum("test");
		cardList.add(memberMemCardDto);
		memberDto.setCardList(cardList);
		memberDto.setCardNo("test");
		memberDto.setCardStatus("test");
		memberDto.setCardType("J2 Benefit Card");
		memberDto.setCellPhone("test");
		memberDto.setChannels("test");
		memberDto.setCouponCode("test");
		memberDto.setEmail("test");
		memberDto.setEntityCardNo("test");
		memberDto.setFullName("test");
		memberDto.setId(1);
		memberDto.setIdentityNo("test");
		memberDto.setIdentityType("test");
		memberDto.setIpAddress("test");
		memberDto.setIsWebMember(true);
		memberDto.setLastUpd(new Date());
		memberDto.setMcMemberCode("test");
		memberDto.setmCustomerId("test");
		memberDto.setMemberCode("test");
		memberDto.setMemberHierarchy("test");
		memberDto.setMemberHierarchyName("test");
		memberDto.setMemberID("test");
		memberDto.setMemberType("test");
		List<MemberVerfyDto> memberVerfyList = new ArrayList<MemberVerfyDto>();
		MemberVerfyDto memberVerfyDto = new MemberVerfyDto();
		memberVerfyDto.setId(1);
		memberVerfyDto.setMemId("test");
		memberVerfyDto.setMemInfoId(1);
		memberVerfyDto.setMemNum("test");
		memberVerfyDto.setMenName("test");
		memberVerfyDto.setPassword("test");
		memberVerfyDto.setWebMember(false);
		memberVerfyList.add(memberVerfyDto);
		memberDto.setMemberVerfyList(memberVerfyList);
		memberDto.setMemType("test");
		memberDto.setPassword("test");
		memberDto.setRegisterDate(new Date());
		memberDto.setRegisterSource("test");
		memberDto.setRegisterTag("test");
		memberDto.setRemindAnswer("test");
		memberDto.setRemindQuestion("test");
		memberDto.setScoreType(1);
		memberDto.setStatus("test");
		memberDto.setThirdpartyType("test");
		memberDto.setTitle("test");
		memberDto.setUserName("test");
		memberDto.setValidEndDate("test");
		memberDto.setValidStartDate("test");
		response.getBody().setMember(memberDto);
		System.out.print(response);
	}

}
