/**
 * 
 */
package com.jje.membercenter.crm.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.esotericsoftware.minlog.Log;
import com.jje.common.utils.JaxbUtils;

/**
 * @author SHENGLI_LU
 * 
 */
@Component
public class CRMBaseProxy {

	private static final String CRM_RET_CODE_PREFIX = "(SBL";
	
	private static final String CRM_NORMAL_ERROR = "当前系统繁忙，请您稍后再试！";
	
	@Value(value = "${crm.url}")
	private String crmUrl;

	@Value(value = "${crm.UserName}")
	private String crmUserName;

	@Value(value = "${crm.Password}")
	private String crmPassword;

	private static final Logger LOG = LoggerFactory
			.getLogger(CRMBaseProxy.class);

	public com.jje.membercenter.xsd.MemBaseQueryRequest newMemQueryRequest() {
		return new com.jje.membercenter.xsd.MemBaseQueryRequest();
	}

	public com.jje.membercenter.xsd.MemBaseQueryResponse getMemQueryResponse(
			com.jje.membercenter.xsd.MemBaseQueryRequest request)
			throws Exception {
		return this.getResponse(request,
				com.jje.membercenter.xsd.MemBaseQueryResponse.class);
	}

	public com.jje.membercenter.xsd.MemBaseQueryResponse getMemQueryResponseByXml(
			String reqXml) throws Exception {
		return this.getResponseByXml(reqXml,
				com.jje.membercenter.xsd.MemBaseQueryResponse.class,new Date());
	}

	protected <T> T getResponse(Object request, Class<T> type) throws Exception {
		String reqXml = JaxbUtils.convertToXmlString(request);
		LOG.debug("CRM Request :" + reqXml);
		Date begin = new Date();		
		return getResponseByXml(reqXml, type,begin);
	}

	/**
	 * @param reqXml
	 * @param type
	 * @return
	 * @throws Exception
	 */
	// 重写,增加费时打印和超时的时间打印
	private <T> T getResponseByXml(String reqXml, Class<T> type,Date begin)
			throws Exception {
		HttpMethod method = createPostData(reqXml);
		int statusCode = -1;
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("utf-8");
		try {
			statusCode = httpClient.executeMethod(method);
		} catch (Exception e ) {
			throw new Exception(e.getMessage() + " cost time " + getCostTime(begin));
		}
		if (statusCode != HttpStatus.SC_OK) {
			throw new RuntimeException("Call crm fail. httpStatus="
					+ statusCode + " cost time  " + getCostTime(begin));
		}
		String responseBodyAsString = method.getResponseBodyAsString();
		
		LOG.debug("CRM Response cost time " + getCostTime(begin) +":" +  responseBodyAsString);
		
		if (StringUtils.isEmpty(responseBodyAsString)) {
			LOG.warn("CRM return empty response, please check with CRM system support person, CRM Link:"
					+ crmUrl);
		}
		responseBodyAsString = crmMessageFriendly(responseBodyAsString);

		return JaxbUtils.convertToObject(responseBodyAsString, type);
	}
	
	
	// 预防有别的模块调用，所以不删除
	private <T> T getResponseByXml(String reqXml, Class<T> type)
			throws IOException, HttpException {
		HttpMethod method = createPostData(reqXml);
		int statusCode = -1;
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("utf-8");
		statusCode = httpClient.executeMethod(method);
		if (statusCode != HttpStatus.SC_OK) {
			throw new RuntimeException("Call crm fail. httpStatus="
					+ statusCode);
		}
		String responseBodyAsString = method.getResponseBodyAsString();

		LOG.debug("CRM Response :" + responseBodyAsString);
		if (StringUtils.isEmpty(responseBodyAsString)) {
			LOG.warn("CRM return empty response, please check with CRM system support person, CRM Link:"
					+ crmUrl);
		}
		responseBodyAsString = crmMessageFriendly(responseBodyAsString);

		return JaxbUtils.convertToObject(responseBodyAsString, type);
	}
	
	/* 打印费时 */
	public String getCostTime(Date start) {
		String costTime = "";
		Date end = new Date();
	    long between=(end.getTime()-start.getTime());
	    costTime = between +" millisecond ";
	    return costTime;
	}

	private  HttpMethod  createPostData(String para) {

	     PostMethod post = new PostMethod(crmUrl);
		NameValuePair SWEExtSource = new NameValuePair("SWEExtSource",
				"JJIntegration");
	     NameValuePair SWEExtCmd = new NameValuePair("SWEExtCmd", "Execute");
	     NameValuePair UserName = new NameValuePair("UserName", crmUserName);
	     NameValuePair Password = new NameValuePair("Password", crmPassword);
	     NameValuePair SWEExtData = new NameValuePair("SWEExtData", para);
	     post.setRequestBody(new NameValuePair[] { SWEExtSource, SWEExtCmd,
	             UserName, Password, SWEExtData });
	     return post;
	}

	public String crmMessageFriendly(String responseBodyAsString) {

		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			StringReader read = new StringReader(responseBodyAsString);

			InputSource source = new InputSource(read);
			Document doc = dombuilder.parse(source);
			NodeList nodes = doc.getElementsByTagName("remsg");
			if (nodes != null && nodes.getLength() > 0) {
				String remsg = nodes.item(0).getTextContent();
				if (!StringUtils.isEmpty(remsg)) {
					if (RESPONSE_MAPPER.containsKey(remsg)) {
						nodes.item(0)
								.setTextContent(RESPONSE_MAPPER.get(remsg));
					} else {
						nodes.item(0).setTextContent(CRM_NORMAL_ERROR);
					}
				}
			}
			TransformerFactory  tf  =  TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			t.setOutputProperty("encoding","utf-8");//解决中文问题，试过用GBK不行
			ByteArrayOutputStream  bos  =  new  ByteArrayOutputStream();
			t.transform(new DOMSource(doc), new StreamResult(bos));
			String xmlStr = bos.toString();
			return xmlStr;
		} catch (Exception e) {
			LOG.error("got one exception when parsering crm response xml!",e);
			return responseBodyAsString;
		}
	}

	static final Map<String, String> RESPONSE_MAPPER = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			this.put("没有找到会员相关的记录无法激活(SBL-EXL-00151)", "没有找到会员相关的记录无法激活,请致电1010-1666,由客服为您服务！");
			this.put("此手机号码已经被使用过(SBL-EXL-00151)", "此手机号码已注册,请直接使用该手机号码找回密码，进行登录！");	
			this.put("没有找到会员相关的记录无法激活(SBL-EXL-00151)", "无法找到相关的会员记录根据输入的信息,请您重试或致电1010-1666,由客服为您服务！");	
			this.put("此证件类型和号码已经被使用过(SBL-EXL-00151)", "您输入证件类型和号码已注册,请输入其它证件号码或使用已注册的信息进行登录！");				
			this.put("此邮箱地址已经被使用过(SBL-EXL-00151)", "此邮箱地址已注册,此手机号码已注册！");	
			this.put("该合作伙伴卡已注册(SBL-EXL-00151)", "该合作伙伴卡已注册");
			this.put("注册信息已提交过(SBL-EXL-00151)", "注册信息已提交过");
			this.put("交易操作成功！", "交易操作成功！");
		}
	};

}
