package com.jje.membercenter.remote.vo;

import java.math.BigDecimal;

import com.jje.dto.membercenter.score.ScoreFillUpType;


public class ScoreRedeemVO {

    protected String srnumber;

    protected String srtype;

    protected String status;

    protected String substatus;

    protected String priority;

    protected String owner;

    protected String srdetail;

//    protected String startdate; 
    /** 2013-12-20*/
    protected String applydate; // 申请日期
    protected String type; // 消费项目
	protected String startdate; // 开始日期
    protected String enddate; // 结束日期
    protected BigDecimal amount; // 消费金额

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getApplydate() {
		return applydate;
	}

	public void setApplydate(String applydate) {
		this.applydate = applydate;
	}

	public String getSrnumber() {
        return srnumber;
    }

    public void setSrnumber(String srnumber) {
        this.srnumber = srnumber;
    }

    public String getSrtype() {
        return srtype;
    }

    public void setSrtype(String srtype) {
        this.srtype = srtype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubstatus() {
        return substatus;
    }

    public void setSubstatus(String substatus) {
        this.substatus = substatus;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSrdetail() {
        return srdetail;
    }

    public void setSrdetail(String srdetail) {
        this.srdetail = srdetail;
    }

//    public String getStartdate() {
//        return startdate;
//    }
//
//    public void setStartdate(String startdate) {
//        this.startdate = startdate;
//    }
}
