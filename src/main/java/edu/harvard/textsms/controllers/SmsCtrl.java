package edu.harvard.textsms.controllers;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.harvard.textsms.models.SmsModel;
import edu.harvard.textsms.models.TokenProfile;
import edu.harvard.textsms.services.JwtService;
import edu.harvard.textsms.services.SmsService;

@PropertySource("classpath:application.properties")
@RestController
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class SmsCtrl {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SmsService smsService;
	
	@Autowired
	private JwtService jwtService;
	
	private SmsModel smsModel;
	
	@Value("${config.sms.api_key}")
	private String api_key;
	
	@Value("${config.sms.api_url}")
	private String sms_url;
	
	@Value("${config.sms.phone}")
	private String phone;
	
	@Value("${config.sms.version}")
	private String version;
	
	@Value("${config.sms.mo}")
	private String mo;
	
	@Value("${config.sms.maximum.send}")
	private Integer maximumSend;
	
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
	
	
	SmsCtrl() {
		smsModel=new SmsModel();
	}
	
	@RequestMapping(value="/sendsms", method=RequestMethod.POST)
	public SmsModel sendSMS(@RequestBody SmsModel sms, @RequestHeader(value="User-Agent") String user_agent, @RequestHeader(value="token") String token) throws Exception {
			
		sms.setStatus(false);
		// convert integer to expire date format
		Date myDate=new Date(sms.getExp());
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd");
		String expDate=ft.format(myDate);
		
		if(!sms.getIp().isEmpty() && !sms.getIat().toString().isEmpty() && !token.isEmpty() && !sms.getSessionToken().isEmpty() && this.vid.equals(sms.getVid()) && this.institution.equals(sms.getInst()) && this.onCampus.equals(sms.getOnCampus()) && this.expireDate.equals(expDate) && this.iss.equals(sms.getIss())) {
			// validate if the token is primo token
			TokenProfile tokenProfile=this.jwtService.getTokenProfile(token);
			Boolean tokenStatus = this.jwtService.validateJWTtoken(tokenProfile);
			if(tokenStatus) {
			Boolean smsFlag = true;
			// get today date
			Date date=new Date();
			sms.setDateTime(date);
			sms.setMaximumSend(1);
			String today=ft.format(date);
			// access service info in memory to validate the maximum number of sms can send per day
			List<SmsModel> listSMS=smsService.getSmsList();
			for(int i=0; i < listSMS.size(); i++) {
				SmsModel data=listSMS.get(i);
				String mydate = ft.format(data.getDateTime());
				if(mydate.equals(today) && sms.getSessionToken().equals(data.getSessionToken())) {
					if(data.getMaximumSend() >= this.maximumSend) {
						smsFlag=false;
					} else {
						Integer max=data.getMaximumSend() + 1;
						sms.setMaximumSend(max);
					}
				} else if(!mydate.equals(today)) {
					listSMS.remove(i);
				}
			}
			listSMS.add(sms);
			smsService.setSmsList(listSMS);
			
			// check if a user reach maximum of sending sms text per day
			if(smsFlag) {
				String p="1" + sms.getPhone();
				List<String> ph=new ArrayList<String>();
				ph.add(p);
				JSONObject json=new JSONObject();
				json.put("text", sms.getBody());
				json.put("to", ph);
				json.put("mo", this.mo);
				json.put("from", this.phone);
		
				StringEntity myParams = new StringEntity(json.toString(),ContentType.APPLICATION_JSON);
		
				logger.debug("*** Send sms to ClickAtell ***");
				logger.debug(json.toString());
		
				// set time out
				final RequestConfig requestTimeOut = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
		
				HttpClient client = HttpClients.createDefault();
				HttpPost post = new HttpPost(this.sms_url);
				post.setConfig(requestTimeOut);
				post.addHeader("X-Version",this.version);
				post.addHeader("Content-type", "application/json");
				post.addHeader("Accept", "application/json");
				post.addHeader("Authorization", this.api_key);
				post.addHeader("User_Agent", user_agent);
				post.setEntity(myParams);
	
				HttpResponse response = client.execute(post);
		
				logger.debug("\nSending 'POST' request to URL : " + this.sms_url);
				logger.debug("Post parameters : " + post.getEntity());
				logger.debug("Response Code : " + response.getStatusLine().getStatusCode());
		
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}

				logger.debug("*** response from ClickAtell ***");
				logger.debug(result.toString());
				sms.setStatus(true);
				smsModel=sms;
				smsModel.setMsg(result.toString());
			} else {
				smsModel=sms;
				smsModel.setMsg("You have reached the maximum sending sms per day. We allow only " + this.maximumSend + " sms per day.");
				logger.debug("*** Reach the maximum sms sending per day ***");
				logger.debug("session token = " + sms.getSessionToken());
				logger.debug("Phone = " + sms.getPhone());
				logger.debug("body = " + sms.getBody());
			}
			} else {
				logger.debug("*** Invalid toke. It is not primo token ***");
				logger.debug("JWT Token = " + sms.getToken());
				logger.debug("session token = " + sms.getSessionToken());
				logger.debug("Phone = " + sms.getPhone());
				logger.debug("body = " + sms.getBody());
			}
		} else {
			logger.debug("*** Missing header token and missing user session token ***");
			logger.debug("JWT Token = " + sms.getToken());
			logger.debug("session token = " + sms.getSessionToken());
			logger.debug("Phone = " + sms.getPhone());
			logger.debug("body = " + sms.getBody());
			smsModel=sms;
			smsModel.setMsg("Invalid request");
		}
		return smsModel;
	}
	
}
