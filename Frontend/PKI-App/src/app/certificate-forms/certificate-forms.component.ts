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
    commonName:"",
    organisationUnit:"",
    organisationName:"",
    country:"",
    email:""
  }

  submit() {
    console.log(this.CertDTO)
    console.log("POSLE INICIJALNOG LOGA")
    this.createCertificate(this.CertDTO)
  }

  ngOnInit(): void {
  }

  createCertificate(certificate: any) {
    this._certificateService.createCertificate(certificate).subscribe(data => console.log(data),
      error => console.log(error));
  }

}
