package com.jje.membercenter.domain;

import com.jje.dto.membercenter.SSORedeemDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberScoreRedeem {
    private String id;

    private String memId;

    private String pdCode;

    private String score;

    private Date time;

    private String orderId;

    private String key;

    private Date sTime;

    private Date eTime;

    private Integer status;

    public MemberScoreRedeem() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MemberScoreRedeem(String id, String memId, String pdCode, String score, Date time, String orderId, String key, Date sTime, Date eTime, Integer status) {
        super();
        this.id = id;
        this.memId = memId;
        this.pdCode = pdCode;
        this.score = score;
        this.time = time;
        this.orderId = orderId;
        this.key = key;
        this.sTime = sTime;
        this.eTime = eTime;
        this.status = status;
    }

    public MemberScoreRedeem(SSORedeemDto ssoRedeemDto) throws Exception {
        super();
        this.memId = ssoRedeemDto.getMemid();
        this.orderId = ssoRedeemDto.getOrderid();
        this.pdCode = ssoRedeemDto.getPdcode();
        this.score = ssoRedeemDto.getScore();
        try {
            this.time = new SimpleDateFormat("yyyyMMddHHmmss").parse(ssoRedeemDto.getTime());
        } catch (ParseException e) {
            this.time = new Date();
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemId() {
        return memId;
    }

    public void setMemId(String memId) {
        this.memId = memId;
    }

    public String getPdCode() {
        return pdCode;
    }

    public void setPdCode(String pdCode) {
        this.pdCode = pdCode;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getsTime() {
        return sTime;
    }

    public void setsTime(Date sTime) {
        this.sTime = sTime;
    }

    public Date geteTime() {
        return eTime;
    }

    public void seteTime(Date eTime) {
        this.eTime = eTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
