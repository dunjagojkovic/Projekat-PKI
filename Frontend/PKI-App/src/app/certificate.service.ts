import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CertificateService {
  getAuthoHeader() : any {
    const headers = {
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("token")
    }
    return{
      headers: headers
    };
  }  

  constructor(private http: HttpClient) { }

  createCertificate(certificate: any): Observable<any> {
    return this.http.post<any>("http://localhost:8080/api/certificates/registerCert", JSON.stringify(certificate), this.getAuthoHeader() );
  }

  createRootCertificate(certificate: any): Observable<any> {
    return this.http.post<any>("http://localhost:8080/api/certificates/registerRoot", JSON.stringify(certificate), this.getAuthoHeader() );
  }

  createSubCertificate(certificate: any): Observable<any> {
    return this.http.post<any>("http://localhost:8080/api/certificates/registerSub", JSON.stringify(certificate), this.getAuthoHeader() );
  }

 
}
