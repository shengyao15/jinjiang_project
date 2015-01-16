package com.jje.vbp.member.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jje.dto.Pagination;
import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.vbp.member.FeedbackStatisticsDto;
import com.jje.dto.vbp.member.MemberFeedbackDto;
import com.jje.dto.vbp.member.MemberFeedbackStatus;
import com.jje.dto.vbp.member.MemberSubFeedbackDto;
import com.jje.membercenter.domain.MemberFeedback;
import com.jje.membercenter.domain.MemberSubFeedback;
import com.jje.membercenter.persistence.MemberFeedbackRepository;
import com.jje.membercenter.persistence.MemberSubFeedbackRepository;

@Service
public class MemberFeedbackService {

	@Autowired
	private MemberFeedbackRepository memberFeedbackRepository;
	
	@Autowired
	private MemberSubFeedbackRepository memberSubFeedbackRepository;

	public ResultMemberDto<MemberFeedbackDto> queryFeedback(QueryMemberDto<MemberFeedbackDto> queryDto) {
		Pagination pagination = queryDto.getPagination();
		if(pagination != null){
			Integer count = memberFeedbackRepository.queryFeedbackCount(queryDto);
			queryDto.getPagination().countRecords(count);
		}
		List<MemberFeedback> feedbacks = memberFeedbackRepository.queryFeedback(queryDto);
		
		ResultMemberDto<MemberFeedbackDto> resultMemberDto = new ResultMemberDto<MemberFeedbackDto>();
		resultMemberDto.setResults(MemberFeedback.toDtos(feedbacks));
		resultMemberDto.setPagination(pagination);
		return resultMemberDto;
	}
	
	public ResultMemberDto<MemberFeedbackDto> queryFeedbackWithSub(QueryMemberDto<MemberFeedbackDto> queryDto) {
		List<MemberFeedback> feedbacks = memberFeedbackRepository.queryFeedback(queryDto);
		List<MemberFeedbackDto> feedbackDtos = MemberFeedback.toDtos(feedbacks);
		for(MemberFeedbackDto feedbackDto: feedbackDtos){
			feedbackDto.setSubFeedbacks(querySubFeedback(feedbackDto.getId()));
		}
		ResultMemberDto<MemberFeedbackDto> resultMemberDto = new ResultMemberDto<MemberFeedbackDto>();
		resultMemberDto.setResults(feedbackDtos);
		resultMemberDto.setPagination(queryDto.getPagination());
		return resultMemberDto;
	}
	
	public MemberFeedbackDto getFeedbackById(Long feedbackId){
		QueryMemberDto<MemberFeedbackDto> queryDto = new QueryMemberDto<MemberFeedbackDto>(null,new MemberFeedbackDto(feedbackId));
		ResultMemberDto<MemberFeedbackDto> feedbackResult = queryFeedback(queryDto);
		List<MemberFeedbackDto> feedbacks = feedbackResult.getResults();
		if (feedbacks != null && feedbacks.size() > 0) {
			MemberFeedbackDto feedbackDto = feedbacks.get(0);
			feedbackDto.setSubFeedbacks(querySubFeedback(feedbackId));
			return feedbackDto;
		}
		return new MemberFeedbackDto();
	}

	private List<MemberSubFeedbackDto> querySubFeedback(Long feedbackId) {
		QueryMemberDto<MemberSubFeedbackDto> subQueryDto = new QueryMemberDto<MemberSubFeedbackDto>(null,new MemberSubFeedbackDto(feedbackId));
		List<MemberSubFeedback> subFeedbacks  = memberSubFeedbackRepository.querySubFeedback(subQueryDto);
		return MemberSubFeedback.toDtos(subFeedbacks);
	}
	
	public FeedbackStatisticsDto queryFeedbackStatistics() {
		MemberFeedbackDto feedbackDto = null;
		Date aMonth = MemberFeedbackDto.getAStartMonth(new Date());
		Date aDay = MemberFeedbackDto.getAStartDay(new Date());

		feedbackDto = new MemberFeedbackDto(MemberFeedbackStatus.PROCESSED, aMonth, new Date());
		QueryMemberDto<MemberFeedbackDto> queryDto = new QueryMemberDto<MemberFeedbackDto>(null, feedbackDto);
		Integer mProcess = memberFeedbackRepository.queryFeedbackCount(queryDto);

		feedbackDto = new MemberFeedbackDto(MemberFeedbackStatus.UNTREATED, aMonth, new Date());
		queryDto = new QueryMemberDto<MemberFeedbackDto>(null, feedbackDto);
		Integer mUnProcess = memberFeedbackRepository.queryFeedbackCount(queryDto);

		feedbackDto = new MemberFeedbackDto(MemberFeedbackStatus.PROCESSED, aDay, new Date());
		queryDto = new QueryMemberDto<MemberFeedbackDto>(null, feedbackDto);
		Integer dProcess = memberFeedbackRepository.queryFeedbackCount(queryDto);

		feedbackDto = new MemberFeedbackDto(MemberFeedbackStatus.UNTREATED, aDay, new Date());
		queryDto = new QueryMemberDto<MemberFeedbackDto>(null, feedbackDto);
		Integer dUnProcess = memberFeedbackRepository.queryFeedbackCount(queryDto);

		feedbackDto = new MemberFeedbackDto(null, aMonth, new Date());
		queryDto = new QueryMemberDto<MemberFeedbackDto>(null, feedbackDto);
		Integer mCount = memberFeedbackRepository.queryFeedbackCount(queryDto);

		feedbackDto = new MemberFeedbackDto(null, aDay, new Date());
		queryDto = new QueryMemberDto<MemberFeedbackDto>(null, feedbackDto);
		Integer dCount = memberFeedbackRepository.queryFeedbackCount(queryDto);
		return new FeedbackStatisticsDto(mProcess, mUnProcess, dProcess, dUnProcess, mCount, dCount);
	}
	
	public void saveFeedback(MemberFeedbackDto memberFeedbackDto) {
		memberFeedbackRepository.insertMemberFeedback(MemberFeedback.fromDto(memberFeedbackDto));
	}

	public void updateFeedback(MemberFeedbackDto memberFeedbackDto) {
		memberFeedbackRepository.updateMemberFeedback(MemberFeedback.fromDto(memberFeedbackDto));
	}

	public void saveSubFeedback(MemberSubFeedbackDto memberSubFeedbackDto) {
		memberSubFeedbackRepository.insertMemberSubFeedback(MemberSubFeedback.fromDto(memberSubFeedbackDto));
	}
}
