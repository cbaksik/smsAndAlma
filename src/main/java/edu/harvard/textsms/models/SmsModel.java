package edu.harvard.textsms.models;

import java.util.Date;

public class SmsModel {
	
	private String phone;
	private String deviceType;
	private String token;
	private String sessionToken;
	private Integer maximumSend;
	private String body;
	private Boolean mobile;
	private String msg;
	private Date dateTime;
	private Boolean status;
	
	// capture the JWT TOKEN from decode on client side
	private String ip;
	private Integer iat;
	private String inst;
	private String vid;
	private String iss;
	private String onCampus;
	private Boolean isLoggedIn;
	private Integer exp;
	private String userName;
	
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

	public Boolean getMobile() {
		return mobile;
	}
	public void setMobile(Boolean mobile) {
		this.mobile = mobile;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	public Integer getMaximumSend() {
		return maximumSend;
	}
	public void setMaximumSend(Integer maximumSend) {
		this.maximumSend = maximumSend;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getIat() {
		return iat;
	}
	public void setIat(Integer iat) {
		this.iat = iat;
	}
	public String getInst() {
		return inst;
	}
	public void setInst(String inst) {
		this.inst = inst;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getIss() {
		return iss;
	}
	public void setIss(String iss) {
		this.iss = iss;
	}
	public String getOnCampus() {
		return onCampus;
	}
	public void setOnCampus(String onCampus) {
		this.onCampus = onCampus;
	}
	public Boolean getIsLoggedIn() {
		return isLoggedIn;
	}
	public void setIsLoggedIn(Boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	public Integer getExp() {
		return exp;
	}
	public void setExp(Integer exp) {
		this.exp = exp;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
