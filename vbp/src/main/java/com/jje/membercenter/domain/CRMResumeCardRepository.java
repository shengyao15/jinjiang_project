package com.jje.membercenter.domain;

import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.ResumeCardDto;

public interface CRMResumeCardRepository
{
	CRMResponseDto resumeCard(ResumeCardDto resumeCardDto) throws Exception;
}
