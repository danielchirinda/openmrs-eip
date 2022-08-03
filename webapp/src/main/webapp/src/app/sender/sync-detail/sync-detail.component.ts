import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalOptions, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { select, Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { BaseListingComponent } from 'src/app/shared/base-listing.component';
import { NgbdModalConfirm } from './confirm-modal';
import { SyncDetailLoaded } from './state/sync-detail.actions';
import { GET_SYNC_DETAIL } from './state/sync-detail.reducer';
import { SyncDetailStatus } from './status/sync-detail-status';
import { SyncMessageStatus } from './status/sync-message-status';
import { SyncDetail } from './sync-detail';
import { SyncDetailService } from './sync-detail.service';
import { SyncMessageEvent } from './sync-message-event';


@Component({
	selector: 'sync-detail',
	templateUrl: './sync-detail.component.html'
})
export class SyncDetailComponent extends BaseListingComponent implements OnInit {

	count?: number;

	events?: SyncMessageEvent[];

	loadedSubscription?: Subscription;

	modalRef?: NgbModalRef;

	syncDetailToView?: SyncDetail;

	filteredSyncStatus?: SyncMessageStatus[];

	isSyncMessageViewOverall = false;

	isSyncMessageView = true;

	syncStatus?: SyncMessageStatus[];


	constructor(
		private service: SyncDetailService,
		private store: Store,
		private modalService: NgbModal
	) {
		super();
	}

	ngOnInit(): void {
		this.init();
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
		this.service.getEventCountAndItems().subscribe(countAndItems => {
			console.log('loaded itens from database', countAndItems)
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

	openOverall() {

		this.service.getSyncStatusDetails().subscribe(countAndItems => {
			console.log('loaded itens from database', countAndItems);
			this.filteredSyncStatus = countAndItems.items;

			console.log('TIPO DE DADO', typeof countAndItems);

			//this.sortData(countAndItems);



			// this.store.dispatch(new SyncDetailLoaded(countAndItems));
		});


		this.isSyncMessageView = false
		this.isSyncMessageViewOverall = true
	}


	sortData(countAndItems: any) {

		// Position 0 = count
		// position 1 = status
		// position 2 = tableName
		countAndItems.forEach((statusObject: any) => {

			/*
			let existentStatus = this.syncStatus?.find((status , index?) => {
				// Search by table Name
				if(status?.tableName===statusObject[2]){
					status.position = index
					return status
				}
			})

			if(existentStatus) {

				if (statusObject[1]==='SENT') {
					existentStatus.setItens = existentStatus.setItens + statusObject[0]

				}
				else if(statusObject[1]==='RECEIVED'){
					existentStatus.receivedItens = existentStatus.receivedItens + statusObject[0]

				}else {
					existentStatus.nonReceivedItens = existentStatus.nonReceivedItens + statusObject[0]
				}
				//update value on syncStatus array
				this.syncStatus?.find((statusToUpdate: SyncMessageStatus, index) => {
					// Search by table Name
					if(statusToUpdate.tableName===existentStatus?.tableName){
						statusToUpdate.setItens = existentStatus?.setItens
						statusToUpdate.nonReceivedItens = existentStatus?.nonReceivedItens
						statusToUpdate.receivedItens = existentStatus?.receivedItens
						return
					}
				})

			}else{
				// existentStatus?.nonReceivedItens = 0





			}
*/
		});
	}
}
