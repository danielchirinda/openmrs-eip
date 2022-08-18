import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { select, Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { BaseListingComponent } from 'src/app/shared/base-listing.component';
import { ReceiverDetail } from './receiver-detail';
import { ReceiverDetailService } from './receiver-detail.service';
import { ReceiverDetailLoaded } from './state/receiver-detail.actions';
import {GET_RECEIVER_DETAIL} from './state/receiver-detail.reducer';

@Component({
	selector: 'app-receiver-detail',
	templateUrl: './receiver-detail.component.html',
	styleUrls: ['./receiver-detail.component.scss']
})
export class ReceiverDetailComponent extends BaseListingComponent implements OnInit {


	count?: number;

	events?: ReceiverDetail[];

	loadedSubscription?: Subscription;

	constructor(
		private service: ReceiverDetailService,
		private store: Store,
		private modalService: NgbModal
	) {
		super();
	}

	ngOnInit(): void {

		this.init();
		this.loadedSubscription = this.store.pipe(select(GET_RECEIVER_DETAIL)).subscribe(
			countAndItems => {
				this.count = countAndItems?.count;
				this.events = countAndItems?.items;
				this.reRender();
			}
		);

		this.loadDetails();

	}

	loadDetails(): void {
		this.service.getDetailCountAndItems().subscribe(countAndItems => {
			console.log('loaded itens from database', countAndItems)
			this.store.dispatch(new ReceiverDetailLoaded(countAndItems));
		});
	}

	ngOnDestroy(): void {
		this.loadedSubscription?.unsubscribe();
		super.ngOnDestroy();
	}
}
