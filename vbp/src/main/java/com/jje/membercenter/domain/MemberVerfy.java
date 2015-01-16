package com.jje.membercenter.domain;
import com.jje.dto.membercenter.MemberVerfyDto;

public class MemberVerfy {

	private long id;        
	private long memInfoId; 
	private String memId;   
	private String memNum;  
	private String menName; 
	private String password;
	private Boolean isWebMember;
	
	
	public MemberVerfy(long id, long memInfoId, String memId, String memNum,
			String menName, String password) {
		super();
		this.id = id;
		this.memInfoId = memInfoId;
		this.memId = memId;
		this.memNum = memNum;
		this.menName = menName;
		this.password = password;
	}
	
	public MemberVerfy(long memInfoId, String memId, String memNum,
			String menName, String password) {
		super();
		this.memInfoId = memInfoId;
		this.memId = memId;
		this.memNum = memNum;
		this.menName = menName;
		this.password = password;
	}
	
	public MemberVerfy() {
		super();
	}
	public MemberVerfy(MemberVerfyDto memberVerfyDto) {
		super();
		this.id = memberVerfyDto.getId();
		this.memInfoId = memberVerfyDto.getMemInfoId();
		this.memId = memberVerfyDto.getMemId();
		this.memNum = memberVerfyDto.getMemNum();
		this.menName = memberVerfyDto.getMenName();
		this.password = memberVerfyDto.getPassword();
	}
	
	public MemberVerfyDto toDto() {
		return  new MemberVerfyDto(id, memInfoId, memId, memNum,
				menName, password) ;
	}
	
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
	public String getMemNum() {
		return memNum;
	}
	public void setMemNum(String memNum) {
		this.memNum = memNum;
	}
	public String getMenName() {
		return menName;
	}
	public void setMenName(String menName) {
		this.menName = menName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean isWebMember() {
		return isWebMember;
	}
	public void setWebMember(Boolean isWebMember) {
		this.isWebMember = isWebMember;
	}

}
