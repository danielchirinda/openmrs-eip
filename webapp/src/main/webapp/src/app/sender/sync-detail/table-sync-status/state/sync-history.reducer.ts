import {createFeatureSelector, createSelector} from "@ngrx/store";
import { SyncDetailCountAndItems } from "../../status/sync-detail-count-and-item";
import {SyncHistoryAction, SyncHistoryActionType} from "./sync-history.actions";

export interface SyncHistoryState {
	countAndItems?: SyncDetailCountAndItems;
}

const GET_SYNC_HISTORY_FEATURE_STATE = createFeatureSelector<SyncHistoryState>('syncHistoryQueue');

export const GET_SYNC_HISTORY = createSelector(
	GET_SYNC_HISTORY_FEATURE_STATE,
	state => state.countAndItems
);


const initialState: SyncHistoryState = {
	countAndItems: new SyncDetailCountAndItems()
};

export function syncHistoryReducer(state = initialState, action: SyncHistoryAction) {

	switch (action.type) {

		case SyncHistoryActionType.SENDER_HISTORY_LOADED:
			return {
				...state,
				countAndItems: action.countAndItems
			};

		default:
			return state;
	}

}
