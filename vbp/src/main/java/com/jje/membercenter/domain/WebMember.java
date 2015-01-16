package com.jje.membercenter.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.jje.dto.membercenter.CardType;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.membercenter.TransformType;
import com.jje.dto.membercenter.WebMemberDto;

public class WebMember {
	private long id;
	private String email;
	private String phone;
	private String tempCardNo;// 卡号
    @Deprecated
	private String memberCode;// 会员号
	private Date lastUpdateTime;
	private Date lastLoginTime;
	private String referrerCardNo;
	private boolean isMobileBind;// 手机是否验证
	private boolean isEmailBind;// 邮箱是否验证
	private RegistChannel registChannel;
    private String registTag;
	private Date registTime;
	private String activityCode;
	private String pwd;
	private String userName;
	private String identityType;
	private String identityNo;
	private String question;
	private String answer;
	private String mcMemberCode;
	private String memType;
	private String ipAddress;
	private TransformType transformType;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public WebMemberDto toDto() {
		WebMemberDto dto = new WebMemberDto();
		BeanUtils.copyProperties(this, dto);
		return dto;
	}

    public String getRegistTag() {
        return registTag;
    }

    public void setRegistTag(String registTag) {
        this.registTag = registTag;
    }

    public static WebMember fromDto(WebMemberDto dto) {
		WebMember member = new WebMember();
		BeanUtils.copyProperties(dto, member);
		return member;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTempCardNo() {
		return tempCardNo;
	}

	public void setTempCardNo(String tempCardNo) {
		this.tempCardNo = tempCardNo;
	}

	public String getReferrerCardNo() {
		return referrerCardNo;
	}

	public void setReferrerCardNo(String referrerCardNo) {
		this.referrerCardNo = referrerCardNo;
	}

	public void setMobileBind(boolean mobileBind) {
		isMobileBind = mobileBind;
	}

	public boolean isEmailBind() {
		return isEmailBind;
	}

	public void setEmailBind(boolean emailBind) {
		isEmailBind = emailBind;
	}

	public RegistChannel getRegistChannel() {
		return registChannel;
	}

	public void setRegistChannel(RegistChannel registChannel) {
		this.registChannel = registChannel;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
    @Deprecated
	public String getMemberCode() {
		return memberCode;
	}
    @Deprecated
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIdentityNo() {
		return StringUtils.upperCase(identityNo);
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isMobileBind() {
		return isMobileBind;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	public String getMcMemberCode() {
		return mcMemberCode;
	}

	public void setMcMemberCode(String mcMemberCode) {
		this.mcMemberCode = mcMemberCode;
	}

	public String getMemType() {
		return memType;
	}

	public void setMemType(String memType) {
		this.memType = memType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public TransformType getTransformType() {
		return transformType;
	}

	public void setTransformType(TransformType transformType) {
		this.transformType = transformType;
	}

	public  MemberDto convertMemberDto() {
        MemberDto memberDto = new MemberDto();
        memberDto.setCardType(CardType.PRESENT.name());
        memberDto.setCellPhone(this.getPhone());
        memberDto.setEmail(this.getEmail());
        memberDto.setCardNo(this.getTempCardNo());
        memberDto.setId(this.getId());
        memberDto.setPassword(this.getPwd());
        memberDto.setIdentityType(this.getIdentityType());
        memberDto.setIdentityNo(this.getIdentityNo());
        memberDto.setMemberCode(this.getMemberCode());
        if(StringUtils.isNotBlank(this.getEmail())){
        	memberDto.setFullName(this.getEmail());
        }else{
        	memberDto.setFullName(this.getPhone());
        }
        memberDto.setRegisterDate(this.getRegistTime());
        memberDto.setMemType(this.getMemType());
        memberDto.setMcMemberCode(this.getMcMemberCode());
        memberDto.setIsWebMember(true);
        memberDto.setRemindAnswer(this.getAnswer());
        memberDto.setRemindQuestion(this.getQuestion());
        return memberDto;
    }

	@SuppressWarnings("unchecked")
	public static List<WebMemberDto> toDtos(List<WebMember> webMembers) {
		if (CollectionUtils.isEmpty(webMembers)) {
			return ListUtils.EMPTY_LIST;
		}
		List<WebMemberDto> webMemberDtos = new ArrayList<WebMemberDto>();
		for (WebMember webMember : webMembers) {
			webMemberDtos.add(webMember.toDto());
		}
		return webMemberDtos;
	}

	
}