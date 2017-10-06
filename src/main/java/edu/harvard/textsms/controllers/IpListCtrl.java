/* This use to validate client ip address to see if the user is
 * an Internal user or external user. The status will return true if a user is an internal user.
 * When it is true, a user can view restrict image on browser without prompt to login.
 */

package edu.harvard.textsms.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.harvard.textsms.models.IpListModel;
import edu.harvard.textsms.services.IpListService;

@RestController
public class IpListCtrl {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IpListService ipListService;
	
	@RequestMapping(value="/validateIp", method=RequestMethod.POST)
	public IpListModel validateIP(@RequestBody IpListModel ipList) {
		ipList = ipListService.validateIp(ipList);
		
		logger.debug("This user of ip address : " + ipList.getIp());
		logger.debug("The status : " + ipList.getStatus());
		
		return ipList;
	}
	
}
