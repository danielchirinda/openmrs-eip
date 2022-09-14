package org.openmrs.eip.app.management.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "receiver_sync_history")
public class ReceiverSyncHistory extends AbstractEntity {

	public static final long serialVersionUID = 1;

	@Column(nullable = false, updatable = false)
	private String identifier;

	@Column(name = "table_name", nullable = false)
	private String tableName;

	@Column(name = "operation", nullable = false, updatable = false)
	private String operation;

	@Column(name = "district", nullable = false, updatable = false)
	private String district;

	@Column(name = "health_facility", nullable = false, updatable = false)
	private String healthFacility;

	@Column(name = "health_facility_code", nullable = false, updatable = false)
	private String healthFacilityCode;

	@Column(name = "message_uuid", length = 38, updatable = false)
	private String messageUuid;

	@Column(name = "status", length = 38, updatable = false)
	private String status;

	@Column(name = "date_sent", updatable = false)
	private Date dateSent;

	@Column(name = "date_received", updatable = false)
	private Date dateReceived;

	/**
	 * Gets the identifier
	 *
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Sets the identifier
	 *
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getMessageUuid() {
		return messageUuid;
	}

	public void setMessageUuid(String messageUuid) {
		this.messageUuid = messageUuid;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getHealthFacility() {
		return healthFacility;
	}

	public void setHealthFacility(String healthFacility) {
		this.healthFacility = healthFacility;
	}

	public String getHealthFacilityCode() {
		return healthFacilityCode;
	}

	public void setHealthFacilityCode(String healthFacilityCode) {
		this.healthFacilityCode = healthFacilityCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	@Override
	public String toString() {
		return "ReceiverSyncHistory [identifier=" + identifier + ", tableName=" + tableName + ", operation=" + operation
				+ ", district=" + district + ", healthFacility=" + healthFacility + ", healthFacilityCode="
				+ healthFacilityCode + ", messageUuid=" + messageUuid + ", status=" + status + ", dateSent=" + dateSent
				+ ", dateReceived=" + dateReceived + "]";
	}
	
	

}
