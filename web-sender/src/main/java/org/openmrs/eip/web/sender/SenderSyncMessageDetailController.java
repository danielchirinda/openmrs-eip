package org.openmrs.eip.web.sender;

import java.util.Map;

import org.openmrs.eip.app.management.entity.SenderSyncMessageDetail;
import org.openmrs.eip.web.RestConstants;
import org.openmrs.eip.web.contoller.BaseRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestConstants.API_PATH + "/dbsync/sender/sync-details")
public class SenderSyncMessageDetailController extends BaseRestController {

	private static final Logger log = LoggerFactory.getLogger(SenderSyncMessageDetailController.class);

	@Override
	public Class<?> getClazz() {
		return SenderSyncMessageDetail.class;
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

	@PatchMapping("/{id}")
	public Object resendEvent(@PathVariable("id") Integer id) {
		if (log.isDebugEnabled()) {
			log.debug("Resend Sync Message with id: " + id);
		}

		SenderSyncMessageDetail syncMessage = (SenderSyncMessageDetail) doGet(id);

		log.info("Founded message to resend with" + syncMessage.toString());

		return syncMessage;
	}

	@GetMapping("/status")
	public Map<String, Object> syncdDetails() {
		if (log.isDebugEnabled()) {
			log.debug("Fetching Sync Status: " );
		}

		return doCount();
	}

}
