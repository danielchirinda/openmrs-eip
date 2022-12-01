import {Action} from "@ngrx/store";
import {AppProperties} from "../app.component";

export enum AppPropertyActionType {
	APP_PROPERTIES_LOADED = 'APP_PROPERTIES_LOADED'
}

export class AppPropertyLoaded implements Action {

	readonly type = AppPropertyActionType.APP_PROPERTIES_LOADED;

	constructor(public appProperties?: AppProperties) {
	}

}

export type AppPropertyAction = AppPropertyLoaded;
