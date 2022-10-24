import {Injectable} from "@angular/core";
import {BaseService} from "../../shared/base.service";
import {Observable} from "rxjs";
import { SearchEvent } from "./search-event";
import { SenderSyncArchive } from "./sender-archive";
import { SenderSyncArchiveCountAndItems } from "./sender-sync-archive-count-and-items";

const RESOURCE_NAME = 'sender/archive';

@Injectable({
	providedIn: 'root'
})
export class SenderArchiveService extends BaseService<SenderSyncArchive> {

	getArchiveCountAndItems(): Observable<SenderSyncArchiveCountAndItems> {
		return this.getCountAndItems(RESOURCE_NAME);
	}

	getSyncArchivedByDate(searchEvent: SearchEvent): Observable<SenderSyncArchiveCountAndItems>{
		return this.getWithParams(RESOURCE_NAME, {startDate:searchEvent.startDate, endDate:searchEvent.endDate})
	}

}
