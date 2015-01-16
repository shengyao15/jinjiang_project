package com.jje.membercenter.domain;

import java.util.Date;

import com.jje.dto.membercenter.MemberMemCardDto;
public class MemberMemCard {
	private long id;         
	private long memInfoId;  
	private String memId;    
	private String cardTypeCd;
	private String xCardNum; 
	private String source;   
	private Date validDate;  
	private Date dueDate;    
	private String status;   
	private String crmKey;   
	private String dtStatus; 
	private String dtMsg;    
	private Date dtUpd;
	private String cardNo;// 电商卡号
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMemInfoId() {
		return memInfoId;
	}
	public void setMemInfoId(long memInfoId) {
		this.memInfoId = memInfoId;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getCardTypeCd() {
		return cardTypeCd;
	}
	public void setCardTypeCd(String cardTypeCd) {
		this.cardTypeCd = cardTypeCd;
	}
	public String getxCardNum() {
		return xCardNum;
	}
	public void setxCardNum(String xCardNum) {
		this.xCardNum = xCardNum;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Date getValidDate() {
		return validDate;
	}
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCrmKey() {
		return crmKey;
	}
	public void setCrmKey(String crmKey) {
		this.crmKey = crmKey;
	}
	public String getDtStatus() {
		return dtStatus;
	}
	public void setDtStatus(String dtStatus) {
		this.dtStatus = dtStatus;
	}
	public String getDtMsg() {
		return dtMsg;
	}
	public void setDtMsg(String dtMsg) {
		this.dtMsg = dtMsg;
	}
	public Date getDtUpd() {
		return dtUpd;
	}
	public void setDtUpd(Date dtUpd) {
		this.dtUpd = dtUpd;
	}   
	public MemberMemCard() {
		super();
	}
	public MemberMemCardDto toDto() {
		return  new MemberMemCardDto(id, memInfoId, memId,
				 cardTypeCd,xCardNum,source,validDate,
				 dueDate,status, crmKey,dtStatus,
				 dtMsg,dtUpd) ;
	}
	
	public MemberMemCard(MemberMemCardDto memberMemCardDto) {
		super();
		this.id = memberMemCardDto.getId();
		this.memInfoId = memberMemCardDto.getMemInfoId();
		this.memId = memberMemCardDto.getMemId();
		this.cardTypeCd = memberMemCardDto.getCardTypeCd();
		this.xCardNum = memberMemCardDto.getxCardNum();
		this.source = memberMemCardDto.getSource();
		this.validDate = memberMemCardDto.getValidDate();
		this.dueDate = memberMemCardDto.getDueDate();
		this.status = memberMemCardDto.getStatus();
		this.crmKey = memberMemCardDto.getCrmKey();
		this.dtStatus = memberMemCardDto.getDtStatus();
		this.dtMsg = memberMemCardDto.getDtMsg();
		this.dtUpd = memberMemCardDto.getDtUpd(); 
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
}
