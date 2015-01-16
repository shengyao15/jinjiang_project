package com.jje.membercenter.domain;


import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.score.MemberScoreLevelInfoDto;

public class MemberScoreLevelInfo{

    private Long id;
    private Long memberInfoId;
    private String memberCode;
    private Long availableScore;
    private Long rankScore;
    private Long rankTimeSize;
    private Date updatedDate;
    private Integer scoreLevel;
    private Date scoreMemberLevelIndate;
    private int  scoreType;
    
    public MemberScoreLevelInfo() {}
    
    public MemberScoreLevelInfo(MemberScoreLevelInfoDto dto) {
    	BeanUtils.copyProperties(dto, this);
    }
    
    public MemberScoreLevelInfo(MemberDto dto) {
    	this.memberCode = dto.getMemberCode();
//    	this.scoreLevel = Integer.parseInt(dto.getMemberHierarchy());
    	this.scoreLevel = Integer.parseInt(dto.getNewMemberHierarchy());//新会员层级
    	this.scoreType = dto.getScoreType();
    	this.availableScore = 0L;
    	this.rankScore = 0L;
    	this.rankTimeSize = 0L;
    	this.memberInfoId=dto.getId();
    }

    public int getScoreType() {
        return scoreType;
    }

    public void setScoreType(int scoreType) {
        this.scoreType = scoreType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberInfoId() {
        return memberInfoId;
    }

    public void setMemberInfoId(Long memberInfoId) {
        this.memberInfoId = memberInfoId;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public Long getAvailableScore() {
        return availableScore;
    }

    public void setAvailableScore(Long availableScore) {
        this.availableScore = availableScore;
    }

    public Long getRankScore() {
        return rankScore;
    }

    public void setRankScore(Long rankScore) {
        this.rankScore = rankScore;
    }

    public Long getRankTimeSize() {
        return rankTimeSize;
    }

    public void setRankTimeSize(Long rankTimeSize) {
        this.rankTimeSize = rankTimeSize;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getScoreLevel() {
        return scoreLevel;
    }

    public void setScoreLevel(Integer scoreLevel) {
        this.scoreLevel = scoreLevel;
    }

    public Date getScoreMemberLevelIndate() {
        return scoreMemberLevelIndate;
    }

    public void setScoreMemberLevelIndate(Date scoreMemberLevelIndate) {
        this.scoreMemberLevelIndate = scoreMemberLevelIndate;
    }

    public MemberScoreLevelInfoDto toDto() {
        MemberScoreLevelInfoDto memberScoreLevelInfoDto = new MemberScoreLevelInfoDto();
        BeanUtils.copyProperties(this, memberScoreLevelInfoDto);
        
        return memberScoreLevelInfoDto;
    }
}
