import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserService {


  baseURL = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  getAuthoHeader() : any {
    const headers = {
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem("token")
    }
    return{
      headers: headers
    };
  }

  getAllUsers() : Observable<any[]>{
    return this.http.get<any[]>(this.baseURL + "/api/users/getAllUsers")
  }

  getSubordinateUsers(username: string) : Observable<any[]>{
    return this.http.get<any[]>(this.baseURL + "/api/users/getSubordinateUsers/" + username)
  }

  login(data: any) {
    return this.http.post(this.baseURL + "/api/users/login", data);
  }

  current() {
    return this.http.get(this.baseURL + "/api/users/current", this.getAuthoHeader());
  }

  register(data: any) {
    return this.http.post(this.baseURL + "/api/users/register", data,  this.getAuthoHeader());
  }

  activateAccount(code: any){
    return this.http.post(this.baseURL + "/api/users/checkActivationCode", code, {responseType:'text'})
  }

  sendEmailCode(data: any) {
    console.log("in send email")
    return this.http.post(this.baseURL + "/api/users/loginCode", data, {responseType:'text'})
  }

  resetPass( data: any) {
    console.log("in reset")
    return this.http.post(this.baseURL + "/api/users/forgottenpassword", data, {responseType:'text'} )
  }

  newPass(data: any) {
    console.log("sending new pass")
    return this.http.post(this.baseURL + "/api/users/checkForgottenPassword", data)
  }
}
