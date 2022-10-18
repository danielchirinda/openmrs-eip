import { Component, OnInit } from '@angular/core';
import { Dashboard } from 'src/app/shared/dashboard';
import { DashboardComponent } from 'src/app/shared/dashboard.component';
import { DashboardService } from 'src/app/shared/dashboard.service';

@Component({
  selector: 'app-receiver-dashboard',
  templateUrl: './receiver-dashboard.component.html',
  styleUrls: ['./receiver-dashboard.component.scss']
})
export class ReceiverDashboardComponent implements OnInit {

	dashboard?: Dashboard

	constructor(
		private service: DashboardService,
	) {
	}


	ngOnInit(): void {
	}
}
