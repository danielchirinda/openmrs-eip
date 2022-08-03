import {Event} from "../event";
import {BaseEntity} from "../../shared/base-entity";
import { SyncMessageEvent } from "./sync-message-event";

export class SyncDetail extends BaseEntity {

	primaryKeyId?: any;

	event?: SyncMessageEvent;

}
