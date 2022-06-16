import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuccessfulActivationComponent } from './successful-activation.component';

describe('SuccessfulActivationComponent', () => {
  let component: SuccessfulActivationComponent;
  let fixture: ComponentFixture<SuccessfulActivationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SuccessfulActivationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SuccessfulActivationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
