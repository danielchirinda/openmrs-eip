import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {SharedModule} from "./shared/shared.module";
import {ReceiverModule} from "./receiver/receiver.module";
import {StoreModule} from '@ngrx/store';
import {StoreDevtoolsModule} from '@ngrx/store-devtools';
import {environment} from '../environments/environment';
import {SenderModule} from "./sender/sender.module";
import { appPropertyReducer } from './state/app-property.reducer';

@NgModule({
	declarations: [
		AppComponent
	],
	imports: [
		SharedModule,
		ReceiverModule,
		SenderModule,
		StoreModule.forRoot({}, {}),
		StoreModule.forFeature('appPropertiesQueue', appPropertyReducer),
		StoreDevtoolsModule.instrument({
			maxAge: 25,
			logOnly: environment.production
		})
	],
	providers: [],
	bootstrap: [AppComponent]
})

export class AppModule {
}
