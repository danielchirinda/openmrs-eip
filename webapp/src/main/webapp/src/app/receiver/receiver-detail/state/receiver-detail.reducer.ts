import {createFeatureSelector, createSelector} from "@ngrx/store";
import { ReceiverDetail } from "../receiver-detail";
import { ReceiverDetailCountAndItems } from "../receiver-detail-count-and-items";
import { ReceiverDetailAction, ReceiverDetailActionType} from "./receiver-detail.actions";

export interface ReceiverDetailState {
	countAndItems?: ReceiverDetailCountAndItems;
	receiverDetailToView?: ReceiverDetail;
}

const GET_RECEIVER_DETAIL_FEATURE_STATE = createFeatureSelector<ReceiverDetailState>('receiverDetailQueue');

export const GET_RECEIVER_DETAIL = createSelector(
	GET_RECEIVER_DETAIL_FEATURE_STATE,
	state => state.countAndItems
);

export const RECEIVER_DETAIL_TO_VIEW = createSelector(
	GET_RECEIVER_DETAIL_FEATURE_STATE,
	state => state.receiverDetailToView
);

const initialState: ReceiverDetailState = {
	countAndItems: new ReceiverDetailCountAndItems()
};

export function receiverDetailReducer(state = initialState, action: ReceiverDetailAction) {

	switch (action.type) {

		case ReceiverDetailActionType.RECEIVER_DETAIL_LOADED:
			return {
				...state,
				countAndItems: action.countAndItems
			};

			case ReceiverDetailActionType.VIEW_RECEIVER_DETAIL:
			return {
				...state,
				countAndItems: action.receiverMesage
			};

		default:
			return state;
	}

}
