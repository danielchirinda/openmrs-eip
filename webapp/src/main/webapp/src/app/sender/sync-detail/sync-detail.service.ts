import {Injectable} from "@angular/core";
import {BaseService} from "../../shared/base.service";
import {Observable} from "rxjs";
import { SyncDetail} from "./sync-detail";
import { SyncDetailEventCountAndItems } from "./sync-detail-event-count-and-items";
import { SyncMessageEvent } from "./sync-message-event";
import { SyncDetailCountAndItems } from "./status/sync-detail-count-and-item";

const RESOURCE_NAME = 'sender/sync-details';
const RESOURCE_NAME_STATUS = RESOURCE_NAME + '/status';

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


}
