import { Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { BaseListingComponent } from 'src/app/shared/base-listing.component';
import { SyncDetailLoaded } from './state/sync-detail.actions';
import { GET_SYNC_DETAIL } from './state/sync-detail.reducer';
import { SyncDetail } from './sync-detail';
import { SyncDetailService } from './sync-detail.service';

@Component({
  selector: 'sync-detail',
  templateUrl: './sync-detail.component.html'
})
export class SyncDetailComponent extends BaseListingComponent implements OnInit {

	count?: number;

	details?: SyncDetail[];

	loadedSubscription?: Subscription;

	constructor(
		private service: SyncDetailService,
		private store: Store
	) {
		super();
	}

	ngOnInit(): void {
		this.init();
		this.loadedSubscription = this.store.pipe(select(GET_SYNC_DETAIL)).subscribe(
			countAndItems => {
				this.count = countAndItems.count;
				this.details = countAndItems.items;
				this.reRender();
			}
		);

		this.loadDetails();
	}

	loadDetails(): void {
		this.service.getEventCountAndItems().subscribe(countAndItems => {
			this.store.dispatch(new SyncDetailLoaded(countAndItems));
		});
	}

	ngOnDestroy(): void {
		this.loadedSubscription?.unsubscribe();
		super.ngOnDestroy();
	}
}
