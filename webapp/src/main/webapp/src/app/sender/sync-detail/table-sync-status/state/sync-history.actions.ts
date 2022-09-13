import {Action} from "@ngrx/store";
import { SyncDetailCountAndItems } from "../../status/sync-detail-count-and-item";

export enum SyncHistoryActionType {
	SENDER_HISTORY_LOADED = 'SENDER_HISTORY_LOADED',
}

export class SyncHistoryLoaded implements Action {

	readonly type = SyncHistoryActionType.SENDER_HISTORY_LOADED;

	constructor(public countAndItems?: SyncDetailCountAndItems) {
	}

}

export type SyncHistoryAction = SyncHistoryLoaded;
