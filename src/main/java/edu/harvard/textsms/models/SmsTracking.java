package edu.harvard.textsms.models;

import java.util.Date;

public class SmsTracking {
	private String ip;
	private String phone;
	private String body;
	private Date sendDate;
	private Integer maximumSend;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Integer getMaximumSend() {
		return maximumSend;
	}
	public void setMaximumSend(Integer maximumSend) {
		this.maximumSend = maximumSend;
	}
	
	
}
