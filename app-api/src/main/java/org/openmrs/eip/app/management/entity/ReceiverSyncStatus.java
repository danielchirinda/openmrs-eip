package org.openmrs.eip.app.management.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "receiver_sync_status")
public class ReceiverSyncStatus extends AbstractEntity {	
	public static final long serialVersionUID = 1;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "site_info_id", referencedColumnName = "id")
	private SiteInfo siteInfo;
	
	@Column(name = "last_sync_date", nullable = false)
	private Date lastSyncDate;
	
	public ReceiverSyncStatus() {
		
	}
	
	public ReceiverSyncStatus(SiteInfo siteInfo, Date lastSyncDate) {
		this.siteInfo= siteInfo;
		this.lastSyncDate = lastSyncDate;
	}
	
	public SiteInfo getSiteInfo() {
		return siteInfo;
	}
	
	public void setSiteInfo(SiteInfo siteInfo) {
		this.siteInfo = siteInfo;
	}
	
	/**
	 * Gets the lastSyncDate
	 *
	 * @return the lastSyncDate
	 */
	public Date getLastSyncDate() {
		return lastSyncDate;
	}
	
	/**
	 * Sets the lastSyncDate
	 *
	 * @param lastSyncDate the lastSyncDate to set
	 */
	public void setLastSyncDate(Date lastSyncDate) {
		this.lastSyncDate = lastSyncDate;
	}
	
	public String toString() {
		return "ReceiverSyncStatus {siteInfoId=" + this.siteInfo.getId() + ", lastSyncDate=" + lastSyncDate + "}";
	}
}
