package com.jje.membercenter.remote.crm.datagram.request;

import com.jje.membercenter.remote.crm.datagram.response.ScoresRedeemResponse;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmSysType;
import com.jje.membercenter.remote.crm.support.CrmTransCode;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlRootElement(name = "request")
@Component
public class ScoreRedeemRequest extends CrmRequest {
	public ScoreRedeemRequest() {
		super(CrmTransCode.GET_SCORES_REDEEM_LIST);
		this.body = new RequestBody();
	}

	private RequestBody body;

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

	public ScoresRedeemResponse send() {
		return this.sendToType(ScoresRedeemResponse.class);
	}

	public static class RequestBody extends Body {
		private String membid;
	    
        private String srtype;

		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

        public String getSrtype() {
            return srtype;
        }

        public void setSrtype(String srtype) {
            this.srtype = srtype;
        }
    }

    @Override
    public DefaultRequestHead getHead() {
        DefaultRequestHead tempHead = super.getHead();
        tempHead.setSystype(CrmSysType.JJE_EBUSINESS.getCode());
        tempHead.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        return tempHead;
    }
}
