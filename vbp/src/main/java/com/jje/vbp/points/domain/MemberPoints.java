package com.jje.vbp.points.domain;


import com.jje.dto.vbp.MemberPointsDto;
import org.springframework.beans.BeanUtils;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class MemberPoints {

    private String name;
    //对应数据库里的TEXT_1
    private String memberNumber;
    private String cardNum;
    private String perTitle;
    private String email;
    private String tier;
    private String highestType;
    private Long points;
    private String pointExpiryDt;
    private Long redeemedPoints;
    private Long tierUpPoints;
    private Long tierUpTimes;
    private Date toDate;
    private Long changedPoints;
    private Long aquiredPoints;
    private String mcMemberCode;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getPerTitle() {
        return perTitle;
    }

    public void setPerTitle(String perTitle) {
        this.perTitle = perTitle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getHighestType() {
        return highestType;
    }

    public void setHighestType(String highestType) {
        this.highestType = highestType;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public String getPointExpiryDt() {
        return pointExpiryDt;
    }

    public void setPointExpiryDt(String pointExpiryDt) {
        this.pointExpiryDt = pointExpiryDt;
    }

    public Long getRedeemedPoints() {
        return redeemedPoints;
    }

    public void setRedeemedPoints(Long redeemedPoints) {
        this.redeemedPoints = redeemedPoints;
    }

    public Long getTierUpPoints() {
        return tierUpPoints;
    }

    public void setTierUpPoints(Long tierUpPoints) {
        this.tierUpPoints = tierUpPoints;
    }

    public Long getTierUpTimes() {
        return tierUpTimes;
    }

    public void setTierUpTimes(Long tierUpTimes) {
        this.tierUpTimes = tierUpTimes;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Long getChangedPoints() {
        return changedPoints;
    }

    public void setChangedPoints(Long changedPoints) {
        this.changedPoints = changedPoints;
    }

    public Long getAquiredPoints() {
        return aquiredPoints;
    }

    public void setAquiredPoints(Long aquiredPoints) {
        this.aquiredPoints = aquiredPoints;
    }


    public MemberPointsDto toDto(){
        MemberPointsDto dto = new MemberPointsDto();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }

}
