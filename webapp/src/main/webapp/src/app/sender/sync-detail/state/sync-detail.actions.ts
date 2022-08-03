import {Action} from "@ngrx/store";
import { SyncDetailEventCountAndItems } from "../sync-detail-event-count-and-items";
import { SyncMessageEvent } from "../sync-message-event";

export enum SyncDetailActionType {
	SENDER_DETAIL_LOADED = 'SENDER_DETAIL_LOADED',
	VIEW_SENDER_DETAIL = 'VIEW_SENDER_DETAIL'
}

export class SyncDetailLoaded implements Action {

	readonly type = SyncDetailActionType.SENDER_DETAIL_LOADED;

	constructor(public countAndItems?: SyncDetailEventCountAndItems) {
	}

}

export class ViewSyncMessage implements Action {

	readonly type = SyncDetailActionType.VIEW_SENDER_DETAIL;

	constructor(public syncMesage?: SyncMessageEvent) {
	}

}

export type SyncDetailAction = SyncDetailLoaded | ViewSyncMessage;
