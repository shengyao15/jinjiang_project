package com.jje.membercenter.remote.vo;

import java.math.BigDecimal;

public class ScoreTransactionVO {
	private String transactionnumber;//交易编号
	private String transactiondate;//交易日期
	private String transactiontype;//交易类型
	private String transactionchannel;//交易渠道
	private String status;//状态
	private String programname;//忠诚度项目名称
	private String productname;//产品名称
	private String points;//基本交易积分
	private String pointtotal;//总积分 ？？？
	private String pointname; // 积分类型
	private String transfermembernumber;//积分自/至
	private String membername;//会员名称
	private String comments;//描述
	private String startdate;//交易历史开始区间 (入住时间|出团日期)
	private String enddate;//交易历史结束区间 (离店时间|回团时间)
	private String availablescore;//可用积分
	
	/** 2013-12-19 */
	private String hotelname; // 酒店名称
	private String hotelid; // 酒店ID
	private BigDecimal amount; // 消费金额
	private String groupcode; // 团代码(团ID)
	private String ordernumber; // 订单编号
	private String travelagency; // 旅行社
	private String partnername; // 来源板块
	
	public String getHotelname() {
		return hotelname;
	}

	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransactionnumber() {
		return transactionnumber;
	}

	public void setTransactionnumber(String transactionnumber) {
		this.transactionnumber = transactionnumber;
	}

	public String getTransactiondate() {
		return transactiondate;
	}

	public void setTransactiondate(String transactiondate) {
		this.transactiondate = transactiondate;
	}

	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	public String getTransactionchannel() {
		return transactionchannel;
	}

	public void setTransactionchannel(String transactionchannel) {
		this.transactionchannel = transactionchannel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProgramname() {
		return programname;
	}

	public void setProgramname(String programname) {
		this.programname = programname;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getPointtotal() {
		return pointtotal;
	}

	public void setPointtotal(String pointtotal) {
		this.pointtotal = pointtotal;
	}

	public String getPointname() {
		return pointname;
	}

	public void setPointname(String pointname) {
		this.pointname = pointname;
	}

	public String getTransfermembernumber() {
		return transfermembernumber;
	}

	public void setTransfermembernumber(String transfermembernumber) {
		this.transfermembernumber = transfermembernumber;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public String getAvailablescore() {
		return availablescore;
	}

	public void setAvailablescore(String availablescore) {
		this.availablescore = availablescore;
	}

	public String getGroupcode() {
		return groupcode;
	}

	public void setGroupcode(String groupcode) {
		this.groupcode = groupcode;
	}

	public String getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}

	public String getTravelagency() {
		return travelagency;
	}

	public void setTravelagency(String travelagency) {
		this.travelagency = travelagency;
	}

	public String getHotelid() {
		return hotelid;
	}

	public void setHotelid(String hotelid) {
		this.hotelid = hotelid;
	}

	public String getPartnername() {
		return partnername;
	}

	public void setPartnername(String partnername) {
		this.partnername = partnername;
	}

	

}
