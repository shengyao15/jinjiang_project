package com.jje.membercenter.remote.crm.support;

import com.jje.membercenter.remote.support.BaseResponse;

public abstract class CrmResponse extends  BaseResponse{
	
	public static enum Status {
		SUCCESS("00001"), FAIL("00002"), ERROR("-1");

		private String code;

		private Status(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
	}
 	
 	
	public  boolean  isStatus(Status status) { 
		return status.getCode().equals(this.getHead().retcode);
	}
 	
	
	public static class DefaultResponseHead extends Head {
		private String retcode;
		private String retmsg;

		public String getRetcode() {
			return retcode;
		}

		public void setRetcode(String retcode) {
			this.retcode = retcode;
		}

		public String getRetmsg() {
			return retmsg;
		}

		public void setRetmsg(String retmsg) {
			this.retmsg = retmsg;
		}
	}
	
//	public static class DefaultResponseBody extends Body {
//		private String membid;
//		private String cardnum;
//		public String getMembid() {
//			return membid;
//		}
//		public void setMembid(String membid) {
//			this.membid = membid;
//		}
//		public String getCardnum() {
//			return cardnum;
//		}
//		public void setCardnum(String cardnum) {
//			this.cardnum = cardnum;
//		}
//	}

	public CrmResponse(){
    	DefaultResponseHead head=new DefaultResponseHead();
	    this.head=head;
	}
	private DefaultResponseHead head;

	public DefaultResponseHead getHead() {
		return head;
	}

	public void setHead(DefaultResponseHead head) {
		this.head = head;
	}



}
