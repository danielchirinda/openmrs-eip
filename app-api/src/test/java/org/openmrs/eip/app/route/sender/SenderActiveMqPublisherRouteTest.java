package org.openmrs.eip.app.route.sender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.openmrs.eip.app.management.entity.SenderSyncMessage.SenderSyncMessageStatus.NEW;
import static org.openmrs.eip.app.management.entity.SenderSyncMessage.SenderSyncMessageStatus.SENT;
import static org.openmrs.eip.app.route.sender.SenderActiveMqPublisherRouteTest.SENDER_ID;
import static org.openmrs.eip.app.route.sender.SenderActiveMqPublisherRouteTest.URI_ACTIVEMQ_SYNC;
import static org.openmrs.eip.app.sender.SenderConstants.PROP_SENDER_ID;
import static org.openmrs.eip.app.sender.SenderConstants.ROUTE_ID_ACTIVEMQ_PUBLISHER;
import static org.openmrs.eip.app.sender.SenderConstants.URI_ACTIVEMQ_PUBLISHER;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.support.DefaultExchange;
import org.junit.Test;
import org.openmrs.eip.app.SyncConstants;
import org.openmrs.eip.app.management.entity.SenderSyncMessage;
import org.openmrs.eip.component.model.PersonModel;
import org.openmrs.eip.component.model.SyncMetadata;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import com.jayway.jsonpath.JsonPath;

import ch.qos.logback.classic.Level;

@TestPropertySource(properties = PROP_SENDER_ID + "=" + SENDER_ID)
@TestPropertySource(properties = "camel.output.endpoint=" + URI_ACTIVEMQ_SYNC)
@TestPropertySource(properties = "logging.level." + ROUTE_ID_ACTIVEMQ_PUBLISHER + "=DEBUG")
public class SenderActiveMqPublisherRouteTest extends BaseSenderRouteTest {
	
	public static final String URI_ACTIVEMQ_SYNC = "mock:activemq.openmrs.sync";
	
	public static final String SENDER_ID = "test-sender-id";
	
	@EndpointInject(URI_ACTIVEMQ_SYNC)
	private MockEndpoint mockActiveMqEndpoint;
	
	@Override
	public String getTestRouteFilename() {
		return ROUTE_ID_ACTIVEMQ_PUBLISHER;
	}
	
	private SenderSyncMessage createSyncMessage(String table, String identifier, String msgUuid, String op) {
		SenderSyncMessage msg = new SenderSyncMessage();
		msg.setTableName(table);
		msg.setIdentifier(identifier);
		msg.setMessageUuid(msgUuid);
		msg.setOperation(op);
		msg.setSnapshot(false);
		msg.setDateCreated(new Date());
		SenderTestUtils.saveEntity(msg);
		return msg;
	}
	
	@Test
	public void shouldLoadSyncMessagesSortedByDateCreatedAndSendThemToSyncQueue() throws Exception {
		mockActiveMqEndpoint.expectedMessageCount(0);
		Exchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send(URI_ACTIVEMQ_PUBLISHER, exchange);
		
		mockActiveMqEndpoint.assertIsSatisfied();
		assertMessageLogged(Level.DEBUG, "No sender sync messages found");
	}
	
	@Test
	@Sql(scripts = { "classpath:openmrs_core_data.sql", "classpath:openmrs_patient.sql" })
	@Sql(scripts = "classpath:mgt_sender_sync_message.sql", config = @SqlConfig(dataSource = SyncConstants.MGT_DATASOURCE_NAME, transactionManager = SyncConstants.MGT_TX_MGR))
	public void shouldLoadAndProcessAllNewSyncMessagesSortedByDateCreated() throws Exception {
		final int messageCount = 3;
		List<SenderSyncMessage> msgs = SenderTestUtils.getEntities(SenderSyncMessage.class).stream()
		        .filter(m -> m.getStatus() == NEW).collect(Collectors.toList());
		assertEquals(messageCount, msgs.size());
		assertTrue(msgs.get(0).getDateCreated().getTime() > (msgs.get(2).getDateCreated().getTime()));
		assertTrue(msgs.get(1).getDateCreated().getTime() > (msgs.get(2).getDateCreated().getTime()));
		mockActiveMqEndpoint.expectedMessageCount(messageCount);
		DefaultExchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send(URI_ACTIVEMQ_PUBLISHER, exchange);
		
		mockActiveMqEndpoint.assertIsSatisfied();
		assertMessageLogged(Level.INFO, "Fetched " + msgs.size() + " sender sync message(s)");
		assertEquals(messageCount, exchange.getIn().getBody(List.class).size());
		assertEquals(3, ((SenderSyncMessage) exchange.getIn().getBody(List.class).get(0)).getId().intValue());
		assertEquals(1, ((SenderSyncMessage) exchange.getIn().getBody(List.class).get(1)).getId().intValue());
		assertEquals(2, ((SenderSyncMessage) exchange.getIn().getBody(List.class).get(2)).getId().intValue());
		assertEquals(0,
		    SenderTestUtils.getEntities(SenderSyncMessage.class).stream().filter(m -> m.getStatus() == NEW).count());
	}
	
	@Test
	public void shouldProcessADeleteEvent() throws Exception {
		final String table = "person";
		final String uuid = "person-uuid";
		final String msgUuid = "msg-uuid";
		final String op = "d";
		assertTrue(SenderTestUtils.getEntities(SenderSyncMessage.class).isEmpty());
		SenderSyncMessage msg = createSyncMessage(table, uuid, msgUuid, op);
		assertEquals(1, SenderTestUtils.getEntities(SenderSyncMessage.class).size());
		mockActiveMqEndpoint.expectedMessageCount(1);
		final List<String> syncMessages = new ArrayList();
		mockActiveMqEndpoint.whenAnyExchangeReceived(e -> syncMessages.add(e.getIn().getBody(String.class)));
		DefaultExchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send(URI_ACTIVEMQ_PUBLISHER, exchange);
		
		mockActiveMqEndpoint.assertIsSatisfied();
		assertMessageLogged(Level.INFO, "Fetched 1 sender sync message(s)");
		assertEquals(1, SenderTestUtils.getEntities(SenderSyncMessage.class).size());
		assertEquals(SENT, SenderTestUtils.getEntity(SenderSyncMessage.class, msg.getId()).getStatus());
		assertEquals(1, syncMessages.size());
		
		SyncMetadata metadata = new SyncMetadata();
		metadata.setOperation(op);
		metadata.setSnapshot(false);
		metadata.setMessageUuid(msgUuid);
		metadata.setSourceIdentifier(SENDER_ID);
		String syncMsg = syncMessages.get(0);
		assertEquals(PersonModel.class.getName(), JsonPath.read(syncMsg, "tableToSyncModelClass"));
		assertEquals(uuid, JsonPath.read(syncMsg, "model.uuid"));
		assertEquals(SENDER_ID, JsonPath.read(syncMsg, "metadata.sourceIdentifier"));
		assertEquals(op, JsonPath.read(syncMsg, "metadata.operation"));
		assertEquals(msgUuid, JsonPath.read(syncMsg, "metadata.messageUuid"));
		assertEquals(false, JsonPath.read(syncMsg, "metadata.snapshot"));
		assertNotNull(JsonPath.read(syncMsg, "metadata.dateSent"));
	}
	
}
