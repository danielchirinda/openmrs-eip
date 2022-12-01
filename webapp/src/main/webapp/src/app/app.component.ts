import {Component, OnInit} from '@angular/core';
import {AppService} from "./app.service";
import {select, Store} from "@ngrx/store";
import {Subscription} from "rxjs";
import {GET_APP_PROPERTY} from "./state/app-property.reducer";
import {AppPropertyLoaded} from "./state/app-property.actions";

export enum SyncMode {
	SENDER = 'SENDER',
	RECEIVER = 'RECEIVER'
}

export class AppProperties {
	syncMode?: SyncMode;
}

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

	constructor(private appService: AppService,
				private store: Store) {
	}

	syncMode?: SyncMode;
	loadedSubscription?: Subscription;
	appProperties?: AppProperties;


	ngOnInit(): void {

		this.loadedSubscription = this.store.pipe(select(GET_APP_PROPERTY)).subscribe(
			appProperties => {
				this.appProperties = appProperties
				this.syncMode = appProperties.syncMode
			}
		);

		this.loadAppProperties();
	}

	loadAppProperties(){
		//TODO use nrgx by dispatching an action
		this.appService.getAppProperties().subscribe(appProperties =>
			this.store.dispatch(new AppPropertyLoaded(appProperties))
		)
	}

}
