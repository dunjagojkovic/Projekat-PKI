import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CertificateService {
  httpOptions = {
    headers: new HttpHeaders().set('Content-Type', 'application/json')
  };

  constructor(private http: HttpClient) { }

  getValidIssuers() : Observable<any[]>{
    return this.http.get<any[]>("http://localhost:8080/api/certificates/getValidIssuers")
  }

  createCertificate(certificate: any): Observable<any> {
    return this.http.post<any>("http://localhost:8080/api/certificates/registerCert", JSON.stringify(certificate), this.httpOptions);
  }

  createRootCertificate(certificate: any): Observable<any> {
    return this.http.post<any>("http://localhost:8080/api/certificates/registerRoot", JSON.stringify(certificate), this.httpOptions);
  }

  createSubCertificate(certificate: any): Observable<any> {
    return this.http.post<any>("http://localhost:8080/api/certificates/registerSub", JSON.stringify(certificate), this.httpOptions);
  }
}
