import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {ReceiverDetail} from "./receiver-detail";
import {BaseService} from "../../shared/base.service";
import { ReceiverDetailCountAndItems } from './receiver-detail-count-and-items';

const RESOURCE_NAME = 'receiver/details';

@Injectable({
	providedIn: 'root'
})
export class ReceiverDetailService extends BaseService<ReceiverDetail> {

	getDetailCountAndItems(): Observable<ReceiverDetailCountAndItems> {
		return this.getCountAndItems(RESOURCE_NAME);
	}
}
