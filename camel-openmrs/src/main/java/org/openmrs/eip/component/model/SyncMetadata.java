package org.openmrs.eip.component.model;

import java.time.LocalDateTime;

/**
 * Encapsulates descriptive data about a sync payload i.e. the unique ID of the site sending the.
 * payload, the date it was sent and the total count of events the sender has sent to activeMQ since
 * it was deployed including the event associated to this metadata object.
 */
public class SyncMetadata {
	
	private String sourceIdentifier;
	
	private String operation;
	
	private LocalDateTime dateSent;
	
	//Count of confirmed messages sent to ActiveMQ
	private Long sentCount;
	
	/**
	 * Gets the sourceIdentifier
	 *
	 * @return the sourceIdentifier
	 */
	public String getSourceIdentifier() {
		return sourceIdentifier;
	}
	
	/**
	 * Sets the sourceIdentifier
	 *
	 * @param sourceIdentifier the sourceIdentifier to set
	 */
	public void setSourceIdentifier(String sourceIdentifier) {
		this.sourceIdentifier = sourceIdentifier;
	}
	
	/**
	 * Gets the operation
	 *
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}
	
	/**
	 * Sets the operation
	 *
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	/**
	 * Gets the dateSent
	 *
	 * @return the dateSent
	 */
	public LocalDateTime getDateSent() {
		return dateSent;
	}
	
	/**
	 * Sets the dateSent
	 *
	 * @param dateSent the dateSent to set
	 */
	public void setDateSent(LocalDateTime dateSent) {
		this.dateSent = dateSent;
	}
	
	/**
	 * Gets the sentCount
	 *
	 * @return the sentCount
	 */
	public Long getSentCount() {
		return sentCount;
	}
	
	/**
	 * Sets the sentCount
	 *
	 * @param sentCount the sentCount to set
	 */
	public void setSentCount(Long sentCount) {
		this.sentCount = sentCount;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "{sourceIdentifier=" + sourceIdentifier + ", operation=" + operation
		        + ", dateSent=" + dateSent + ", sentCount=" + sentCount + "}";
	}
	
}
