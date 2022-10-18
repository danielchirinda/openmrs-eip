package org.openmrs.eip.web.receiver;

import org.apache.camel.CamelContext;
import org.openmrs.eip.app.management.entity.receiver.ReceiverSyncArchive;
import org.openmrs.eip.component.SyncProfiles;
import org.openmrs.eip.web.Dashboard;
import org.openmrs.eip.web.contoller.DashboardGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(SyncProfiles.RECEIVER)
public class ReceiverDashboardGenerator implements DashboardGenerator {
	
	private static final String ARCHIVED_ENTITY_NAME = ReceiverSyncArchive.class.getSimpleName();
	
	protected CamelContext camelContext;
	
	@Autowired
	public ReceiverDashboardGenerator(CamelContext camelContext) {
		this.camelContext = camelContext;
	}
	
	/**
	 * @see DashboardGenerator#generate()
	 */
	@Override
	public Dashboard generate() {

	    return null;
    
	}
	
}
