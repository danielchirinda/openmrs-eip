import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableSyncStatusComponent } from './table-sync-status.component';

describe('TableStatusComponent', () => {
  let component: TableSyncStatusComponent;
  let fixture: ComponentFixture<TableSyncStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TableSyncStatusComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TableSyncStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
