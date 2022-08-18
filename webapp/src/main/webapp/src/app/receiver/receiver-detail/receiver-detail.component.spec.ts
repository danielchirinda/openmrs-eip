import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReceiverDetailComponent } from './receiver-detail.component';

describe('ReceiverDetailComponent', () => {
  let component: ReceiverDetailComponent;
  let fixture: ComponentFixture<ReceiverDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReceiverDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReceiverDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
