package edu.harvard.textsms.controllers;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.harvard.textsms.models.SmsModel;

@PropertySource("classpath:application.properties")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class SmsCtrl {

	private SmsModel smsModel;
	
	@Value("${config.sms.api_key}")
	private String api_key;
	
	@Value("${config.sms.api_secret}")
	private String api_secret;
	
	@Value("${config.sms.api_url}")
	private String sms_url;
	
	@Value("${config.sms.phone}")
	private String phone;
	
	
	SmsCtrl() {
		smsModel=new SmsModel();
	}
	
	@RequestMapping(value="/sendsms", method=RequestMethod.POST)
	public SmsModel sendSMS(@RequestBody SmsModel sms, @RequestHeader(value="User-Agent") String user_agent) throws Exception {
		
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(this.sms_url);
		//post.setHeader("Content-type", "application/json");
		post.setHeader("User_Agent", user_agent);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("api_key",this.api_key));
		params.add(new BasicNameValuePair("api_secret",this.api_secret));
		params.add(new BasicNameValuePair("to","1" + sms.getPhone()));
		params.add(new BasicNameValuePair("from",this.phone));
		params.add(new BasicNameValuePair("text",sms.getBody()));
		
		post.setEntity(new UrlEncodedFormEntity(params));
		HttpResponse response = client.execute(post);
		
		System.out.println("\nSending 'POST' request to URL : " + this.sms_url);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : " +
                                    response.getStatusLine().getStatusCode());
		
		BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		System.out.println(result.toString());
		
		
		smsModel=sms;
		smsModel.setMsg(result.toString());
		return smsModel;
	}
	
}
