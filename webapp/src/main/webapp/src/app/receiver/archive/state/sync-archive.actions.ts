import {Action} from "@ngrx/store";
import { ReceiverSyncArchiveCountAndItems } from "../receiver-sync-archive-count-and-items";

export enum SyncArchiveActionType {
	SYNC_ARCHIVE_LOADED = 'SYNC_ARCHIVE_LOADED',
}

export class SyncArchiveLoaded implements Action {

	readonly type = SyncArchiveActionType.SYNC_ARCHIVE_LOADED;

	constructor(public countAndItems?: ReceiverSyncArchiveCountAndItems) {
	}

}

export type SyncArchiveAction = SyncArchiveLoaded;
