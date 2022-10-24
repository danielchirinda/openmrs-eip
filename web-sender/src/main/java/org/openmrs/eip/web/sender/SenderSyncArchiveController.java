package org.openmrs.eip.web.sender;

import java.util.Map;

import org.openmrs.eip.app.management.entity.sender.SenderSyncArchive;
import org.openmrs.eip.web.RestConstants;
import org.openmrs.eip.web.contoller.BaseRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestConstants.API_PATH + "/dbsync/sender/archive")
public class SenderSyncArchiveController extends BaseRestController {
	
	private static final Logger log = LoggerFactory.getLogger(SenderSyncArchiveController.class);
	
	@Override
	public Class<?> getClazz() {
		return SenderSyncArchive.class;
	}
	
	@GetMapping
	public Map<String, Object> getAll() {
		if (log.isDebugEnabled()) {
			log.debug("Fetching sender sync Archives");
		}
		
		return doGetAll();
	}
	
	@GetMapping
	@RequestMapping(params = {"startDate","endDate"})
    public Map<String, Object> getArchivedEventByDate(@RequestParam(name = "startDate") String startDate,  @RequestParam( name = "endDate") String enddDate) {
        if (log.isDebugEnabled()) {
            log.debug("Fetching archived events: ");
        }

        log.info("Search Data : " + startDate + "End Date - " + enddDate);
        return getByDateCreated(startDate, enddDate);
    }
	
}
