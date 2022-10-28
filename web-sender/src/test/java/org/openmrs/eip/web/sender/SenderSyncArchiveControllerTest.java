package org.openmrs.eip.web.sender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.openmrs.eip.app.SyncConstants.MGT_DATASOURCE_NAME;
import static org.openmrs.eip.app.SyncConstants.MGT_TX_MGR;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openmrs.eip.app.management.entity.sender.SenderSyncArchive;
import org.openmrs.eip.app.sender.BaseSenderTest;
import org.openmrs.eip.component.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@Sql(scripts = "classpath:mgt_sender_sync_archive.sql", config = @SqlConfig(dataSource = MGT_DATASOURCE_NAME, transactionManager = MGT_TX_MGR))
public class SenderSyncArchiveControllerTest extends BaseSenderTest {
	
	@Autowired
	private SenderSyncArchiveController controller;
	
	@Test
	public void shouldGetAllArchivesMessages() {
		Map result = controller.getAll();
		assertEquals(2, result.size());
		assertEquals(3, result.get("count"));
		assertEquals(3, ((List) result.get("items")).size());
		
	}
	
	@Test
	public void shouldDosearchByEventDate() {
		
		// Do Search with startDate and EndDate
		String stardDate = "2022-10-25";
		String endDate = "2022-10-30";
		
		Map results = controller.doSearchByPeriod(stardDate, endDate, Constants.EVENT_DATE);
		assertEquals(1, results.get("count"));
		assertNotNull(((List<SenderSyncArchive>) results.get("items")).get(0).getDateCreated());
		assertNotNull(((List<SenderSyncArchive>) results.get("items")).get(0).getEventDate());
		assertNotNull(((List<SenderSyncArchive>) results.get("items")).get(0).getDateSent());
		
		// Do Search with endDate
		stardDate = "";
		endDate = "2022-10-23";
		
		Map result = controller.doSearchByPeriod(stardDate, endDate, Constants.EVENT_DATE);
		assertEquals(1, results.get("count"));
		assertEquals("e2bc25aa-1d5f-11e0-b929-000c29ad1d07",
		    ((List<SenderSyncArchive>) result.get("items")).get(0).getIdentifier());
		assertNotNull(((List<SenderSyncArchive>) result.get("items")).get(0).getDateCreated());
		assertNotNull(((List<SenderSyncArchive>) result.get("items")).get(0).getEventDate());
		assertNotNull(((List<SenderSyncArchive>) result.get("items")).get(0).getDateSent());
		
		// Do Search with startDate
		stardDate = "2022-10-30";
		endDate = "";
		
		Map res = controller.doSearchByPeriod(stardDate, endDate, Constants.EVENT_DATE);
		assertEquals(0, res.get("count"));
		assertEquals(0, ((List) res.get("items")).size());
		assertEquals(2, res.size());
		
	}
	
}
