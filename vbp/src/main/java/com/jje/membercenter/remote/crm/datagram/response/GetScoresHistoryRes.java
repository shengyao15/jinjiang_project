package com.jje.membercenter.remote.crm.datagram.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmResponse;
import com.jje.membercenter.remote.vo.ScoreTransactionVO;

@XmlRootElement(name = "response")
public class GetScoresHistoryRes extends CrmResponse {
	private ResponseBody body;

	public ResponseBody getBody() {
		return body;
	}

	public void setBody(ResponseBody body) {
		this.body = body;
	}

	public static class ResponseBody extends Body {
		private String membid;//会员id

		private Listofloytransaction listofloytransaction;

		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

		public Listofloytransaction getListofloytransaction() {
			return listofloytransaction;
		}

		public void setListofloytransaction(
				Listofloytransaction listofloytransaction) {
			this.listofloytransaction = listofloytransaction;
		}

	}

	public static class Listofloytransaction {

		private List<ScoreTransactionVO> loytransaction = new ArrayList<ScoreTransactionVO>();

		public List<ScoreTransactionVO> getLoytransaction() {
			return loytransaction;
		}

		public void setLoytransaction(List<ScoreTransactionVO> loytransaction) {
			this.loytransaction = loytransaction;
		}

	}
}
