import {ErrorHandler, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BrowserModule} from "@angular/platform-browser";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {DataTablesModule} from "angular-datatables";
import {ConfirmDialogComponent} from "./dialogs/confirm.component";
import {HttpErrorInterceptor} from "./http-error.interceptor";
import {ModelClassPipe} from "./pipes/model-class.pipe";
import {GlobalErrorHandler} from "./global-error.handler";
import {ClassNamePipe} from "./pipes/class-name.pipe";


@NgModule({
	declarations: [ConfirmDialogComponent, ClassNamePipe, ModelClassPipe],
	exports: [
		CommonModule,
		BrowserModule,
		HttpClientModule,
		NgbModule,
		DataTablesModule,
		ClassNamePipe,
		ModelClassPipe
	],
	providers: [
		{
			provide: HTTP_INTERCEPTORS,
			useClass: HttpErrorInterceptor,
			multi: true
		},
		{
			provide: ErrorHandler,
			useClass: GlobalErrorHandler
		}
	]
})

export class SharedModule {
}
