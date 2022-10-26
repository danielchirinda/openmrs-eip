import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { select, Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { BaseListingComponent } from 'src/app/shared/base-listing.component';
import { ReceiverSyncArchive } from './receiver-sync-archive';
import { ReceiverSyncArchiveService } from './receiver-sync-archive.service';
import { SyncArchiveLoaded } from './state/sync-archive.actions';
import { GET_SYNC_ARCHIVE } from './state/sync-archive.reducer';

@Component({
  selector: 'app-archive',
  templateUrl: './archive.component.html',
})
export class ArchiveComponent extends BaseListingComponent implements OnInit {


	count?: number;

	events?: ReceiverSyncArchive[];

	loadedSubscription?: Subscription;

	constructor(
		private service: ReceiverSyncArchiveService,
		private store: Store,
	) {
		super();
	}

	ngOnInit(): void {

		this.init();
		this.loadedSubscription = this.store.pipe(select(GET_SYNC_ARCHIVE)).subscribe(
			countAndItems => {
				this.count = countAndItems?.count;
				this.events = countAndItems?.items;
				this.reRender();
			}
		);

		this.loadDetails();

	}

	loadDetails(): void {
		this.service.getSyncArchiveCountAndItems().subscribe(countAndItems => {
			this.store.dispatch(new SyncArchiveLoaded(countAndItems));
		});
	}

	ngOnDestroy(): void {
		this.loadedSubscription?.unsubscribe();
		super.ngOnDestroy();
	}

}
