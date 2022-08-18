package org.openmrs.eip.web.contoller;

import static org.apache.camel.impl.engine.DefaultFluentProducerTemplate.on;
import static org.openmrs.eip.web.RestConstants.DEFAULT_MAX_COUNT;
import static org.openmrs.eip.web.RestConstants.FIELD_COUNT;
import static org.openmrs.eip.web.RestConstants.FIELD_ITEMS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.openmrs.eip.app.management.entity.SenderSyncMessageDetail;
import org.openmrs.eip.web.dto.SyncStatusDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;

public abstract class BaseRestController {

	protected static final Logger log = LoggerFactory.getLogger(BaseRestController.class);

	@Autowired
	protected ProducerTemplate producerTemplate;

	@Autowired
	protected CamelContext camelContext;
	@Autowired
	ConfigurableEnvironment env;

	public Map<String, Object> doGetAll() {
		Map<String, Object> results = new HashMap<String, Object>(2);
		Integer count = on(camelContext).to("jpa:" + getName() + "?query=SELECT count(*) FROM " + getName())
				.request(Integer.class);

		results.put(FIELD_COUNT, count);

		List<Object> items;
		if (count > 0) {
			items = on(camelContext).to("jpa:" + getName() + "?query=SELECT c FROM " + getName() + " c &maximumResults="
					+ DEFAULT_MAX_COUNT).request(List.class);

			results.put(FIELD_ITEMS, items);
		} else {
			results.put(FIELD_ITEMS, Collections.emptyList());
		}

		return results;
	}

	public Object doGet(Integer id) {
		return producerTemplate.requestBody(
				"jpa:" + getName() + "?query=SELECT c FROM " + getName() + " c WHERE c.id = " + id, null, getClazz());
	}

	public void doDelete(Integer id) {
		on(camelContext).to("jpa:" + getName() + "?query=DELETE FROM " + getName() + " WHERE id = " + id).request();
	}

	/**
	 * Count
	 * 
	 * @param <CountDTO>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> doCount() {

		Map<String, Object> results = new HashMap<String, Object>(2);

		List<SenderSyncMessageDetail> messageItens = on(camelContext).to(
				"jpa:" + getName() + "?query=SELECT s" + " FROM " + getName() + " s order by " + " s.tableName asc ")
				.request(List.class);

		results.put(FIELD_COUNT, messageItens.size());

		List<SyncStatusDetailDTO> items = new ArrayList<>();

		messageItens.forEach((sync) -> {

			SyncStatusDetailDTO dto = this.findSyncDetailByTableName(sync.getTableName(), items);
			if (dto != null) {
				items.forEach((item) -> {

					if (item.getTableName().equals(sync.getTableName())) {
						if (sync.getStatus().toString().equals(SyncStatusDetailDTO.RECEIVED)) {
							item.setReceivedItens(item.getReceivedItens() + 1);

						} else if (sync.getStatus().toString().equals(SyncStatusDetailDTO.SENT)) {
							item.setSetItens(item.getSetItens() + 1);

						} else if (sync.getStatus().toString().equals(SyncStatusDetailDTO.NEW)) {
							item.setNonReceivedItens(item.getNonReceivedItens() + 1);
						}

					}
				});
			} else {
				SyncStatusDetailDTO syncStatusDetail = new SyncStatusDetailDTO();
				syncStatusDetail.setTableName(sync.getTableName());

				if (sync.getStatus().toString().equals(SyncStatusDetailDTO.RECEIVED)) {
					syncStatusDetail.setReceivedItens(1);
				}

				if (sync.getStatus().toString().equals(SyncStatusDetailDTO.SENT)) {
					syncStatusDetail.setSetItens(1);
				}

				if (sync.getStatus().toString().equals(SyncStatusDetailDTO.NEW)) {
					syncStatusDetail.setNonReceivedItens(1);
				}

				items.add(syncStatusDetail);
			}

		});
		results.put(FIELD_ITEMS, items);

		/*
		 * List<CountDTO> receivedItens = on(camelContext).toF("jpa:" + getName() +
		 * "?query=SELECT count(*), s.status, s.tableName " + " FROM " + getName() +
		 * " s " +
		 * " where s.status='RECEIVED' group by s.tableName having count(*) >= 1 order by "
		 * + " s.tableName desc ") .request(List.class);
		 * 
		 * List<CountDTO> nonReceivedItens = on(camelContext).to("jpa:" + getName() +
		 * "?query=SELECT count(*), s.status, s.tableName " + " FROM " + getName() +
		 * " s " +
		 * " where s.status='NEW' group by s.tableName having count(*) >= 1 order by " +
		 * " s.tableName desc ") .request(List.class);
		 */

		/*
		 * for (SenderSyncMessageDetail countDTO : SentItens) {
		 * System.out.println("COunt DTO VALUE" + countDTO.getTableName()); }
		 * List<CountDTO> itens = new ArrayList<CountDTO>(); //itens.addAll(SentItens);
		 * //itens.addAll(receivedItens); //itens.addAll(nonReceivedItens);
		 */
		return results;
	}

	/*
	 * public Long doGetByIdentifierAndTableName(String identifier, String
	 * tableName) {
	 * 
	 * final String dbName = env.getProperty("openmrs.db.name");
	 * 
	 * return on(camelContext).to("jpa:" + tableName +
	 * "?nativeQuery=SELECT p.uuid FROM " + dbName + "." + tableName +
	 * " p WHERE p.identifier=" + "'" + identifier + "'").request(Long.class);
	 * 
	 * 
	 * return producerTemplate.requestBody( "jpa:" + tableName +
	 * "?query=SELECT p FROM " + tableName + " p WHERE p.identifier =" +
	 * identifier);
	 * 
	 * }
	 */

	private SyncStatusDetailDTO findSyncDetailByTableName(String tableName, List<SyncStatusDetailDTO> details) {

		for (SyncStatusDetailDTO syncStatusDetailDTO : details) {
			if (syncStatusDetailDTO.getTableName().equals(tableName)) {
				return syncStatusDetailDTO;
			}
		}

		return null;

	}

	public String getName() {
		return getClazz().getSimpleName();
	}

	public abstract Class<?> getClazz();

}
