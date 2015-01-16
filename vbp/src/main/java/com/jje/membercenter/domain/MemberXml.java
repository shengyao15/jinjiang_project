package com.jje.membercenter.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.jje.dto.membercenter.MemberXmlDto;

@XmlRootElement
public class MemberXml
{
	public static String EXEC_FAIL="F";
	
	public static String EXEC_ERROR="E";
	
	private String id;

	private String email;

	private String mobile;

	private String orderNo;

	private String certificateType;

	private String certificateNo;

	private String xml;

	private String callBackFlag;
	
	private Date updatedDate;
	
	private String couponCode;
	
	
	

	public MemberXml(String id, String callBackFlag) {
		super();
		this.id = id;
		this.callBackFlag = callBackFlag;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public MemberXml()
	{
		super();
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getCertificateType()
	{
		return certificateType;
	}

	public void setCertificateType(String certificateType)
	{
		this.certificateType = certificateType;
	}

	public String getCertificateNo()
	{
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo)
	{
		this.certificateNo = certificateNo;
	}

	public MemberXml(String id, String email, String mobile, String orderNo, String certificateType, String certificateNo, String xml, String callBackFlag)
	{
		super();
		this.id = id;
		this.email = email;
		this.mobile = mobile;
		this.orderNo = orderNo;
		this.certificateType = certificateType;
		this.certificateNo = certificateNo;
		this.xml = xml;
		this.callBackFlag = callBackFlag;
	}
	
	public MemberXmlDto toDto(){
		MemberXmlDto dto = new MemberXmlDto();
		dto.setId(id);
		dto.setEmail(email);
		dto.setMobile(mobile);
		dto.setOrderNo(orderNo);
		dto.setCertificateNo(certificateNo);
		dto.setCertificateType(certificateType);
		dto.setXml(xml);
		dto.setCallBackFlag(callBackFlag);
		dto.setUpdatedDate(updatedDate);
		return dto;
	}

	/**
	 * @return the xml
	 */
	public String getXml()
	{
		return xml;
	}

	/**
	 * @param xml
	 *            the xml to set
	 */
	protected void setXml(String xml)
	{
		this.xml = xml;
	}

	/**
	 * @return the callBackFlag
	 */
	public String getCallBackFlag()
	{
		return callBackFlag;
	}

	/**
	 * @param callBackFlag
	 *            the callBackFlag to set
	 */
	public void setCallBackFlag(String callBackFlag)
	{
		this.callBackFlag = callBackFlag;
	}
	
	public MemberXml(MemberXmlDto memberXmlDto) {
		super();
		this.id = memberXmlDto.getId();
		this.email = memberXmlDto.getEmail();
		this.mobile = memberXmlDto.getMobile();
		this.orderNo = memberXmlDto.getOrderNo();
		this.certificateType = memberXmlDto.getCertificateType();
		this.certificateNo = memberXmlDto.getCertificateNo();
		this.xml = memberXmlDto.getXml();
		this.callBackFlag = memberXmlDto.getCallBackFlag();
	}
}
