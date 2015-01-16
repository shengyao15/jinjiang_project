package com.jje.membercenter.remote.crm.datagram.response;

import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmResponse;
import com.jje.membercenter.remote.vo.ScoreRedeemVO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "response")
public class ScoresRedeemResponse extends CrmResponse {
	private ResponseBody body;

	public ResponseBody getBody() {
		return body;
	}

	public void setBody(ResponseBody body) {
		this.body = body;
	}

	public static class ResponseBody extends Body {
		private String membid;


		private Listofsr listofsr;

		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}


        public Listofsr getListofsr() {
            return listofsr;
        }

        public void setListofsr(Listofsr listofsr) {
            this.listofsr = listofsr;
        }
    }

	public static class Listofsr {

		protected List<ScoreRedeemVO> sr = new ArrayList<ScoreRedeemVO>();


        public List<ScoreRedeemVO> getSr() {
            return sr;
        }

        public void setSr(List<ScoreRedeemVO> sr) {
            this.sr = sr;
        }

    }
}
