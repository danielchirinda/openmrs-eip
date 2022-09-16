import { Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { BaseListingComponent } from 'src/app/shared/base-listing.component';
import { SearchEvent } from '../search-event';
import { SyncMessageStatus } from '../status/sync-message-status';
import { SyncDetailService } from '../sync-detail.service';
import { SyncHistoryLoaded } from './state/sync-history.actions';
import { GET_SYNC_HISTORY } from './state/sync-history.reducer';

@Component({
	selector: 'app-table-status',
	templateUrl: './table-sync-status.component.html',
	styleUrls: ['./table-sync-status.component.scss']
})
export class TableSyncStatusComponent extends BaseListingComponent implements OnInit {

	isSyncMessageViewOverall = false;

	count?: number;

	loadedSubscription?: Subscription;

	filteredSyncStatus?: SyncMessageStatus[];

	selectEventHistory?: SyncMessageStatus;

	openSyncDetails: boolean = false

	searchEvent: SearchEvent = new SearchEvent;

	constructor(private service: SyncDetailService,
		private store: Store) {
		super();
	}

	ngOnInit() {
		this.init();

		this.loadedSubscription = this.store.pipe(select(GET_SYNC_HISTORY)).subscribe(
			countAndItems => {
				this.count = countAndItems?.count;
				this.filteredSyncStatus = countAndItems?.items
				this.reRender();
			}
		);

		this.dtOptions = {
			pagingType: 'full_numbers',
			deferLoading: 12,
			searching: true,
			processing: true,
			// on onclick Method
			rowCallback: (row: Node, data: any[] | Object, index: number) => {
				const self = this;
				$('td', row).off('click');
				$('td', row).on('click', () => {
					self.eventClickHandler(data);
				});
				return row;
			}
		};

		this.openOverall();

		this.store.select(GET_SYNC_HISTORY).subscribe((value) => {
			this.filteredSyncStatus = value?.items
		})

	}

	eventClickHandler(event: any): void {
		this.selectEventHistory = event
		this.openSyncDetails = true

	}

	openOverall() {

		this.service.getSyncStatusDetails().subscribe(countAndItems => {
			this.store.dispatch(new SyncHistoryLoaded(countAndItems));
		});

	}

	searchByPeriod(event: Event) {

		this.service.getSyncHistoryByDate(this.searchEvent).subscribe((countAndItems) => {
			this.filteredSyncStatus = []
			this.filteredSyncStatus = countAndItems.items;
			this.count = countAndItems.count;
			this.reRender();
		});


	}

	someTotalEvent(nonReceivedItens: any, sentItens: any, receivedItens: any) {
		let unReceived = nonReceivedItens ? parseFloat(nonReceivedItens) : 0
		let sent = sentItens ? parseFloat(sentItens) : 0
		let received = receivedItens ? parseFloat(receivedItens) : 0

		return unReceived + sent + received

	}

	ngOnDestroy(): void {
		this.loadedSubscription?.unsubscribe();
		super.ngOnDestroy();
	}
}
