package com.jje.membercenter.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jje.dto.membercenter.score.DataCodeDto;
import com.jje.dto.membercenter.score.MemberScoreLevelInfoDto;
import com.jje.membercenter.domain.MemberScoreLevelInfo;
import com.jje.membercenter.domain.MemberScoreLevelInfoRepository;
import com.jje.membercenter.support.domain.SupportRepository;

@Repository
public class MemberScoreLevelInfoRepositoryImpl implements MemberScoreLevelInfoRepository {
	@Autowired
	private SupportRepository supportRepository;
	
    @Autowired
    private  MemberScoreLevelInfoMapper memberScoreLevelInfoMapper ;

    public MemberScoreLevelInfoDto getMemberScoreInfo(String memberInfoId) {
    	MemberScoreLevelInfoDto memberScoreLevelInfoDto = new MemberScoreLevelInfoDto();
        MemberScoreLevelInfo memberScoreLevelInfo = memberScoreLevelInfoMapper.queryMemberScoreInfoByMemberId(memberInfoId);
        if(memberScoreLevelInfo != null){
	        memberScoreLevelInfoDto=memberScoreLevelInfo.toDto();
	        List<DataCodeDto> dataCodeList=supportRepository.queryDataCodeAll();
	        memberScoreLevelInfoDto.setDataCodeDto(dataCodeList);
        }
        return memberScoreLevelInfoDto;
    }

	public void updateMemberAvailableScore(MemberScoreLevelInfoDto info) {
		memberScoreLevelInfoMapper.updateMemberAvailableScore(new MemberScoreLevelInfo(info));
	}

    public void updateMemberScoreType(MemberScoreLevelInfoDto infoDto) {
        memberScoreLevelInfoMapper.updateMemberScoreType(new MemberScoreLevelInfo(infoDto));
    }
}
