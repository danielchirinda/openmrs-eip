package org.openmrs.eip.app.management.service;

import java.util.List;

import org.openmrs.eip.app.management.repository.SenderSyncMessageHistoryRepository;
import org.openmrs.eip.app.utils.CountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenderSyncMessageService {
	
	@Autowired
	private SenderSyncMessageHistoryRepository historyRepository;

	
	
	public List<CountDTO> fetchSyncHistory(){
		return historyRepository.fetchSyncHistory();
	}
	
	public List<CountDTO> fetchSyncHistoryByDate(String startDate, String endDate){
		return historyRepository.fetchSyncHistoryByDate(startDate,endDate);
	}
}
