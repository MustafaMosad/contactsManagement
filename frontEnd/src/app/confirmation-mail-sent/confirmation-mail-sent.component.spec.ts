import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmationMailSentComponent } from './confirmation-mail-sent.component';

describe('ConfirmationMailSentComponent', () => {
  let component: ConfirmationMailSentComponent;
  let fixture: ComponentFixture<ConfirmationMailSentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmationMailSentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmationMailSentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
