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
    commonName:"dwawaddwa",
    organisationUnit:"wdwad",
    organisationName:"dwadwa",
    email:"dwadwa",
    privateKeyPass : "1234",
    alias: "nekiAlias69"
  }

  SubCertDTO = {
    begin:"",
    end:"",
    commonName:"dwawaddwa",
    organisationUnit:"wdwad",
    organisationName:"dwadwa",
    email:"dwadwa",
    privateKeyPass : "1234",
    alias: "nekiAlias99",
    issuerAlias: "nekiAlias69",
    usage: 2
  }

  submit() {
    this.createRootCertificate(this.CertDTO)
    //this.createSubCertificate(this.SubCertDTO)
  }

  ngOnInit(): void {
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
