
export class SyncMessageEvent {

	checked?=true;

	primaryKeyId?: any;

	messageUuid?: string;

	identifier?: string;

	tableName?: string;

	operation?: string;

	dateCreated?:string;

	dateSent?:string;

	dateReceived?:string;

	status?: string;

	snapshot?:boolean;

	id?: bigint;

}
