package edu.harvard.textsms.services;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import edu.harvard.textsms.models.IpListModel;

@PropertySource("classpath:application.properties")
@Service("IpListService")
public class IpListService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${config.iplist.filename}")
	private String fileName;
	
	private StringBuilder result;
	private JSONArray ipList;
	
	IpListService () {
		this.result = new StringBuilder("");	
		this.ipList = new JSONArray();
	}
	
	// 
	public JSONArray readIpList () {	
		if(result.toString().isEmpty()) {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(this.fileName).getFile());
			try (Scanner scanner = new Scanner(file)) {
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					this.result.append(line).append("\n");
				}
				scanner.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			logger.debug("Read ip list file : " + fileName);
			ipList = new JSONArray(result.toString());
		} else {
			ipList = new JSONArray(result.toString());
		}
		
		return ipList;
	}
	
	public IpListModel validateIp(IpListModel ipModel) {
		ipModel.setStatus(false);
		JSONArray ips = this.readIpList();
		for(int i=0; i < ips.length(); i++) {
			JSONObject obj = ips.getJSONObject(i);
			String start = obj.getString("start");
			String end = obj.getString("end");
			if(start.equals(end)) {
				if(ipModel.getIp().equals(start)) {
					ipModel.setStatus(true);
					i = ips.length() ;
				}
			} else {
				// find range of ip address
				String[] listStartIp = start.split("\\.");
				String[] listEndIp = end.split("\\.");
				String ip = ipModel.getIp();
				String[] clientIps = ip.split("\\.");
				
				String start3 = listStartIp[0] + "." + listStartIp[1] + "." + listStartIp[2];
				String client3 = clientIps[0] + "." + clientIps[1] + "." + clientIps[2];
				
				// compare the first 3 section of ip address if they are equal, then find the range
				if(start3.equals(client3)) {
					int userIp = Integer.parseInt(clientIps[3]);
					int startIp = Integer.parseInt(listStartIp[3]);
					int endIp  = Integer.parseInt(listEndIp[3]);
					// find the range of ip if the last section in between start ip and end ip
					if( userIp >= startIp && userIp <= endIp ) {
						ipModel.setStatus(true);
						i=ips.length();
					}
				}
				
			}
		}
		
		return ipModel;
	}

}
