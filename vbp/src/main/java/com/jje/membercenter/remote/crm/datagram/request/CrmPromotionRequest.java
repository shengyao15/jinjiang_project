package com.jje.membercenter.remote.crm.datagram.request;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmResponse;
import com.jje.membercenter.remote.crm.support.CrmTransCode;

@XmlRootElement(name = "request")
@Component
public class CrmPromotionRequest extends CrmRequest{
	
	private RequestBody body;

	public CrmPromotionRequest() {
		super(CrmTransCode.PROMOTION_SIGN_UP);
		setBody(new RequestBody());
	}

	@Override
	protected CrmResponse send() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

	public static class RequestBody extends Body {
		// memberId
		private String membid;
		// 活动名称
		private String activity;
		// 活动报名渠道
		private String attchanel;
		// 活动报名时间 格式MMddYYYY – 12102014
		private String attdate;
		
		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

		public String getActivity() {
			return activity;
		}

		public void setActivity(String activity) {
			this.activity = activity;
		}

		public String getAttchanel() {
			return attchanel;
		}

		public void setAttchanel(String attchanel) {
			this.attchanel = attchanel;
		}

		public String getAttdate() {
			return attdate;
		}

		public void setAttdate(String attdate) {
			this.attdate = attdate;
		}

		@Override
		public String toString() {
			return "RequestBody [membid=" + membid + ", activity=" + activity + ", attchanel=" + attchanel + ", attdate=" + attdate + "]";
		}
		
	}
}
