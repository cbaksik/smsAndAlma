package edu.harvard.textsms.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.harvard.textsms.models.SmsTracking;

@Service("SmsService")
public class SmsService {

	private List<SmsTracking> smsList;
	
	SmsService(){
		smsList = new ArrayList<SmsTracking>();
	}

	public List<SmsTracking> getSmsList() {
		return smsList;
	}

	public void setSmsList(List<SmsTracking> smsList) {
		this.smsList = smsList;
	}
	
	
}
