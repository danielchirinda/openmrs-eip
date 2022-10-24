import {Action} from "@ngrx/store";
import { SenderSyncArchiveCountAndItems } from "../sender-sync-archive-count-and-items";

export enum SenderArchiveActionType {
	SENDER_ARCHIVED_LOADED = 'SENDER_ARCHIVED_LOADED',
	UPDATE_ARCHIVED_DATA='UPDATE_ARCHIVED_DATA'
}

export class SenderArchivedLoaded implements Action {

	readonly type = SenderArchiveActionType.SENDER_ARCHIVED_LOADED;

	constructor(public countAndItems?: SenderSyncArchiveCountAndItems) {
	}

}

export class UpdateSenderArchivedLoaded implements Action {

	readonly type = SenderArchiveActionType.UPDATE_ARCHIVED_DATA;

	constructor(public countAndItems?: SenderSyncArchiveCountAndItems) {
	}

}

export type SenderArchiveAction = SenderArchivedLoaded | UpdateSenderArchivedLoaded;
