import { Injectable } from "@angular/core";
import { BaseService } from "../../shared/base.service";
import { Observable } from "rxjs";
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

	getSyncArchivedByDate(startDate: any, endDate: any): Observable<SenderSyncArchiveCountAndItems> {
		return this.getWithParams(RESOURCE_NAME, { startDate: startDate, endDate: endDate })
	}

}
