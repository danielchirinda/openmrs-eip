import {Injectable} from "@angular/core";
import {BaseService} from "../../shared/base.service";
import {Observable} from "rxjs";
import { SyncDetail} from "./sync-detail";
import { SyncDetailEventCountAndItems } from "./sync-detail-event-count-and-items";
import { SyncMessageEvent } from "./sync-message-event";
import { SyncDetailCountAndItems } from "./status/sync-detail-count-and-item";
import { SearchEvent } from "./search-event";
import { ResendMessage } from "./resend-message-id";

const RESOURCE_NAME = 'sender/sync-details';
const RESOURCE_NAME_STATUS = RESOURCE_NAME + '/status';
const RESOURCE_NAME_ENV_BY_DATE = RESOURCE_NAME + '/sync-event';
const RESEND_MULTIPLE_EVENT = RESOURCE_NAME + '/resend'
const RESOURCE_NAME_HIST_BY_DATE = RESOURCE_NAME + '/sync-history';


@Injectable({
	providedIn: 'root'
})
export class SyncDetailService extends BaseService<SyncDetail> {

	getEventCountAndItems(): Observable<SyncDetailEventCountAndItems> {
		return this.getCountAndItems(RESOURCE_NAME);
	}

	resendEvent(syncMessage: SyncMessageEvent): Observable<any> {
		return this.update(RESOURCE_NAME, syncMessage);
	}

	getSyncStatusDetails(): Observable<SyncDetailCountAndItems>{
		return this.getCountAndItems(RESOURCE_NAME_STATUS);
	}

	getSyncEventByDate(searchEvent: SearchEvent): Observable<SyncDetailCountAndItems>{
		return this.postCountAndItems(RESOURCE_NAME_ENV_BY_DATE, searchEvent);
	}

	resendMultipleEvent(resendMessage: ResendMessage): Observable<any> {
		return this.updateMultiple(RESEND_MULTIPLE_EVENT, resendMessage);
	}

	getSyncHistoryByDate(searchEvent: SearchEvent): Observable<SyncDetailCountAndItems>{
		return this.postCountAndItems(RESOURCE_NAME_HIST_BY_DATE, searchEvent);
	}


}
