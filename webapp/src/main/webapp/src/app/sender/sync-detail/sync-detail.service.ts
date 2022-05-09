import {Injectable} from "@angular/core";
import {BaseService} from "../../shared/base.service";
import {Observable} from "rxjs";
import { SyncDetail} from "./sync-detail";
import { SyncDetailEventCountAndItems } from "./sync-detail-event-count-and-items";

const RESOURCE_NAME = 'sender/event';

@Injectable({
	providedIn: 'root'
})
export class SyncDetailService extends BaseService<SyncDetail> {

	getEventCountAndItems(): Observable<SyncDetailEventCountAndItems> {
		return this.getCountAndItems(RESOURCE_NAME);
	}

}
