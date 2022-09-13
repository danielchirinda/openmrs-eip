import {Component, OnInit} from '@angular/core';
import { Store } from '@ngrx/store';
import { SyncDetailLoaded } from './sync-detail/state/sync-detail.actions';
import { SyncDetailService } from './sync-detail/sync-detail.service';
import { SyncHistoryLoaded } from './sync-detail/table-sync-status/state/sync-history.actions';

@Component({
	selector: 'sender',
	templateUrl: './sender.component.html',
	styleUrls: ['./sender.component.scss']
})
export class SenderComponent implements OnInit {

	constructor(private service: SyncDetailService,
		private store: Store) {
	}

	ngOnInit(): void {

		// Load the data
		this.service.getSyncStatusDetails().subscribe(countAndItems => {
			console.log('loaded itens from database new', countAndItems);
			this.store.dispatch(new SyncHistoryLoaded(countAndItems));
		});


	}








}
