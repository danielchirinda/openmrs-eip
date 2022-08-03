import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SyncDetailComponent } from './sync-detail.component';

describe('SyncDetailComponent', () => {
  let component: SyncDetailComponent;
  let fixture: ComponentFixture<SyncDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SyncDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SyncDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
