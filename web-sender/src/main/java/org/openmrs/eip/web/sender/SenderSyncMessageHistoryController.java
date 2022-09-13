package org.openmrs.eip.web.sender;

import java.util.Map;

import org.openmrs.eip.app.management.entity.SenderSyncMessageHistory;
import org.openmrs.eip.web.RestConstants;
import org.openmrs.eip.web.contoller.BaseRestController;
import org.openmrs.eip.web.dto.SenderSearchDTO;
import org.openmrs.eip.web.sender.dto.SyncMessageIdDTO;
import org.openmrs.eip.web.sender.services.SenderControllerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestConstants.API_PATH + "/dbsync/sender/sync-details")
public class SenderSyncMessageHistoryController extends BaseRestController {

	private static final Logger log = LoggerFactory.getLogger(SenderSyncMessageHistoryController.class);

	@Autowired
	private SenderControllerService senderControllerService;

	@Override
	public Class<?> getClazz() {
		return SenderSyncMessageHistory.class;
	}

	@GetMapping
	public Map<String, Object> getAll() {
		if (log.isDebugEnabled()) {
			log.debug("Fetching sent message ");
		}

		return doGetAll();
	}

	@GetMapping("/{id}")
	public Object get(@PathVariable("id") Integer id) {
		if (log.isDebugEnabled()) {
			log.debug("Fetching Sync Message item with id: " + id);
		}

		return doGet(id);
	}

	@PatchMapping("/resend")
	public void resendEvent(@RequestBody SyncMessageIdDTO syncMessage) {
		if (log.isDebugEnabled()) {
			log.debug("Resend Sync Message List with size : " + syncMessage.getSyncMessages());
		}

		for (Integer id : syncMessage.getSyncMessages()) {
			SenderSyncMessageHistory message = (SenderSyncMessageHistory) doGet(id);

			if (message != null) {

				this.senderControllerService.resendMessageEvent(message);

			}
		}

	}

	@GetMapping("/status")
	public Map<String, Object> syncdDetails() {
		if (log.isDebugEnabled()) {
			log.debug("Fetching Sync Status: ");
		}

		return doCount();
	}
	
	@PostMapping("/sync-event")
	public Map<String, Object> syncEventByDate(@RequestBody SenderSearchDTO search) {
		if (log.isDebugEnabled()) {
			log.debug("Fetching Sync Status: ");
		}

		return getEventByDate(search);
	}

	@PostMapping("/sync-history")
	public Map<String, Object> syncHistoryByDate(@RequestBody SenderSearchDTO search) {
		if (log.isDebugEnabled()) {
			log.debug("Fetching Sync Status: ");
		}

		return getSyncHistoryByDate(search.getStartDate(), search.getEndDate());
	}

}
