import {createFeatureSelector, createSelector} from "@ngrx/store";
import {ReceiverSyncMessageCountAndItems} from "../receiver-sync-message-count-and-items";
import {ReceiverSyncMessage} from "../receiver-sync-message";
import {SyncMessageAction, SyncMessageActionType} from "./sync-message.actions";

export interface SyncMessageState {
	totalCount?: number;
	countAndItems: ReceiverSyncMessageCountAndItems;
	msgToView?: ReceiverSyncMessage;
}

const GET_MSG_FEATURE_STATE = createFeatureSelector<SyncMessageState>('syncMsgQueue');

export const GET_MSGS = createSelector(
	GET_MSG_FEATURE_STATE,
	state => state.countAndItems
);

export const MSG_TO_VIEW = createSelector(
	GET_MSG_FEATURE_STATE,
	state => state.msgToView
);

export const GET_TOTAL_COUNT = createSelector(
	GET_MSG_FEATURE_STATE,
	state => state.totalCount
);

const initialState: SyncMessageState = {
	countAndItems: new ReceiverSyncMessageCountAndItems()
};

export function syncMessageReducer(state = initialState, action: SyncMessageAction) {

	switch (action.type) {

		case SyncMessageActionType.MSGS_LOADED:
			return {
				...state,
				totalCount: action.countAndItems?.count,
				countAndItems: action.countAndItems
			};

		case SyncMessageActionType.VIEW_MSG:
			return {
				...state,
				msgToView: action.message
			};

		default:
			return state;
	}

}
