import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CertificateService } from '../certificate.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  constructor(private router: Router, private _certificateService: CertificateService) { }

  allCertificates = [] as any;
  currentUser = "";
  role = -1;

  ngOnInit(): void {
    this.getAllCertificates()

    const helper = new JwtHelperService();
    this.currentUser = helper.decodeToken(localStorage.getItem('token') || '{}').sub;
    this.role = helper.decodeToken(localStorage.getItem('token') || '{}').roles;
  }

  getAllCertificates() {
    this._certificateService.getAllCertificates().subscribe(data => this.allCertificates = data); 
  }

  revokeCertificate(serialNumber: number) {
    this._certificateService.revokeCertificate(serialNumber).subscribe(data => console.log("Revoked"),
      error => console.log(error));
  }

  logout(){
    localStorage.clear();
  }

}
