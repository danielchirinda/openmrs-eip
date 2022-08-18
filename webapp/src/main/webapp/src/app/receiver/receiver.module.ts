import {NgModule} from '@angular/core';
import {ReceiverErrorComponent} from './error/receiver-error.component';
import {ConflictComponent} from './conflict/conflict.component';
import {SharedModule} from "../shared/shared.module";
import {StoreModule} from "@ngrx/store";
import {conflictReducer} from "./conflict/state/conflict.reducer";
import {receiverErrorReducer} from "./error/state/error.reducer";
import {ReceiverComponent} from './receiver.component';
import { ReceiverDetailComponent } from './receiver-detail/receiver-detail.component';
import { receiverDetailReducer } from './receiver-detail/state/receiver-detail.reducer';

@NgModule({
	declarations: [
		ReceiverComponent,
		ReceiverErrorComponent,
		ConflictComponent,
		ReceiverDetailComponent
	],
	imports: [
		SharedModule,
		StoreModule.forFeature('conflictQueue', conflictReducer),
		StoreModule.forFeature('receiverErrorQueue', receiverErrorReducer),
		StoreModule.forFeature('receiverDetailQueue', receiverDetailReducer),
	], exports: [ReceiverComponent]
})

export class ReceiverModule {
}
