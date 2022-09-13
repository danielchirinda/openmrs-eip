package org.openmrs.eip.web.sender.services;

import java.util.Date;
import java.util.UUID;

import org.apache.camel.ProducerTemplate;
import org.openmrs.eip.app.management.entity.SenderSyncMessageDetail;
import org.openmrs.eip.app.management.entity.SenderSyncRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenderControllerService {
	
	@Autowired
	protected ProducerTemplate producerTemplate;

	public void sendItemToRetryQueue(SenderSyncMessageDetail syncMessage) {
		
		SenderSyncRequest senderSyncRequest = new SenderSyncRequest();
		senderSyncRequest.setDateCreated(new Date());
		senderSyncRequest.setTableName(syncMessage.getTableName());
		senderSyncRequest.setIdentifier(syncMessage.getIdentifier());
		senderSyncRequest.setRequestUuid(UUID.randomUUID().toString());
		
		producerTemplate.sendBody("jpa:SenderSyncRequest?usePersist=true", senderSyncRequest);

	}

}
