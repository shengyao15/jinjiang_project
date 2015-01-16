package com.jje.membercenter.remote.crm.support;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jje.common.utils.JaxbUtils;

@Component
public class CrmPassage {

	private Logger logger = LoggerFactory.getLogger(CrmPassage.class);
	@Value(value = "${crm.UserName}")
	private String crmUserName;

	@Value(value = "${crm.Password}")
	private String crmPassword;

	@Value(value = "${crm.url}")
	private String crmUrl;

	public <T extends CrmResponse> T sendToType(Object data, Class<T> type) {
		PostMethod post = null;
		String requestXml=null;
		try {
			requestXml = JaxbUtils.convertToXmlString(data);
			logger.warn("send crm  Request---> crmUrl=" + crmUrl
					+ "?SWEExtSource=JJIntegration&SWEExtCmd=Execute&SWEExtData=" + requestXml);
			post = new PostMethod(crmUrl);
			NameValuePair sWEExtSource = new NameValuePair("SWEExtSource",
					"JJIntegration");
			NameValuePair sWEExtCmd = new NameValuePair("SWEExtCmd", "Execute");
			NameValuePair userName = new NameValuePair("UserName", crmUserName);
			NameValuePair password = new NameValuePair("Password", crmPassword);
			NameValuePair sWEExtData = new NameValuePair("SWEExtData",
					requestXml);
			post.setRequestBody(new NameValuePair[] { sWEExtSource, sWEExtCmd,
					userName, password, sWEExtData });
			HttpClient httpClient = new HttpClient();
			httpClient.getParams().setContentCharset("utf-8");
			int statusCode = httpClient.executeMethod(post);
			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("Call crm fail. httpStatus="
						+ statusCode);
			}
			String responseXml = post.getResponseBodyAsString();
			logger.warn("send crm  Respons--->xml=" + responseXml);
			return JaxbUtils.convertToObject(responseXml, type);
		} catch (Exception e) {
			logger.error("sendToType error!", e);
			logger.error("send crm  Request---> crmUrl=" + crmUrl
					+ "?SWEExtSource=JJIntegration&SWEExtCmd=Execute&SWEExtData=" + requestXml);
			throw new RuntimeException("Call crm happen exception "+e.getMessage(),e);
		} finally {
			post.releaseConnection();
		}
	}
}
