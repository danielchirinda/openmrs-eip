import {NgModule} from '@angular/core';
import {ReceiverErrorComponent} from './error/receiver-error.component';
import {ConflictComponent} from './conflict/conflict.component';
import {SharedModule} from "../shared/shared.module";
import {StoreModule} from "@ngrx/store";
import {conflictReducer} from "./conflict/state/conflict.reducer";
import {receiverErrorReducer} from "./error/state/error.reducer";
import {ReceiverComponent} from './receiver.component';
import {SiteStatusComponent} from "./status/site-status.component";
import {siteStatusReducer} from "./status/state/site-status.reducer";
import {ReceiverSyncMessageComponent} from './sync/receiver-sync-message.component';
import {syncMessageReducer} from "./sync/state/sync-message.reducer";

@NgModule({
	declarations: [
		ReceiverComponent,
		ReceiverErrorComponent,
		ConflictComponent,
		SiteStatusComponent,
		ReceiverSyncMessageComponent
	],
	imports: [
		SharedModule,
		StoreModule.forFeature('conflictQueue', conflictReducer),
		StoreModule.forFeature('receiverErrorQueue', receiverErrorReducer),
		StoreModule.forFeature('siteStatuses', siteStatusReducer),
		StoreModule.forFeature('syncMsgQueue', syncMessageReducer)
	], exports: [ReceiverComponent]
})

export class ReceiverModule {
}
