export class SyncMessageEvent {

	primaryKeyId?: any;

	identifier?: string;

	tableName?: string;

	operation?: string;

	dateCreated?:string;

	dateChanged?:string;

	dateReceived?:string;

	status?: string;

	snapshot?:boolean;

}
