package com.jje.membercenter.remote.support;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ /*TestReceiver.class*/ })
public class BaseReceiver {
	private String transCode;

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
}
