import {createFeatureSelector, createSelector} from "@ngrx/store";
import { SyncDetailEventCountAndItems } from "../sync-detail-event-count-and-items";
import { SyncDetailAction, SyncDetailActionType} from "./sync-detail.actions";

export interface SyncDetailState {
	countAndItems: SyncDetailEventCountAndItems;
}

const GET_SYNC_DETAIL_FEATURE_STATE = createFeatureSelector<SyncDetailState>('syncDetailQueue');

export const GET_SYNC_DETAIL = createSelector(
	GET_SYNC_DETAIL_FEATURE_STATE,
	state => state.countAndItems
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

		default:
			return state;
	}

}
