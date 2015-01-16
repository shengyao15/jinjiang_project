package com.jje.membercenter.persistence;


import com.jje.membercenter.domain.MemberScoreLevelInfo;

public interface MemberScoreLevelInfoMapper {

    MemberScoreLevelInfo queryMemberScoreInfoByMemberId(String memberInfoId);
    
    void  updateMemberAvailableScore(MemberScoreLevelInfo  info);
    
    void insert(MemberScoreLevelInfo info);

    void updateMemberScoreType(MemberScoreLevelInfo  info);
}
