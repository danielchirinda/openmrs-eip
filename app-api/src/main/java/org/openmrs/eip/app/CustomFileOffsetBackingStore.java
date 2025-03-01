package org.openmrs.eip.app;

import org.apache.kafka.connect.storage.FileOffsetBackingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom {@link FileOffsetBackingStore} that only saves the offset if no exception was encountered
 * while processing a source record read by debezium from the MySQL binlog to ensure no binlog entry
 * goes unprocessed.
 */
public class CustomFileOffsetBackingStore extends FileOffsetBackingStore {
	
	protected static final Logger log = LoggerFactory.getLogger(CustomFileOffsetBackingStore.class);
	
	private static boolean disabled = false;
	
	private static boolean paused = false;
	
	public synchronized static void disable() {
		disabled = true;
		if (log.isDebugEnabled()) {
			log.debug("Disabled saving of offsets");
		}
	}
	
	public synchronized static boolean isDisabled() {
		return disabled;
	}
	
	public synchronized static boolean isPaused() {
		return paused;
	}
	
	public synchronized static void pause() {
		paused = true;
		if (log.isDebugEnabled()) {
			log.debug("Pause saving of offsets");
		}
	}
	
	public synchronized static void unpause() {
		paused = false;
		if (log.isDebugEnabled()) {
			log.debug("Removing pause on saving of offsets");
		}
	}
	
	/**
	 * @see FileOffsetBackingStore#save()
	 */
	@Override
	protected void save() {
		synchronized (CustomFileOffsetBackingStore.class) {
			if (disabled || paused) {
				if (paused) {
					if (log.isDebugEnabled()) {
						log.debug("Skipping saving of offset because it is paused");
					}
				} else {
					log.warn("Skipping saving of offset because an error was encountered while processing a source record");
				}
				
				return;
			}
			
			if (log.isDebugEnabled()) {
				log.debug("Saving binlog offset");
			}
			
			doSave();
		}
	}
	
	protected void doSave() {
		super.save();
	}
	
}
