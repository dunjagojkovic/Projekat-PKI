import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CertificateService } from '../certificate.service';

@Component({
  selector: 'app-certificate-forms',
  templateUrl: './certificate-forms.component.html',
  styleUrls: ['./certificate-forms.component.css']
})
export class CertificateFormsComponent implements OnInit {

  constructor(private router: Router, private _certificateService: CertificateService) { }
  hide = true;

  CertDTO = {
    begin:"",
    end:"",
    commonName:"",
    organisationUnit:"",
    organisationName:"",
    email:"",
    privateKeyPass : "1234",
    alias: ""
  }
  selectedSerialNumber = 0;
  validIssuers = [] as any;

  submit() {
    this.createRootCertificate(this.CertDTO)
  }

  ngOnInit(): void {
    this.getValidIssuers()
  }

  getValidIssuers() {
    this._certificateService.getValidIssuers().subscribe(data => this.validIssuers = data); 
  }

  createRootCertificate(certificate: any) {
    this._certificateService.createRootCertificate(certificate).subscribe(data => console.log("sent root"),
      error => console.log(error));
  }

  createSubCertificate(certificate: any) {
    this._certificateService.createSubCertificate(certificate).subscribe(data => console.log("sent sub"),
      error => console.log(error));
  }

}
