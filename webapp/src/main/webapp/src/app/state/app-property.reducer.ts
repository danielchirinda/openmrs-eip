import {createFeatureSelector, createSelector} from "@ngrx/store";
import {
	AppPropertyAction,
	AppPropertyActionType,
} from "./app-property.actions";
import {AppProperties, SyncMode} from "../app.component";

export interface AppPropertyState {
	appProperties: AppProperties;
}

const GET_APP_PROPERTY_STATE = createFeatureSelector<AppPropertyState>('appPropertiesQueue');

export const GET_APP_PROPERTY = createSelector(
	GET_APP_PROPERTY_STATE,
	state => state.appProperties
);

const initialState: AppPropertyState = {
	appProperties: new AppProperties()
};

export function appPropertyReducer(state = initialState, action: AppPropertyAction) {

	switch (action.type) {

		case AppPropertyActionType.APP_PROPERTIES_LOADED:
			return {
				...state,
				appProperties: action.appProperties
			};

		default:
			return state;
	}

}
