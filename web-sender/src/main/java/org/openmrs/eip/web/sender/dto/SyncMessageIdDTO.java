package org.openmrs.eip.web.sender.dto;

import java.util.List;

public class SyncMessageIdDTO {

	private List<Integer> syncMessages;

	public List<Integer> getSyncMessages() {
		return syncMessages;
	}

	public void setSyncMessages(List<Integer> syncMessages) {
		this.syncMessages = syncMessages;
	}

}
