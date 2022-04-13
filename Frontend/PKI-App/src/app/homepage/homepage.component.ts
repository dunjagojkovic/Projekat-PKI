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
  role = "";

  ngOnInit(): void {
    const helper = new JwtHelperService();
    this.currentUser = helper.decodeToken(localStorage.getItem('token') || '{}').sub;
    this.role = helper.decodeToken(localStorage.getItem('token') || '{}').roles;
    console.log(helper.decodeToken(localStorage.getItem('token') || '{}'))
    
    if(this.role == "Admin")
      this.getAllCertificates()
    else
      this.getSubordinateCertificates()
  }

  getAllCertificates() {
    this._certificateService.getAllCertificates().subscribe(data => this.allCertificates = data); 
  }

  getSubordinateCertificates() {
    this._certificateService.getSubordinateCertificates(this.currentUser).subscribe(data => this.allCertificates = data); 
  }

  revokeCertificate(serialNumber: number) {
    this._certificateService.revokeCertificate(serialNumber).subscribe(data => console.log("Revoked"),
      error => console.log(error));
  }

  logout(){
    localStorage.clear();
  }

  download(alias : string){
    this._certificateService.downloadCertificate(alias).subscribe(response =>{

      let fileName = response.headers.get('content-disposition')?.split(';')[1].split('=')[1];
      fileName = alias+'.cer'
      let blob:Blob = response.body as Blob;
      let a = document.createElement('a');
      a.download = fileName;
      a.href = window.URL.createObjectURL(blob);
      a.click();
    });
  }

}
