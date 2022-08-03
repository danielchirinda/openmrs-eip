package org.openmrs.eip.web.dto;


public class SyncStatusDetailDTO {

	public static final String RECEIVED = "RECEIVED";
	
	public static final String SENT = "SENT";
	
	public static final String NEW = "NEW";
	
	private String tableName;

	private int setItens;

	private int receivedItens;

	private int nonReceivedItens;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getSetItens() {
		return setItens;
	}

	public void setSetItens(int setItens) {
		this.setItens = setItens;
	}

	public int getReceivedItens() {
		return receivedItens;
	}

	public void setReceivedItens(int receivedItens) {
		this.receivedItens = receivedItens;
	}

	public int getNonReceivedItens() {
		return nonReceivedItens;
	}

	public void setNonReceivedItens(int nonReceivedItens) {
		this.nonReceivedItens = nonReceivedItens;
	}

}
