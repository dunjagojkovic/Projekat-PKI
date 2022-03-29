import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificateFormsComponent } from './certificate-forms.component';

describe('CertificateFormsComponent', () => {
  let component: CertificateFormsComponent;
  let fixture: ComponentFixture<CertificateFormsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CertificateFormsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CertificateFormsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
