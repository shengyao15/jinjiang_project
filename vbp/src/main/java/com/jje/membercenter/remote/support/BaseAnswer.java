package com.jje.membercenter.remote.support;

public  class BaseAnswer {

	private String status;
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static enum Status {
		SUCCESS("业务处理成功"), FAIL("业务处理失败"), ERROR("业务处理出错");
		private String alias;

		private Status(String alias) {
			this.alias = alias;

		}

		public String getAlias() {
			return alias;
		}

	}

}
