package com.jje.membercenter.remote.crm.support;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"transcode","reqtime", "systype"})
public abstract class Head {
	private String transcode;
	private String reqtime;
	private String systype;

	public String getTranscode() {
		return transcode;
	}

	public void setTranscode(String transcode) {
		this.transcode = transcode;
	}

	public String getReqtime() {
		return reqtime;
	}

	public void setReqtime(String reqtime) {
		this.reqtime = reqtime;
	}

	public String getSystype() {
		return systype;
	}

	public void setSystype(String systype) {
		this.systype = systype;
	}

}
