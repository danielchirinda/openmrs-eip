import {createFeatureSelector, createSelector} from "@ngrx/store";
import { SyncDetail } from "../sync-detail";
import { SyncDetailEventCountAndItems } from "../sync-detail-event-count-and-items";
import { SyncMessageEvent } from "../sync-message-event";
import { SyncDetailAction, SyncDetailActionType} from "./sync-detail.actions";

export interface SyncDetailState {
	countAndItems?: SyncDetailEventCountAndItems;
	syncDetailToView?: SyncDetail;
}

const GET_SYNC_DETAIL_FEATURE_STATE = createFeatureSelector<SyncDetailState>('syncDetailQueue');

export const GET_SYNC_DETAIL = createSelector(
	GET_SYNC_DETAIL_FEATURE_STATE,
	state => state.countAndItems
);

export const SYNC_DETAIL_TO_VIEW = createSelector(
	GET_SYNC_DETAIL_FEATURE_STATE,
	state => state.syncDetailToView
);

const initialState: SyncDetailState = {
	countAndItems: new SyncDetailEventCountAndItems()
};

export function syncDetailReducer(state = initialState, action: SyncDetailAction) {

	switch (action.type) {

		case SyncDetailActionType.SENDER_DETAIL_LOADED:
			return {
				...state,
				countAndItems: action.countAndItems
			};

			case SyncDetailActionType.VIEW_SENDER_DETAIL:
			return {
				...state,
				countAndItems: action.syncMesage
			};

		default:
			return state;
	}

}
