import {Component, OnInit} from "@angular/core";
import {DashboardService} from "./dashboard.service";
import {Dashboard} from "./dashboard";
import {Subscription, timer} from 'rxjs';
import {select, Store} from "@ngrx/store";
import {GET_APP_PROPERTY} from "../state/app-property.reducer";
import {AppProperties, SyncMode} from "../app.component";

@Component({template: ''})
export abstract class DashboardComponent implements OnInit {

	dashboard?: Dashboard;

	reloadTimer?: Subscription;

	syncMode?: SyncMode;

	loadedSubscription?: Subscription;

	appProperties?: AppProperties;

	constructor(private service: DashboardService,private store: Store) {
	}

	ngOnInit(): void {
		this.reloadTimer = timer(100, 30000).subscribe(() => {
			this.loadDashboard();
		});

		this.loadedSubscription = this.store.pipe(select(GET_APP_PROPERTY)).subscribe(
			appProperties => {
				this.appProperties = appProperties
				this.syncMode = appProperties.syncMode
			}
		);
	}

	loadDashboard(): void {
		if (this.syncMode==SyncMode.SENDER ){
			this.service.getDashboard().subscribe(dashboard => {
				this.dashboard = dashboard;
			});
		}
	}

	ngOnDestroy(): void {
		this.reloadTimer?.unsubscribe();
	}

}
