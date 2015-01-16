package com.jje.membercenter.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.jje.dto.membercenter.AppMemberDto;
import com.jje.dto.membercenter.MemberCardType;
import com.jje.dto.membercenter.NewCardType;

public class AppMember {

	private Long id;
	
	private String memberId;
	
	private String fullName;
	
	private String phone;
	
	private String email;
	
	private String cardNo;
	
	private String memberCode;
	
	private String cardLevel;
	
	private BigDecimal availableScore;
	
	private String mcMemberCode;
	
	private String memType;
	
	private String identityType;
	
	private String identityNo;
	
	private String mCustomerId;
	
	//大客户渠道
	private String channels;
	
	private List<MemberMemCard> cardList = new ArrayList<MemberMemCard>();
	
    private String memberHierarchy;
    
    private String newMemberHierarchy;
    
    private String registTag;
    
    public AppMember(){}


	public String getRegistTag() {
		return registTag;
	}


	public void setRegistTag(String registTag) {
		this.registTag = registTag;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
	}

	public BigDecimal getAvailableScore() {
		return availableScore;
	}

	public void setAvailableScore(BigDecimal availableScore) {
		this.availableScore = availableScore;
	}
	
	public List<MemberMemCard> getCardList() {
		return cardList;
	}

	public void setCardList(List<MemberMemCard> cardList) {
		this.cardList = cardList;
	}

    public String getMemberHierarchy() {
        return memberHierarchy;
    }

    public void setMemberHierarchy(String memberHierarchy) {
        this.memberHierarchy = memberHierarchy;
    }
    
	public String getNewMemberHierarchy() {
		return newMemberHierarchy;
	}

	public void setNewMemberHierarchy(String newMemberHierarchy) {
		this.newMemberHierarchy = newMemberHierarchy;
	}

	public AppMemberDto toDto() {
		AppMemberDto dto = new AppMemberDto();
		String[] ignores = new String[]{"cardList"};
		BeanUtils.copyProperties(this, dto, ignores);
		List<NewCardType> types = new ArrayList<NewCardType>();
		for(MemberMemCard card : cardList) {
            dto.getCardList().add(card.toDto());
            NewCardType type = NewCardType.valueOfCode(card.getCardTypeCd());
			if( type != null)
				types.add(type);
		}
		dto.setOldCardType(NewCardType.getHighestLevelCardType(types));
		return dto;
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

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public String getmCustomerId() {
		return mCustomerId;
	}

	public void setmCustomerId(String mCustomerId) {
		this.mCustomerId = mCustomerId;
	}

	public String getChannels() {
		return channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}

	@Override
	public String toString() {
		return "AppMember [id=" + id + ", memberId=" + memberId + ", fullName="
				+ fullName + ", phone=" + phone + ", email=" + email
				+ ", cardNo=" + cardNo + ", memberCode=" + memberCode
				+ ", cardLevel=" + cardLevel + ", availableScore="
				+ availableScore + ", mcMemberCode=" + mcMemberCode
				+ ", memType=" + memType + ", identityType=" + identityType
				+ ", identityNo=" + identityNo + ", cardList=" + cardList + "]";
	}
}
