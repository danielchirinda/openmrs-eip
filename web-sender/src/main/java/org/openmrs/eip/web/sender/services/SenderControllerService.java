package org.openmrs.eip.web.sender.services;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.support.DefaultExchange;
import org.openmrs.eip.app.management.entity.SenderRetryQueueItem;
import org.openmrs.eip.app.management.entity.SenderSyncMessageDetail;
import org.openmrs.eip.component.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenderControllerService {

	@EndpointInject(uri = "direct:outbound-error-handler")
	@Autowired
	protected ProducerTemplate producerTemplate;

	@Autowired
	private CamelContext camelContext;

	public void sendItemToRetryQueue(SenderSyncMessageDetail syncMessage) {

		SenderRetryQueueItem retryQueueItem = new SenderRetryQueueItem();
		retryQueueItem.setAttemptCount(0);
		retryQueueItem.setExceptionType("org.openmrs.eip.component.exception");

		Event event = new Event();
		event.setIdentifier(syncMessage.getIdentifier());
		event.setOperation(syncMessage.getOperation());
		event.setTableName(syncMessage.getTableName());
		event.setSnapshot(Boolean.FALSE);

		retryQueueItem.setMessage(event.toString());
		//Apenas pra efeitos de testes
		event.setPrimaryKeyId(String.valueOf(1));

		retryQueueItem.setEvent(event);

		Exchange exchange = new DefaultExchange(camelContext);
		//	exchange.getIn().setBody(retryQueueItem);
		exchange.getIn().setBody(exchange, SenderRetryQueueItem.class);

		exchange.setProperty("event", event);
		exchange.setProperty("exceptionType", "org.openmrs.eip.component.exception");
		exchange.setProperty("message", "Its Working");
		producerTemplate.send(exchange);
	}

}
