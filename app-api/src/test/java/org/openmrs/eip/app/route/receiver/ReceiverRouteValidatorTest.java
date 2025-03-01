package org.openmrs.eip.app.route.receiver;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.openmrs.eip.app.route.BaseRouteValidatorTest;

public class ReceiverRouteValidatorTest extends BaseRouteValidatorTest {
	
	private static final Set<String> retryErrorHandlerRoutes;
	
	static {
		retryErrorHandlerRoutes = new HashSet();
		retryErrorHandlerRoutes.add("inbound-db-sync");
		retryErrorHandlerRoutes.add("message-processor");
		retryErrorHandlerRoutes.add("receiver-retry");
		retryErrorHandlerRoutes.add("receiver-update-search-index");
		retryErrorHandlerRoutes.add("receiver-clear-db-cache");
	}
	
	@Override
	public String getAppFolder() {
		return "receiver";
	}
	
	@Override
	public String getRetryHandlerRef() {
		return "inBoundErrorHandler";
	}
	
	@Override
	public Set<String> getRoutesWithRetryHandler() {
		return retryErrorHandlerRoutes;
	}
	
	@Override
	public Set<String> getRoutesWithDeadLetterChannelHandler() {
		return Collections.singleton("update-site-last-sync-date");
	}
	
}
