package com.blackchopper.demo_onhttp.entity;

public class UserResult {
	private	String cookie;
	private int errorcode;
	private String userid;
	private String msg;
	private boolean result;
	
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public int getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "UserResult{" +
				"cookie='" + cookie + '\'' +
				", errorcode=" + errorcode +
				", userid=" + userid +
				", msg='" + msg + '\'' +
				", result=" + result +
				'}';
	}
}
