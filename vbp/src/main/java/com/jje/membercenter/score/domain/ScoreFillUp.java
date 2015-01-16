package com.jje.membercenter.score.domain;

import com.jje.common.utils.DateUtils;
import com.jje.dto.membercenter.score.RedeemStatus;
import com.jje.dto.membercenter.score.ScoreFillUpDto;
import com.jje.dto.membercenter.score.ScoreFillUpStatus;
import com.jje.dto.membercenter.score.ScoreFillUpType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

public class ScoreFillUp {

    private static final String SEPARATOR = "\n";

    private static final String DELIMITER = ":";

    private ScoreFillUpType businessType;

    private String checkInCity;

    private String hotelName;

    private Date checkInTime;

    private Date checkOutTime;

    private String roomNo;

    private String orderNo;

    private String invoiceNo;

    private String carNo;

    private Date payTime;

    private Date carStartTime;

    private Date carEndTime;

    private String lineName;

    private String groupCode;

    private Date departDate;

    private Date returnDate;

    private String storeName;

    private BigDecimal amount;

    private String invoiceUrl;

    private ScoreFillUpStatus status;

    private RedeemStatus redeemStatus;

    private Date applyDate;

    private String mcMemberCode;
    
    private String memberId;

    public ScoreFillUp() {
        super();
    }

    public ScoreFillUp(ScoreFillUpDto dto) {
        BeanUtils.copyProperties(dto, this);
    }

    public ScoreFillUpType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(ScoreFillUpType businessType) {
        this.businessType = businessType;
    }

    public String getCheckInCity() {
        return checkInCity;
    }

