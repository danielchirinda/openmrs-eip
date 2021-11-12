package org.openmrs.eip.component.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.openmrs.eip.component.model.DrugOrderModel;
import org.openmrs.eip.component.model.OrderModel;
import org.openmrs.eip.component.model.PatientModel;
import org.openmrs.eip.component.model.PersonModel;
import org.openmrs.eip.component.model.TestOrderModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Utils {
	
	protected static final Logger log = LoggerFactory.getLogger(Utils.class);
	
	private static final String STATS_FILENAME = ".dbsync_stats.properties";
	
	//Property for count of messages sent to ActiveMQ but yet confirmed
	private static final String PROP_SENT_UNCONFIRMED = "sent_unconfirmed";
	
	//Property for count of messages sent to ActiveMQ and confirmed
	private static final String PROP_SENT_CONFIRMED = "sent_confirmed";
	
	private static Properties stats;
	
	/**
	 * Gets comma-separated list of table names surrounded with apostrophes associated to the specified
	 * table as tables of subclass or superclass entities.
	 *
	 * @param tableName the tables to inspect
	 * @return a list of table names
	 */
	public static List<String> getListOfTablesInHierarchy(String tableName) {
		//TODO This logic should be extensible
		List<String> tables = new ArrayList();
		tables.add(tableName);
		if ("person".equalsIgnoreCase(tableName) || "patient".equalsIgnoreCase(tableName)) {
			tables.add("person".equalsIgnoreCase(tableName) ? "patient" : "person");
		} else if ("orders".equalsIgnoreCase(tableName)) {
			tables.add("test_order");
			tables.add("drug_order");
		} else if ("test_order".equalsIgnoreCase(tableName) || "drug_order".equalsIgnoreCase(tableName)) {
			tables.add("orders");
		}
		
		return tables;
	}
	
	/**
	 * Gets the tables associated to the specified table as tables of subclass or superclass entities.
	 *
	 * @param tableName the tables to inspect
	 * @return a comma-separated list of table names
	 */
	public static String getTablesInHierarchy(String tableName) {
		List<String> tables = getListOfTablesInHierarchy(tableName);
		return String.join(",", tables.stream().map(tName -> "'" + tName + "'").collect(Collectors.toList()));
	}
	
	/**
	 * Gets comma-separated list of model class names surrounded with apostrophes that are subclasses or
	 * superclasses of the specified class name.
	 *
	 * @param modelClass the model class to inspect
	 * @return a list of model class names
	 */
	public static List<String> getListOfModelClassHierarchy(String modelClass) {
		//TODO This logic should be extensible
		List<String> tables = new ArrayList();
		tables.add(modelClass);
		if (PersonModel.class.getName().equals(modelClass) || PatientModel.class.getName().equals(modelClass)) {
			tables.add(
			    PersonModel.class.getName().equals(modelClass) ? PatientModel.class.getName() : PersonModel.class.getName());
		} else if (OrderModel.class.getName().equals(modelClass)) {
			tables.add(TestOrderModel.class.getName());
			tables.add(DrugOrderModel.class.getName());
		} else if (TestOrderModel.class.getName().equals(modelClass) || DrugOrderModel.class.getName().equals(modelClass)) {
			tables.add(OrderModel.class.getName());
		}
		
		return tables;
	}
	
	/**
	 * Gets all the model classes that are subclasses or superclass of the specified class name.
	 *
	 * @param modelClass the model class to inspect
	 * @return a comma-separated list of model class names
	 */
	public static String getModelClassesInHierarchy(String modelClass) {
		List<String> classes = getListOfModelClassHierarchy(modelClass);
		return String.join(",", classes.stream().map(clazz -> "'" + clazz + "'").collect(Collectors.toList()));
	}
	
	private static synchronized File getStatsFile() {
		return new File(STATS_FILENAME);
	}
	
	private static synchronized Properties getStats() throws Exception {
		if (stats == null) {
			//First load stats from the database
			//If file doesnt exist but we have counts in DB, what happened to the file?
			//If file count if less than DB count, what happened?
			//Flush file contents to DB 
			
			File file = getStatsFile();
			if (log.isDebugEnabled()) {
				log.debug("Loading stats from file: " + file);
			}
			
			stats = new Properties();
			stats.load(new FileInputStream(getStatsFile()));
		}
		
		return stats;
	}
	
	private static synchronized void saveStats() throws Exception {
		getStats().store(new FileOutputStream(getStatsFile()), null);
	}
	
	public static Long getSentUnconfirmed() {
		return Long.valueOf(stats.getProperty(PROP_SENT_UNCONFIRMED), 0);
	}
	
	public static Long getSentConfirmed() {
		return Long.valueOf(stats.getProperty(PROP_SENT_CONFIRMED), 0);
	}
	
	public static synchronized void incrementSentUnconfirmed() throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Incrementing the count of unconfirmed sent events");
		}
		
		getStats().setProperty(PROP_SENT_UNCONFIRMED, Long.valueOf(getSentUnconfirmed().longValue() + 1).toString());
		
		if (log.isDebugEnabled()) {
			log.debug("Saving the count of sent unconfirmed events");
		}
		
		saveStats();
		
		if (log.isDebugEnabled()) {
			log.debug("Saving the count of sent unconfirmed events");
		}
	}
	
	public static synchronized void incrementSentConfirmed() throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Incrementing the count of confirmed sent events");
		}
		
		getStats().setProperty(PROP_SENT_CONFIRMED, Long.valueOf(getSentConfirmed().longValue() + 1).toString());
		
		if (log.isDebugEnabled()) {
			log.debug("Successfully saved the count of confirmed sent events");
		}
		
		saveStats();
		
		if (log.isDebugEnabled()) {
			log.debug("Successfully saved the count of confirmed sent events");
		}
	}
	
}
