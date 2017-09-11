package edu.harvard.textsms.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.harvard.textsms.models.BarcodeModel;

@PropertySource("classpath:application.properties")
@RestController
public class BarcodeCtrl {
	
	
	@Value("${config.alma.apikey}")
	private String apikey;
	
	@Value("${config.alma.host}")
	private String apiHost;
	
	@Value("${config.alma.path}")
	private String apiPath;
	
	@Value("${config.alma.protocol}")
	private String apiProtocol;

	// constructor
	BarcodeCtrl() {
		
	}
	
	@RequestMapping(value="/barcode", method=RequestMethod.POST)
	public String getBarcode(@RequestBody BarcodeModel barcode, @RequestHeader(value="User-Agent") String user_agent) throws Exception {
		
		URIBuilder  builder=new URIBuilder();
		builder.setScheme(this.apiProtocol).setHost(this.apiHost).setPath(this.apiPath);
		builder.setParameter("apikey", this.apikey);
		builder.setParameter("item_barcode", barcode.getBarcode());
		builder.setParameter("format", "json");
		
		URI url = builder.build();
		
		System.out.println("*** barcode = " + barcode.getBarcode());
	
		
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		get.setHeader("User_Agent", user_agent);
		get.setHeader("Content-Type","application/json");
		get.setHeader("Accept","application/json");
		
		HttpResponse response = client.execute(get);
		
		System.out.println("\nSending 'GET' request to URL : " + url);
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
		
		return result.toString();
	}
	
}
