import {NgModule} from '@angular/core';
import {SenderErrorComponent} from './error/sender-error.component';
import {SharedModule} from "../shared/shared.module";
import {StoreModule} from "@ngrx/store";
import {senderErrorReducer} from "./error/state/error.reducer";
import {SenderComponent} from './sender.component';
import {SenderDashboardComponent} from "./dashboard/sender-dashboard.component";
import {EbEventComponent} from "./event/db-event.component";
import {dbEventReducer} from "./event/state/db-event.reducer";
import { SyncDetailComponent } from './sync-detail/sync-detail.component';
import { syncDetailReducer } from './sync-detail/state/sync-detail.reducer';
import { TableSyncStatusComponent } from './sync-detail/table-sync-status/table-sync-status.component';
import { TableStatsComponent } from './stats/table-stats.component';


@NgModule({
	declarations: [
		SenderComponent,
		SenderErrorComponent,
		SenderDashboardComponent,
		TableStatsComponent,
		EbEventComponent,
		SyncDetailComponent,
		TableSyncStatusComponent
	],
	imports: [
		SharedModule,
		StoreModule.forFeature('senderErrorQueue', senderErrorReducer),
		StoreModule.forFeature('eventQueue', dbEventReducer),
		StoreModule.forFeature('syncDetailQueue', syncDetailReducer )
	], exports: [SenderComponent]
})

export class SenderModule {
}