    public void setCheckInCity(String checkInCity) {
        this.checkInCity = checkInCity;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Date getDepartDate() {
        return departDate;
    }

    public void setDepartDate(Date departDate) {
        this.departDate = departDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getInvoiceUrl() {
        return invoiceUrl;
    }

    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }


    public ScoreFillUpStatus getStatus() {
        return status;
    }

    public void setStatus(ScoreFillUpStatus status) {
        this.status = status;
    }

    public RedeemStatus getRedeemStatus() {
        return redeemStatus;
    }

    public void setRedeemStatus(RedeemStatus redeemStatus) {
        this.redeemStatus = redeemStatus;
    }

    public Date getCarStartTime() {
        return carStartTime;
    }

    public void setCarStartTime(Date carStartTime) {
        this.carStartTime = carStartTime;
    }

    public Date getCarEndTime() {
        return carEndTime;
    }

    public void setCarEndTime(Date carEndTime) {
        this.carEndTime = carEndTime;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getDetail() {
        StringBuilder sub = new StringBuilder();
        if (businessType.equals(ScoreFillUpType.HOTEL)) {
            setHotelDetail(sub);
        }
        if (businessType.equals(ScoreFillUpType.TRAVEL)) {
            setTravelDetail(sub);
        }
        if (businessType.equals(ScoreFillUpType.AUTO)) {
            setAutoDetail(sub);
        }
        return sub.toString();
    }

    public void setDetail(String detail) {
        String[] result = detail.split(SEPARATOR);
        String type = result[0].split(DELIMITER)[1];
        if (type.equals(ScoreFillUpType.AUTO.getCnName())) {
            setBusinessType(ScoreFillUpType.AUTO);
            getAutoDetail(result);
        }
        if (type.equals(ScoreFillUpType.TRAVEL.getCnName())) {
            setBusinessType(ScoreFillUpType.TRAVEL);
            getTravelDetail(result);
        }

        if (type.equals(ScoreFillUpType.HOTEL.getCnName())) {
            setBusinessType(ScoreFillUpType.HOTEL);
            getHotelDetail(result);
        }
    }

    private void setAutoDetail(StringBuilder sub) {
        sub.append("消费类型").append(DELIMITER).append(ScoreFillUpType.AUTO.getCnName()).append(SEPARATOR)
                .append("购买时间").append(DELIMITER).append(DateUtils.formatCnDate(payTime)).append(SEPARATOR)
                .append("购买门店").append(DELIMITER).append(storeName).append(SEPARATOR)
                .append("车牌号").append(DELIMITER).append(carNo).append(SEPARATOR)
                .append("租车开始时间").append(DELIMITER).append(DateUtils.formatCnDate(carStartTime)).append(SEPARATOR)
                .append("租车结束时间").append(DELIMITER).append(DateUtils.formatCnDate(carEndTime)).append(SEPARATOR)
                .append("消费金额(元)").append(DELIMITER).append(amount).append(SEPARATOR)
                .append("订单号").append(DELIMITER).append(orderNo).append(SEPARATOR)
                .append("发票号").append(DELIMITER).append(invoiceNo);
    }

    private void getAutoDetail(String[] result) {
        if (1 < result.length)
            payTime = DateUtils.parseCnDate(getValue(result[1]));
        if (2 < result.length)
            storeName = getValue(result[2]);
        if (3 < result.length)
            carNo = getValue(result[3]);
        if (4 < result.length)
            carStartTime = DateUtils.parseCnDate(getValue(result[4]));
        if (5 < result.length)
            carEndTime = DateUtils.parseCnDate(getValue(result[5]));
        if (6 < result.length)
            amount = new BigDecimal(getValue(result[6]));
        if (7 < result.length)
            orderNo = getValue(result[7]);
        if (8 < result.length)
            invoiceNo = getValue(result[8]);
    }

    private void setTravelDetail(StringBuilder sub) {
        sub.append("消费类型").append(DELIMITER).append(ScoreFillUpType.TRAVEL.getCnName()).append(SEPARATOR)
                .append("购买时间").append(DELIMITER).append(DateUtils.formatCnDate(payTime)).append(SEPARATOR)
                .append("购买门店").append(DELIMITER).append(storeName).append(SEPARATOR)
                .append("线路名称").append(DELIMITER).append(lineName).append(SEPARATOR)
                .append("团号").append(DELIMITER).append(groupCode).append(SEPARATOR)
                .append("出发日期").append(DELIMITER).append(DateUtils.formatCnDate(departDate)).append(SEPARATOR)
                .append("结束日期").append(DELIMITER).append(DateUtils.formatCnDate(returnDate)).append(SEPARATOR)
                .append("消费金额(元)").append(DELIMITER).append(amount).append(SEPARATOR)
                .append("订单号").append(DELIMITER).append(orderNo).append(SEPARATOR)
                .append("发票号").append(DELIMITER).append(invoiceNo);
    }

    private void getTravelDetail(String[] result) {
        if (1 < result.length)
            payTime = DateUtils.parseCnDate(getValue(result[1]));
        if (2 < result.length)
            storeName = getValue(result[2]);
        if (3 < result.length)
            lineName = getValue(result[3]);
        if (4 < result.length)
            groupCode = getValue(result[4]);
        if (5 < result.length)
            departDate = DateUtils.parseCnDate(getValue(result[5]));
        if (6 < result.length)
            returnDate = DateUtils.parseCnDate(getValue(result[6]));
        if (7 < result.length)
            amount = new BigDecimal(getValue(result[7]));
        if (8 < result.length)
            orderNo = getValue(result[8]);
        if (9 < result.length)
            invoiceNo = getValue(result[9]);
    }

    private void setHotelDetail(StringBuilder sub) {
        sub.append("消费类型").append(DELIMITER).append(ScoreFillUpType.HOTEL.getCnName()).append(SEPARATOR)
                .append("入住城市").append(DELIMITER).append(checkInCity).append(SEPARATOR)
                .append("入住酒店").append(DELIMITER).append(hotelName).append(SEPARATOR)
                .append("入住房间").append(DELIMITER).append(roomNo).append(SEPARATOR)
                .append("入住时间").append(DELIMITER).append(DateUtils.formatCnDate(checkInTime)).append(SEPARATOR)
                .append("退房时间").append(DELIMITER).append(DateUtils.formatCnDate(checkOutTime)).append(SEPARATOR)
                .append("消费金额(元)").append(DELIMITER).append(amount).append(SEPARATOR)
                .append("订单号").append(DELIMITER).append(orderNo).append(SEPARATOR)
                .append("发票号").append(DELIMITER).append(invoiceNo);
    }

    private void getHotelDetail(String[] result) {
        if (1 < result.length)
            checkInCity = getValue(result[1]);
        if (2 < result.length)
            hotelName = getValue(result[2]);
        if (3 < result.length)
            roomNo = getValue(result[3]);
        if (4 < result.length)
            checkInTime = DateUtils.parseCnDate(getValue(result[4]));
        if (5 < result.length)
            checkOutTime = DateUtils.parseCnDate(getValue(result[5]));
        if (6 < result.length)
            amount = new BigDecimal(getValue(result[6]));
        if (7 < result.length)
            orderNo = getValue(result[7]);
        if (8 < result.length)
            invoiceNo = getValue(result[8]);
    }

    private String getValue(String param) {
        return param.substring(param.indexOf(DELIMITER) + 1);
    }

    public ScoreFillUpDto toDto() {
        ScoreFillUpDto dto = new ScoreFillUpDto();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }

    public String getMcMemberCode() {
        return mcMemberCode;
    }

    public void setMcMemberCode(String mcMemberCode) {
        this.mcMemberCode = mcMemberCode;
    }
    
    public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public boolean checkScoreFillUpLegal() {
        return StringUtils.isNotEmpty(this.getDetail()) && StringUtils.isNotEmpty(this.getMcMemberCode());
    }
}
