package com.jje.membercenter.remote.crm.support;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.jje.common.utils.DateUtils;
import com.jje.common.utils.JaxbUtils;
import com.jje.membercenter.remote.support.BaseRequest;

 

public abstract class  CrmRequest extends BaseRequest implements ApplicationContextAware{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	 
	private static CrmPassage crmPassage;

	public static class DefaultRequestHead extends Head {
	}

	private DefaultRequestHead head;

	private CrmRequest() {
	}

	public CrmRequest(CrmTransCode transCode) {
		DefaultRequestHead head = new DefaultRequestHead();
		head.setReqtime(DateUtils.formatDate(new Date(), DateUtils.YYYYMMDD));
		head.setSystype(CrmConstant.SYSTEM_TYPE);
		head.setTranscode(transCode.getCode());
		setHead(head);
	}

	public DefaultRequestHead getHead() {
		return head;
	}

	public void setHead(DefaultRequestHead head) {
		this.head = head;
	}

	protected abstract CrmResponse send() throws Exception;

	protected <T extends CrmResponse> T sendToType(Class<T> type) {
		try {
			 T response=crmPassage.sendToType(this, type);
			 logger.warn(" return object xml ==" + JaxbUtils.convertToXmlString(response));
			 return response;
		} catch (Exception e) {
			logger.error("call crm fail", e);
			try {
				T response = type.newInstance();
				response.getHead().setRetcode(CrmResponse.Status.ERROR.getCode());
				response.getHead().setRetmsg("call crm error");
				return response;
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}

	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		crmPassage=applicationContext.getBean(CrmPassage.class);
	}

}
