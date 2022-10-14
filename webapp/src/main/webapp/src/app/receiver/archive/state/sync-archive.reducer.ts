import {createFeatureSelector, createSelector} from "@ngrx/store";
import {ReceiverSyncArchiveCountAndItems} from "../receiver-sync-archive-count-and-items";
import {SyncArchiveAction, SyncArchiveActionType} from "./sync-archive.actions";

export interface SyncArchiveState {
	countAndItems: ReceiverSyncArchiveCountAndItems;
}

const GET_SYNC_ARCHIVE_FEATURE_STATE = createFeatureSelector<SyncArchiveState>('syncArquiveQueue');

export const GET_SYNC_ARCHIVE = createSelector(
	GET_SYNC_ARCHIVE_FEATURE_STATE,
	state => state.countAndItems
);

const initialState: SyncArchiveState = {
	countAndItems: new ReceiverSyncArchiveCountAndItems()
};

export function syncArchiveReducer(state = initialState, action: SyncArchiveAction) {

	switch (action.type) {

		case SyncArchiveActionType.SYNC_ARCHIVE_LOADED:
			return {
				...state,
				countAndItems: action.countAndItems
			};

		default:
			return state;
	}

}
