package com.jje.membercenter.domain;

import com.jje.dto.membercenter.score.MemberScoreLevelInfoDto;

public interface MemberScoreLevelInfoRepository
{

    MemberScoreLevelInfoDto getMemberScoreInfo(String memberInfoId);
    
    void  updateMemberAvailableScore(MemberScoreLevelInfoDto  info);

    void updateMemberScoreType(MemberScoreLevelInfoDto info);
}
