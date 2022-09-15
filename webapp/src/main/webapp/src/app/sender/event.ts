export class Event {

	identifier?: string;

	primaryKeyId?: string;

	tableName?: string;

	operation?: string;

	snapshot?: boolean;


	//New Attr for test
	dateCreated?:string;

	dateSend?:string;

	dateReceived?:string;

	syncStatus?: string;

}
