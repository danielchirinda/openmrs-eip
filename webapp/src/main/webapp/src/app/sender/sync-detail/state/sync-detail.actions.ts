import {Action} from "@ngrx/store";
import { SyncDetailEventCountAndItems } from "../sync-detail-event-count-and-items";

export enum SyncDetailActionType {
	SENDER_DETAIL_LOADED = 'SENDER_DETAIL_LOADED'
}

export class SyncDetailLoaded implements Action {

	readonly type = SyncDetailActionType.SENDER_DETAIL_LOADED;

	constructor(public countAndItems?: SyncDetailEventCountAndItems) {
	}

}

export type SyncDetailAction = SyncDetailLoaded;
