import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SyncDetailService } from './sync-detail.service';
import { SyncMessageEvent } from './sync-message-event';

@Component({
	selector: 'ngbd-modal-confirm',
	templateUrl: './confirm-modal.component.html',
})
export class NgbdModalConfirm implements OnInit  {
	@Input() public syncMessageEvent!: SyncMessageEvent;

	public syncMessage!: SyncMessageEvent;

	message!: String;

	constructor(public modal: NgbActiveModal,
		private service: SyncDetailService) {}

	ngOnInit():void {
		this.syncMessage = this.syncMessageEvent

		if (this.syncMessage.status == 'RECEIVED') {
			this.message = 'Item Already send And received by central server. '
		} else {
			this.message = 'Are you sure you want to Resend the event to the central server?';
		}
	}

	resendEvent() {
		this.service.resendEvent(this.syncMessage).subscribe(() =>{

		})
		this.modal.close('Ok click');
	}
}
