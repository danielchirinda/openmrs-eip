package org.openmrs.eip.web.sender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.openmrs.eip.app.SyncConstants.MGT_DATASOURCE_NAME;
import static org.openmrs.eip.app.SyncConstants.MGT_TX_MGR;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openmrs.eip.app.management.entity.SenderSyncMessageHistory;
import org.openmrs.eip.app.sender.BaseSenderTest;
import org.openmrs.eip.web.sender.factory.SendSyncMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@Sql(scripts = "classpath:mgt_sender_sync_message_history.sql", config = @SqlConfig(dataSource = MGT_DATASOURCE_NAME, transactionManager = MGT_TX_MGR))
public class SenderSyncMessageHistoryControllerTest extends BaseSenderTest {
	
	@Autowired
	private SenderSyncMessageHistoryController controller;
	
	@Test
	public void shouldGetAllSyncMessageHistory() {
		Map result = controller.getAll();
		assertEquals(2, result.size());
		assertEquals(2, result.get("count"));
		assertEquals(2, ((List) result.get("items")).size());
	}
	
	@Test
	public void shouldGetSyncMessageHistoryById() {
		assertTrue( controller.get(new Integer(1)) != null );
		
	}
	
	@Test
	public void getetTheSyncMessageMatchingTheSpecifiedId() {
		assertEquals("0eae0087-4ddb-4765-9b59-b38c6fb95b32", ((SenderSyncMessageHistory) controller.get(1)).getMessageUuid());
	}
	
	@Test
	public void shouldGetSyncEventByDate() {
		Map result  = controller.syncEventByDate(SendSyncMessageFactory.createSenderSearchDTOFactory());
		
		assertNotNull(result);
		assertEquals(1, result.get("count"));
		assertEquals("person", ((List<SenderSyncMessageHistory>)result.get("items")).get(0).getTableName());
		assertEquals("6abb7ae5-78a0-4365-b08a-90217a886606", ((List<SenderSyncMessageHistory>)result.get("items")).get(0).getMessageUuid());
		
	}
	
}
