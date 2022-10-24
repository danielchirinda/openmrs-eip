import { Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { BaseListingComponent } from 'src/app/shared/base-listing.component';
import { SearchEvent } from './search-event';
import { SenderSyncArchive } from './sender-archive';
import { SenderArchiveService } from './sender-archive.service';
import { SenderArchivedLoaded, UpdateSenderArchivedLoaded } from './state/sender-archive.actions';
import { GET_SYNC_ARCHIVE } from './state/sender-archive.reducer';

@Component({
	selector: 'app-sender-archive',
	templateUrl: './sender-archive.component.html',
})
export class SenderArchiveComponent extends BaseListingComponent implements OnInit {

	count?: number;

	loadedSubscription?: Subscription;

	senderArchiveItens?: SenderSyncArchive[];

	searchEvent: SearchEvent = new SearchEvent;

	constructor(private service: SenderArchiveService,
		private store: Store) {
		super();
	}

	ngOnInit() {
		this.init();

		this.loadedSubscription = this.store.pipe(select(GET_SYNC_ARCHIVE)).subscribe(
			countAndItems => {
				this.count = countAndItems?.count;
				this.senderArchiveItens = countAndItems?.items
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
					// self.eventClickHandler(data);
				});
				return row;
			}
		};

		this.loadSenderArchiveData();

		this.store.select(GET_SYNC_ARCHIVE).subscribe((value) => {
			this.senderArchiveItens = value?.items
		})

	}

	loadSenderArchiveData() {

		this.service.getArchiveCountAndItems().subscribe(countAndItems => {
			this.store.dispatch(new SenderArchivedLoaded(countAndItems));
		});

	}

	searchByPeriod(event: Event) {
		this.service.getSyncArchivedByDate(this.searchEvent).subscribe(countAndItems => {
			this.store.dispatch(new UpdateSenderArchivedLoaded(countAndItems));
		});
	}

	ngOnDestroy(): void {
		this.loadedSubscription?.unsubscribe();
		super.ngOnDestroy();
	}

}
