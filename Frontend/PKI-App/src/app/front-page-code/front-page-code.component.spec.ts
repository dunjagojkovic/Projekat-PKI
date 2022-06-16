import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FrontPageCodeComponent } from './front-page-code.component';

describe('FrontPageCodeComponent', () => {
  let component: FrontPageCodeComponent;
  let fixture: ComponentFixture<FrontPageCodeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FrontPageCodeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FrontPageCodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
