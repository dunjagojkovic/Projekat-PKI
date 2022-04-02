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
    commonName:"dwawaddwa",
    organisationUnit:"wdwad",
    organisationName:"dwadwa",
    email:"dwadwa",
    privateKeyPass : "1234",
    alias: "nekiAlias2"
  }

  submit() {
    console.log(this.CertDTO)
    console.log("POSLE INICIJALNOG LOGA")
    this.createCertificate(this.CertDTO)
  }

  ngOnInit(): void {
  }

  createCertificate(certificate: any) {
    this._certificateService.createRootCertificate(certificate).subscribe(data => console.log(data),
      error => console.log(error));
  }

}
