package org.openmrs.eip.app.management.repository;

import java.util.List;

import org.openmrs.eip.app.management.entity.SenderSyncMessageHistory;
import org.openmrs.eip.app.utils.Count;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SenderSyncMessageHistoryRepository extends JpaRepository<SenderSyncMessageHistory, Long> {
	
	@Query(value = " select table_.table_name as tableName, received as receivedItens, sent as sentItens, newer as  nonReceivedItens "
			+ " from "
			+ "	( "
			+ "		(select distinct(table_name) table_name  from sender_sync_message_history) table_ "
			+ "			left join (select table_name, count(*) received  "
			+ "						from sender_sync_message_history  "
			+ "						where status = 'RECEIVED' "
			+ "						group by table_name) received_ on received_.table_name = table_.table_name "
			+ "			"
			+ "			left join (select table_name, count(*) sent "
			+ "						from sender_sync_message_history   "
			+ "						where status = 'SENT' "
			+ "						group by table_name) sent_ on sent_.table_name = table_.table_name "
			+ "			 "
			+ "			left join (select table_name, count(*) newer "
			+ "						from sender_sync_message_history "
			+ "						where status = 'NEW' "
			+ "						group by table_name) newer_ on newer_.table_name = table_.table_name "
			+ "						 "
			+ "	) ", 
			  nativeQuery = true)
	public List<Count> fetchSyncHistory();
	
	@Query(value = " select table_.table_name as tableName, received as receivedItens, sent as sentItens, newer as  nonReceivedItens "
			+ " from "
			+ "	( "
			+ "		(select distinct(table_name) table_name  from sender_sync_message_history) table_ "
			+ "			left join (select table_name, count(*) received  "
			+ "						from sender_sync_message_history  "
			+ "						where status = 'RECEIVED' and (date_created > :startDate and date_created < :endDate) "
			+ "						group by table_name) received_ on received_.table_name = table_.table_name "
			+ "			"
			+ "			left join (select table_name, count(*) sent "
			+ "						from sender_sync_message_history   "
			+ "						where status = 'SENT' and (date_created > :startDate and date_created < :endDate) "
			+ "						group by table_name) sent_ on sent_.table_name = table_.table_name "
			+ "			 "
			+ "			left join (select table_name, count(*) newer "
			+ "						from sender_sync_message_history "
			+ "						where status = 'NEW' and (date_created > :startDate and date_created < :endDate) "
			+ "						group by table_name) newer_ on newer_.table_name = table_.table_name "
			+ "						 "
			+ "	) ", 
			  nativeQuery = true)
	public List<Count> fetchSyncHistoryByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
