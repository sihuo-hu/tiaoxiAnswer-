package com.royal.commen.rong.models;


import com.royal.commen.rong.util.GsonUtil;

//小灰条消息
public class InfoNtfMessage extends Message {

	private String message;
	private String extra;

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public InfoNtfMessage(String message) {
		this.type = "RC:InfoNtf";
		this.message = message;
	}

	public InfoNtfMessage(String message, String extra) {
		this(message);
		this.extra = extra;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return GsonUtil.toJson(this, InfoNtfMessage.class);
	}
}
