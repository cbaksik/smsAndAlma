package edu.harvard.textsms.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
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

import edu.harvard.textsms.models.TableOfContentModel;


@PropertySource("classpath:application.properties")
@RestController
public class TableOfContentCtrl {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${config.tableofcontent.protocol}")
	private String protocol;
	
	@Value("${config.tableofcontent.host}")
	private String host;
	
	@Value("${config.tableofcontent.path}")
	private String path;
	
	@Value("${config.tableofcontent.client}")
	private String client;
	
	@Value("${config.tableofcontent.type}")
	private String type;

	@RequestMapping(value="/toc", method=RequestMethod.POST)
	public TableOfContentModel sendTOC(@RequestBody TableOfContentModel toc, @RequestHeader(value="User-Agent") String user_agent) throws Exception {
		
		toc.setHasData(false);
		
		String isbn = toc.getIsbn() + '/' + "xml.xml";
		
		URIBuilder  builder=new URIBuilder();
		builder.setScheme(this.protocol).setHost(this.host).setPath(this.path);
		builder.setParameter("client", this.client);
		builder.setParameter("type", this.type);
		builder.setParameter("isbn", isbn);
		
		URI url = builder.build();
			
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		get.setHeader("User_Agent", user_agent);
		get.setHeader("Content-Type","application/xml");
		get.setHeader("Accept","application/xml");
		
		HttpResponse response = client.execute(get);
		
		logger.debug("\nSending 'GET' request to URL : " + url);
		logger.debug("Response Code : " +
                                    response.getStatusLine().getStatusCode());
		
		BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		Integer counter = 0;
		while ((line = rd.readLine()) != null) {
			result.append(line);
			counter ++;
		}
			
		
		if(counter == 1) {
			toc.setHasData(true);
		}
		
		toc.setResult(result.toString());
		
		logger.debug("isbn : " + toc.getIsbn() + " . It has data : " + toc.getHasData());
		
		return toc;
	}
}
