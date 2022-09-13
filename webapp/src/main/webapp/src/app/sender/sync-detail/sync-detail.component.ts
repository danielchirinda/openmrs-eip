import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbModal, NgbModalOptions, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { select, Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { BaseListingComponent } from 'src/app/shared/base-listing.component';
import { NgbdModalConfirm } from './confirm-modal';
import { SyncDetailLoaded } from './state/sync-detail.actions';
import { GET_SYNC_DETAIL } from './state/sync-detail.reducer';
import { SyncMessageStatus } from './status/sync-message-status';
import { SyncDetail } from './sync-detail';
import { SyncDetailService } from './sync-detail.service';
import { SyncMessageEvent } from './sync-message-event';
import { SearchEvent } from './search-event';
import { ResendMessage } from './resend-message-id';
import { Location } from '@angular/common';



@Component({
	selector: 'sync-detail',
	templateUrl: './sync-detail.component.html'
})
export class SyncDetailComponent extends BaseListingComponent implements OnInit {

	@Input() tableName : any

	@Input() startDate : any

	@Input() endDate : any

	count?: number;

	events?: SyncMessageEvent[];

	loadedSubscription?: Subscription;

	modalRef?: NgbModalRef;

	syncDetailToView?: SyncDetail;

	filteredSyncStatus?: SyncMessageStatus[];

	isSyncMessageViewOverall = false;

	isSyncMessageView = true;

	syncStatus?: SyncMessageStatus[];

	masterSelected?: boolean = false

	selectedEvents = new Map();

	resendMessage: ResendMessage = new ResendMessage;

	searchEvent: SearchEvent = new SearchEvent;

	constructor(
		private service: SyncDetailService,
		private store: Store,
		private modalService: NgbModal,
	) {
		super();
	}

	ngOnInit(): void {
		console.log('this is event emitted', this.tableName)
		console.log('search data', this.startDate)
		console.log('search data', this.endDate)
		this.searchEvent.startDate=this.startDate
		this.searchEvent.endDate=this.endDate
		this.searchEvent.tableName=this.tableName
		this.masterSelected = false

		this.dtOptions = {
			pagingType: 'full_numbers',
			deferLoading: 12,
			searching: true,
		};
		this.loadedSubscription = this.store.pipe(select(GET_SYNC_DETAIL)).subscribe(
			countAndItems => {
				this.count = countAndItems?.count;
				this.events = countAndItems?.items;

				this.reRender();
			}
		);

		this.loadDetails();
	}

	loadDetails(): void {
		this.service.getSyncEventByDate(this.searchEvent).subscribe(countAndItems => {
			this.store.dispatch(new SyncDetailLoaded(countAndItems));
		});
	}

	ngOnDestroy(): void {
		this.loadedSubscription?.unsubscribe();
		super.ngOnDestroy();
	}

	// custom method's

	closeDetailsDialog(): void {
		this.modalRef?.close();
	}

	open(syncMessage: SyncMessageEvent) {
		console.log('SYNC MESSAGE SELECTED', syncMessage)

		const dialogConfig: NgbModalOptions = {
			size: 'xl',
			scrollable: true
		}
		this.modalRef = this.modalService.open(NgbdModalConfirm, dialogConfig);
		this.modalRef.componentInstance.syncMessageEvent = syncMessage;

	}

	checkUncheckAll(event: any) {
		const isChecked = event.target.checked
		let newEvent: SyncMessageEvent[] = [];

		this.events?.forEach(currentEvent => {
			newEvent.push({
				dateChanged: currentEvent.dateChanged,
				dateCreated: currentEvent.dateCreated,
				dateReceived: currentEvent.dateReceived,
				checked: isChecked,
				identifier: currentEvent.identifier,
				messageUuid: currentEvent.messageUuid,
				operation: currentEvent.operation,
				primaryKeyId: currentEvent.primaryKeyId,
				snapshot: currentEvent.snapshot,
				status: currentEvent.status,
				tableName: currentEvent.tableName,
				id: currentEvent.id

			})
			if (isChecked) {
				this.selectedEvents.set(currentEvent.messageUuid, currentEvent);
			} else {
				this.selectedEvents.delete(currentEvent.messageUuid)
			}
		})

		this.masterSelected = true;
		this.events = newEvent;
		this.reRender()

	}

	isSelected(syncMessage: SyncMessageEvent ,event: any) {
		const isChecked = event.target.checked
		this.masterSelected = false

		if (isChecked) {
			this.selectedEvents.set(syncMessage.messageUuid, syncMessage);
		} else {
			this.selectedEvents.delete(syncMessage.messageUuid);
		}
	}

	 resendSelectedEvent() {
		this.selectedEvents.forEach((value,key) =>{
			this.resendMessage.syncMessages?.push(value.id);
		})

		  this.service.resendMultipleEvent(this.resendMessage).subscribe((message) =>{
		},
		error => {
			console.log('Error message', error)
		}
		);
		this.loadDetails();
	}
	back(){
		window.location.reload();
	}
}
