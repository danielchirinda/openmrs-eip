package org.openmrs.eip.web.sender.factory;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.eip.web.dto.SenderSearchDTO;
import org.openmrs.eip.web.sender.dto.SyncMessageId;

public  final class SendSyncMessageFactory {

	public SyncMessageId createSyncMessageIdDTOFactory() {

		SyncMessageId messageIdDTO = new SyncMessageId();
		List<Integer> syncMessages = new ArrayList<>();
		syncMessages.add(null);
		messageIdDTO.setSyncMessages(syncMessages);

		return messageIdDTO;
	}
	
	public final static SenderSearchDTO createSenderSearchDTOFactory() {
		SenderSearchDTO searchDTO = new SenderSearchDTO();
		searchDTO.setTableName("person");
		searchDTO.setStartDate("2022-09-15 00:39:39.179000000");
		searchDTO.setEndDate("2022-09-20 00:39:39.179000000");
		
		return searchDTO;
	}
}
