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

  getAllCertificates() : Observable<any[]>{
    return this.http.get<any[]>("http://localhost:8080/api/certificates/getAllCertificates")
  }

  getSubordinateCertificates(username: string) : Observable<any[]>{
    return this.http.get<any[]>("http://localhost:8080/api/certificates/getSubordinateCertificates/" + username)
  }

  getValidIssuers() : Observable<any[]>{
    return this.http.get<any[]>("http://localhost:8080/api/certificates/getValidIssuers")
  }

  getValidIssuersForUser(username: string) : Observable<any[]>{
    return this.http.get<any[]>("http://localhost:8080/api/certificates/getValidIssuersForUser/" + username)
  }

  createCertificate(certificate: any): Observable<any> {
    return this.http.post<any>("http://localhost:8080/api/certificates/registerCert", JSON.stringify(certificate), this.getAuthoHeader() );
  }

  createRootCertificate(certificate: any): Observable<any> {
    return this.http.post<any>("http://localhost:8080/api/certificates/registerRoot", JSON.stringify(certificate), this.getAuthoHeader() );
  }

  createSubCertificate(certificate: any): Observable<any> {
    return this.http.post<any>("http://localhost:8080/api/certificates/registerSub", JSON.stringify(certificate), this.getAuthoHeader() );
  }

  revokeCertificate(serialNumber: number): Observable<any> {
    return this.http.post<any>("http://localhost:8080/api/certificates/revokeCert/" + serialNumber , this.getAuthoHeader() );
  }

  public downloadCertificate(alias : string){
    const requestOptions: Object = {
      /* other options here */
      observe: 'response',
      responseType: 'blob'
    }
    return this.http.get<any>("http://localhost:8080/api/certificates/"+alias+".cer", requestOptions); 
  }
}
