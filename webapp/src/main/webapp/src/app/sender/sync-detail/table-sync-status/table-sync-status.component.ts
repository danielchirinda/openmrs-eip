import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { BaseListingComponent } from 'src/app/shared/base-listing.component';
import { SyncMessageStatus } from '../status/sync-message-status';
import { SyncDetailService } from '../sync-detail.service';

@Component({
	selector: 'app-table-status',
	templateUrl: './table-sync-status.component.html',
	styleUrls: ['./table-sync-status.component.scss']
})
export class TableSyncStatusComponent extends BaseListingComponent implements OnInit {

	isSyncMessageViewOverall = false;
	count?: number;

	filteredSyncStatus?: SyncMessageStatus[];

	constructor(private service: SyncDetailService,
		private store: Store) {
		super();
	}

	async ngOnInit() {
		await this.openOverall();
	}

	openOverall() {

		this.service.getSyncStatusDetails().subscribe(countAndItems => {
			console.log('loaded itens from database', countAndItems);
			this.filteredSyncStatus = countAndItems.items;
		});

	}
}
