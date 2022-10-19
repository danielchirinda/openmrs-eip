import {BaseEntity} from "../../shared/base-entity";

export class SenderSyncArchive extends BaseEntity {

	tableName?: string;

	identifier?: string;

	operation?: string;

	messageUuid?: string;

	requestUuid?: string;

	status?: string;

	dateSent?: string;

	eventDate?: string;


}
