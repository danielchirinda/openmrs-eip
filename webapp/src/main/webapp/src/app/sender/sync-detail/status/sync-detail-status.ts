import {Event} from "../../event";
import {BaseEntity} from "../../../shared/base-entity";
import { SyncMessageEvent } from "../sync-message-event";
import { SyncMessageStatus } from "./sync-message-status";

export class SyncDetailStatus extends BaseEntity {

	primaryKeyId?: any;

	event?: SyncMessageStatus;

}
