package edu.harvard.textsms.models;

public class TokenProfile {
    private String kid;
    private String ip;
    private String iss;
    private String userName;
    private String onCampus;
    private String institution;
    private String vid;
    private Integer iat; // long and lat
    private String expireDate;
    
    
	public String getKid() {
		return kid;
	}
	public void setKid(String kid) {
		this.kid = kid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIss() {
		return iss;
	}
	public void setIss(String iss) {
		this.iss = iss;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOnCampus() {
		return onCampus;
	}
	public void setOnCampus(String onCampus) {
		this.onCampus = onCampus;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public Integer getIat() {
		return iat;
	}
	public void setIat(Integer iat) {
		this.iat = iat;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
    
    
    
    
}
