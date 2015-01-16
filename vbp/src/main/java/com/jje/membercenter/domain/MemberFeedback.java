package com.jje.membercenter.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.jje.dto.vbp.member.MemberFeedbackChannel;
import com.jje.dto.vbp.member.MemberFeedbackDto;
import com.jje.dto.vbp.member.MemberFeedbackSources;
import com.jje.dto.vbp.member.MemberFeedbackStatus;

public class MemberFeedback {
	private Long id;
	private String operator;
	private MemberFeedbackChannel channel;
	private MemberFeedbackSources sources;
	private MemberFeedbackStatus status;
	private String mcMemberCode;
	private String feedbackType;
	private String content;
	private String solveMemo;
	private Date createTime;
	private Date updateTime;
	
	private String name;
	private String phone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public MemberFeedbackChannel getChannel() {
		return channel;
	}

	public void setChannel(MemberFeedbackChannel channel) {
		this.channel = channel;
	}

	public MemberFeedbackStatus getStatus() {
		return status;
	}

	public void setStatus(MemberFeedbackStatus status) {
		this.status = status;
	}

	public String getMcMemberCode() {
		return mcMemberCode;
	}

	public void setMcMemberCode(String mcMemberCode) {
		this.mcMemberCode = mcMemberCode;
	}

	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSolveMemo() {
		return solveMemo;
	}

	public void setSolveMemo(String solveMemo) {
		this.solveMemo = solveMemo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public MemberFeedbackSources getSources() {
		return sources;
	}

	public void setSources(MemberFeedbackSources sources) {
		this.sources = sources;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public static List<MemberFeedbackDto> toDtos(List<MemberFeedback> feedbacks) {
		if (CollectionUtils.isEmpty(feedbacks)) {
			return new ArrayList<MemberFeedbackDto>();
		}
		List<MemberFeedbackDto> dtos = new ArrayList<MemberFeedbackDto>();
		for (MemberFeedback feedback : feedbacks) {
			dtos.add(feedback.toDto());
		}
		return dtos;
	}

	public MemberFeedbackDto toDto() {
		MemberFeedbackDto dto = new MemberFeedbackDto();
		dto.setId(id);
		dto.setChannel(channel);
		dto.setSources(sources);
		dto.setFeedbackType(feedbackType);
		dto.setContent(content);
		dto.setOperator(operator);
		dto.setMcMemberCode(mcMemberCode);
		dto.setSolveMemo(solveMemo);
		dto.setStatus(status);
		dto.setUpdateTime(updateTime);
		dto.setCreateTime(createTime);
		dto.setPhone(phone);
		dto.setName(name);
		return dto;
	}

	public static MemberFeedback fromDto(MemberFeedbackDto memberFeedbackDto) {
		MemberFeedback feedback = new MemberFeedback();
		feedback.setId(memberFeedbackDto.getId());
		feedback.setChannel(memberFeedbackDto.getChannel());
		feedback.setSources(memberFeedbackDto.getSources());
		feedback.setFeedbackType(memberFeedbackDto.getFeedbackType());
		feedback.setContent(memberFeedbackDto.getContent());
		feedback.setOperator(memberFeedbackDto.getOperator());
		feedback.setMcMemberCode(memberFeedbackDto.getMcMemberCode());
		feedback.setSolveMemo(memberFeedbackDto.getSolveMemo());
		feedback.setStatus(memberFeedbackDto.getStatus());
		feedback.setUpdateTime(memberFeedbackDto.getUpdateTime());
		feedback.setCreateTime(memberFeedbackDto.getCreateTime());
		feedback.setName(memberFeedbackDto.getName());
		feedback.setPhone(memberFeedbackDto.getPhone());
		return feedback;
	}
}
