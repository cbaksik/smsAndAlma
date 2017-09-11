package edu.harvard.textsms.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.harvard.textsms.models.SmsModel;

@Service("SmsService")
public class SmsService {

	private List<SmsModel> smsList;
	
	SmsService(){
		smsList = new ArrayList<SmsModel>();
	}

	public List<SmsModel> getSmsList() {
		return smsList;
	}

	public void setSmsList(List<SmsModel> smsList) {
		this.smsList = smsList;
	}
	
	
}
