package org.openmrs.eip.app;

import static java.util.Collections.synchronizedList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.openmrs.eip.app.management.entity.AbstractEntity;
import org.openmrs.eip.component.SyncContext;

/**
 * Base class for sender processors that operate on items in a DB sync related queue and forward
 * each item to another handler camel endpoint for processing
 * 
 * @param <T>
 */
public abstract class BaseSenderQueueProcessor<T extends AbstractEntity> extends BaseParallelProcessor {
	
	@Override
	public void process(Exchange exchange) throws Exception {
		if (producerTemplate == null) {
			producerTemplate = SyncContext.getBean(ProducerTemplate.class);
		}
		
		List<String> uniqueKeys = synchronizedList(new ArrayList(threadCount));
		List<CompletableFuture<Void>> syncThreadFutures = synchronizedList(new ArrayList(threadCount));
		List<T> items = exchange.getIn().getBody(List.class);
		
		for (T item : items) {
			final String key = getItemKey(item);
			if (isSnapshot(item) && !uniqueKeys.contains(key)) {
				uniqueKeys.add(key);
				if (executor == null) {
					executor = Executors.newFixedThreadPool(threadCount);
				}
				
				//TODO Periodically wait and reset futures to save memory
				syncThreadFutures.add(CompletableFuture.runAsync(() -> {
					final String originalThreadName = Thread.currentThread().getName();
					try {
						setThreadName(item);
						producerTemplate.sendBody(getDestinationUri(), item);
					}
					finally {
						Thread.currentThread().setName(originalThreadName);
					}
				}, executor));
			} else {
				final String originalThreadName = Thread.currentThread().getName();
				try {
					setThreadName(item);
					if (syncThreadFutures.size() > 0) {
						waitForFutures(syncThreadFutures);
						syncThreadFutures.clear();
					}
					
					producerTemplate.sendBody(getDestinationUri(), item);
				}
				finally {
					Thread.currentThread().setName(originalThreadName);
				}
			}
		}
		
		if (syncThreadFutures.size() > 0) {
			waitForFutures(syncThreadFutures);
		}
	}
	
	private void setThreadName(T item) {
		Thread.currentThread().setName(Thread.currentThread().getName() + ":" + getQueueName() + ":" + getThreadName(item));
	}
	
	/**
	 * Generate a unique key for the item
	 * 
	 * @param item the queue item
	 * @return the key
	 */
	public abstract String getItemKey(T item);
	
	/**
	 * Checks if the item is for a snapshot event or not
	 * 
	 * @param item the queue item
	 * @return true for a snapshot otherwise false
	 */
	public abstract boolean isSnapshot(T item);
	
	/**
	 * Gets the logical queue name
	 * 
	 * @return the logical name
	 */
	public abstract String getQueueName();
	
	/**
	 * Generate a unique name for the thread that will process the item
	 * 
	 * @param item the queue item
	 * @return the thread name
	 */
	public abstract String getThreadName(T item);
	
	/**
	 * Gets the camel URI to call to process a single item
	 * 
	 * @return the camel URI
	 */
	public abstract String getDestinationUri();
	
}
