
export class SyncMessageEvent {

	checked?=true;

	primaryKeyId?: any;

	messageUuid?: string;

	identifier?: string;

	tableName?: string;

	operation?: string;

	dateCreated?:string;

	dateChanged?:string;

	dateReceived?:string;

	status?: string;

	snapshot?:boolean;

	id?: bigint;

}
