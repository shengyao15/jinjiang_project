/**
 * 
 */
package com.jje.membercenter.domain;

import java.util.Date;

import com.jje.dto.membercenter.MemberOrderNoteDto;

/**
 * @author Z_Xiong
 *
 */
public class MemberOrderNote {

    private long id;
	
	private String orderNo;
	
	private String content;
	
	private String userId;
	
	private String operation;
	
	private String userName;
	
	private String reason;

	private Date createTime;
	
	public MemberOrderNote(){
		
	}
	
	public MemberOrderNote(MemberOrderNoteDto noteDto){
		this.id = noteDto.getId();
		this.orderNo = noteDto.getOrderNo();
		this.content = noteDto.getContent();
		this.userId = noteDto.getUserId();
		this.operation = noteDto.getOperation();
		this.userName = noteDto.getUserName();
		this.reason = noteDto.getReason();
		this.createTime = noteDto.getCreateTime();
	}
	
	public MemberOrderNoteDto toDto(){
		MemberOrderNoteDto note = new MemberOrderNoteDto();
		note.setId(id);
		note.setOrderNo(orderNo);
		note.setContent(content);
		note.setUserId(userId);
		note.setOperation(operation);
		note.setUserName(userName);
		note.setReason(reason);
		note.setReason(reason);
		note.setCreateTime(createTime);
		return note;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
