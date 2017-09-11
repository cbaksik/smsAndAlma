package edu.harvard.textsms.services;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.harvard.textsms.models.TokenProfile;


@Service("JwtService")
public class JwtService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private TokenProfile tokenProfile;
	
	@Value("${config.jwt.institution}")
	private String institution;
	
	@Value("${config.jwt.vid}")
	private String vid;
	
	@Value("${config.jwt.iss}")
	private String iss;
	
	@Value("${config.jwt.onCampus}")
	private String onCampus;
	
	@Value("${config.jwt.expireDate}")
	private String expireDate;
	
	
	JwtService() {
		this.tokenProfile=new TokenProfile();
	}
	
	// convert jwt token string into json object list
	public List<JSONObject> decodeJwtToken(String jwt)  {
		
		
		
		List<JSONObject> listObj=new ArrayList<JSONObject>();
		String[] parts = jwt.split("[.]");
		  try
		  {
			String body="";
		    int len=parts.length;
		    if(len > 1) {
		    		body=parts[1];
		    } else {
		    		return null;
		    }
		    logger.debug("*** body jwt token ***");
		    logger.debug(body);
		   
		     byte[] partAsBytes = body.getBytes("UTF-8");
		      
		      try {
		    	  	String decodedPart = new String(java.util.Base64.getUrlDecoder().decode(partAsBytes), "UTF-8");
		    	  	JSONObject obj=new JSONObject(decodedPart);
		    	  	listObj.add(obj);
		    	  	
		    	  	logger.debug("*** decode part of body ***");
		    	  	logger.debug(decodedPart);
		      } catch(Exception e) {
		    	  	logger.debug("*** It can't decode jwt base 64 ***");
		    	  	logger.debug(e.getMessage());
		      }
		    
		  }
		  catch(Exception e)
		  {
			logger.debug("*** Base64 JWT decode error ***");
			logger.debug(e.getMessage());
			logger.debug(jwt);
		    throw new RuntimeException("Couldnt decode jwt", e);		    
		    
		  }
	
		return listObj;
		
	}
	
	// get token profile inform of model class
	public TokenProfile getTokenProfile(String jwtToken) {
		
		this.tokenProfile=new TokenProfile();
		
		List<JSONObject> output=this.decodeJwtToken(jwtToken);
		JSONObject obj=output.get(0);
		if(!obj.isNull("exp")) {
			Integer exp=(Integer) obj.get("exp");
			Date myDate=new Date(exp);
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd");
			String today=ft.format(myDate);
			tokenProfile.setExpireDate(today);
		}
		if(!obj.isNull("ip")) {
			String ip=obj.getString("ip");
			tokenProfile.setIp(ip);
		}
		if(!obj.isNull("iat")) {
			Integer iat=(Integer) obj.get("iat");
			tokenProfile.setIat(iat);
		}
		if(!obj.isNull("viewId")) {
			String viewId=obj.getString("viewId");
			tokenProfile.setVid(viewId);
		}
		if(!obj.isNull("institution")) {
			String institution = obj.getString("institution");
			tokenProfile.setInstitution(institution);
		}
		if(!obj.isNull("onCampus")) {
			String onCampus=obj.getString("onCampus");
			tokenProfile.setOnCampus(onCampus);
		}
		if(!obj.isNull("userName")) {
			String userName=obj.getString("userName");
			tokenProfile.setUserName(userName);
		}
		
		if(!obj.isNull("iss")) {
			String iss=obj.getString("iss");
			tokenProfile.setIss(iss);
		}
		
		return tokenProfile;
	}
	
	// validate JWT Token
	public Boolean validateJWTtoken(TokenProfile tokenProfile) {
		Boolean tokenFlag=false;
		String inst=tokenProfile.getInstitution();
		String viewId=tokenProfile.getVid();
		String campus=tokenProfile.getOnCampus();
		String expDate=tokenProfile.getExpireDate();
		String Iss=tokenProfile.getIss();
		if(inst.equals(this.institution) && viewId.equals(this.vid) && campus.equals(this.onCampus) && expDate.equals(this.expireDate) && Iss.equals(this.iss)) {
			tokenFlag=true;
		}
		
		logger.debug("*** validateJWTtoken ****");
		logger.debug("expire date = " + tokenProfile.getExpireDate());
		logger.debug("vid = " + tokenProfile.getVid());
		logger.debug("institution = " + tokenProfile.getInstitution());
		logger.debug("onCampus = " + tokenProfile.getOnCampus());
		logger.debug("iss = " + tokenProfile.getIss());
		logger.debug("ip = " + tokenProfile.getIp());
		logger.debug("iat = " + tokenProfile.getIat());
		logger.debug("userName = " + tokenProfile.getUserName());
		logger.debug("tokenFlag = " + tokenFlag);
		
		return tokenFlag;
	}

}
