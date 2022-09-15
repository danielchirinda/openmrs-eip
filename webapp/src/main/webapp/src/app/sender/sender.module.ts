import {NgModule} from '@angular/core';
import {SenderErrorComponent} from './error/sender-error.component';
import {SharedModule} from "../shared/shared.module";
import {StoreModule} from "@ngrx/store";
import {senderErrorReducer} from "./error/state/error.reducer";
import {SenderComponent} from './sender.component';
import {TableStatsComponent} from "./stats/table-stats.component";
import {SenderDashboardComponent} from "./dashboard/sender-dashboard.component";
import {DbEventComponent} from "./event/db-event.component";
import {dbEventReducer} from "./event/state/db-event.reducer";
import {senderSyncMessageReducer} from "./sync/state/sender-sync-message.reducer";
import {SenderSyncMessageComponent} from "./sync/sender-sync-message.component";
import { SyncDetailComponent } from './sync-detail/sync-detail.component';
import { syncDetailReducer } from './sync-detail/state/sync-detail.reducer';
import { TableSyncStatusComponent } from './sync-detail/table-sync-status/table-sync-status.component';
import { FormsModule } from '@angular/forms';
import { syncHistoryReducer } from './sync-detail/table-sync-status/state/sync-history.reducer';




@NgModule({
	declarations: [
		SenderComponent,
		SenderErrorComponent,
		SenderDashboardComponent,
		TableStatsComponent,
		DbEventComponent,
		SenderSyncMessageComponent,
		SyncDetailComponent,
		TableSyncStatusComponent
	],
	imports: [
		SharedModule,
		StoreModule.forFeature('senderErrorQueue', senderErrorReducer),
		StoreModule.forFeature('eventQueue', dbEventReducer),
		StoreModule.forFeature('syncQueue', senderSyncMessageReducer),
		StoreModule.forFeature('syncDetailQueue', syncDetailReducer ),
		StoreModule.forFeature('syncHistoryQueue', syncHistoryReducer ),
		FormsModule,

	], exports: [SenderComponent]
})

export class SenderModule {
}
