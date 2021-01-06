package org.openmrs.eip.component.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationshipModel extends BaseChangeableDataModel {
    private String personaUuid;
    private int relationshipTypeId;
	private String personbUuid;
    protected LocalDateTime startDate;
    protected LocalDateTime endDate;
    
	public int getRelationshipTypeId() {
		return relationshipTypeId;
	}
	public void setRelationshipTypeId(int relationshipTypeId) {
		this.relationshipTypeId = relationshipTypeId;
	}
	
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public String getPersonaUuid() {
		return personaUuid;
	}
	public void setPersonaUuid(String personaUuid) {
		this.personaUuid = personaUuid;
	}
	public String getPersonbUuid() {
		return personbUuid;
	}
	public void setPersonbUuid(String personbUuid) {
		this.personbUuid = personbUuid;
	}
}
