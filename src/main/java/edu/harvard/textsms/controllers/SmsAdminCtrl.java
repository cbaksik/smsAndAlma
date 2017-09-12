package edu.harvard.textsms.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.harvard.textsms.models.SmsAdminModel;
import edu.harvard.textsms.models.SmsTracking;
import edu.harvard.textsms.services.SmsService;

@PropertySource("classpath:application.properties")
@RestController
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class SmsAdminCtrl {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SmsService smsService;
	
	@Value("${config.sms.admin.username}")
	private String username;
	
	@Value("${config.sms.admin.password}")
	private String password;
	
	private List<SmsTracking> listSMS;
	
	// constructor
	SmsAdminCtrl() {
		this.listSMS = new ArrayList<SmsTracking>();
	}
	
	@RequestMapping(value="/smsAdmin", method=RequestMethod.POST)
	public List<SmsTracking> getSMSList(@RequestBody SmsAdminModel smsAdminModel) {
		if(this.username.equals(smsAdminModel.getUsername()) && this.password.equals(smsAdminModel.getPassword())) {
			this.listSMS = smsService.getSmsList();
		} else {
			this.listSMS = new ArrayList<SmsTracking>();
		}
		logger.debug("*** list sms ***");
		return this.listSMS;
	}
	
	@RequestMapping(value="/smsAdmin", method=RequestMethod.DELETE)
	public List<SmsTracking> deleteSMSList(@RequestBody SmsAdminModel smsAdminModel) {
		this.listSMS = new ArrayList<SmsTracking>();
		if(this.username.equals(smsAdminModel.getUsername()) && this.password.equals(smsAdminModel.getPassword())) {
			smsService.setSmsList(listSMS);
		} 
		logger.debug("*** delete sms list ***");
		return this.listSMS;
	}

}
