package com.jje.membercenter.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.jje.dto.vbp.member.MemberSubFeedbackDto;

/**
 * @author edison.zuo
 *
 */
public class MemberSubFeedback {
	private Long id;
	private Long feedbackId;
	private String operator;
	private String content;
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static List<MemberSubFeedbackDto> toDtos(List<MemberSubFeedback> feedbacks) {
		if (CollectionUtils.isEmpty(feedbacks)) {
			return new ArrayList<MemberSubFeedbackDto>();
		}
		List<MemberSubFeedbackDto> dtos = new ArrayList<MemberSubFeedbackDto>();
		for (MemberSubFeedback feedback : feedbacks) {
			dtos.add(feedback.toDto());
		}
		return dtos;
	}

	public MemberSubFeedbackDto toDto() {
		MemberSubFeedbackDto dto = new MemberSubFeedbackDto();
		dto.setId(id);
		dto.setFeedbackId(feedbackId);
		dto.setContent(content);
		dto.setOperator(operator);
		dto.setCreateTime(createTime);
		return dto;
	}

	public static MemberSubFeedback fromDto(MemberSubFeedbackDto subFeedbackDto) {
		MemberSubFeedback feedback = new MemberSubFeedback();
		feedback.setId(subFeedbackDto.getId());
		feedback.setFeedbackId(subFeedbackDto.getFeedbackId());
		feedback.setContent(subFeedbackDto.getContent());
		feedback.setOperator(subFeedbackDto.getOperator());
		feedback.setCreateTime(subFeedbackDto.getCreateTime());
		return feedback;
	}
}
