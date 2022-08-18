import {Action} from "@ngrx/store";
import {ReceiverDetail} from './../receiver-detail'
import {ReceiverDetailCountAndItems} from './../receiver-detail-count-and-items'

export enum ReceiverDetailActionType {
	RECEIVER_DETAIL_LOADED = 'RECEIVER_DETAIL_LOADED',
	VIEW_RECEIVER_DETAIL = 'VIEW_RECEIVER_DETAIL'
}

export class ReceiverDetailLoaded implements Action {

	readonly type = ReceiverDetailActionType.RECEIVER_DETAIL_LOADED;

	constructor(public countAndItems?: ReceiverDetailCountAndItems) {
	}

}

export class ViewReceiverMessage implements Action {

	readonly type = ReceiverDetailActionType.VIEW_RECEIVER_DETAIL;

	constructor(public receiverMesage?: ReceiverDetail) {
	}

}

export type ReceiverDetailAction = ReceiverDetailLoaded | ViewReceiverMessage;
