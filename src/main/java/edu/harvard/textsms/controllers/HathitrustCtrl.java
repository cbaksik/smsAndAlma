package edu.harvard.textsms.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.harvard.textsms.models.HathiTrustModel;

@PropertySource("classpath:application.properties")
@RestController
public class HathitrustCtrl {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${config.hathitrust.url}")
	private String hathitrusthUrl;
	
	@RequestMapping(value="/hathitrust", method=RequestMethod.POST)
	public String getHathiTrust(@RequestBody  HathiTrustModel obj, @RequestHeader(value="User-Agent") String user_agent) throws Exception {
		
		logger.debug("Parameter request from UI");
		logger.debug("isbn = " + obj.getIsbn());
		logger.debug("oclcid = " + obj.getOclcid());
		
		String isbn=obj.getIsbn();
		String oclcid=obj.getOclcid();
		String url=this.hathitrusthUrl;
		
		if(!isbn.isEmpty()) {
			url+="isbn/"+isbn+".json";
		}
		if(!oclcid.isEmpty()) {
			url+="oclc/"+oclcid+".json";
		}
		
			
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		get.setHeader("User_Agent", user_agent);
		get.setHeader("Content-Type","application/json");
		get.setHeader("Accept","application/json");
		
		HttpResponse response = client.execute(get);
		
		logger.debug("*** response from HathiTrust ***");
		logger.debug("\nSending 'GET' request to URL : " + url);
		logger.debug("Response Code : " + response.getStatusLine().getStatusCode());
		
		BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		
		logger.debug(result.toString());
		
		return result.toString();
	}
}
