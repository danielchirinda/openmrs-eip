import {Action} from "@ngrx/store";
import { SearchEvent } from "../search-event";
import { SenderArchiveService } from "../sender-archive.service";
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

export type SenderArchiveAction = SenderArchivedLoaded;
