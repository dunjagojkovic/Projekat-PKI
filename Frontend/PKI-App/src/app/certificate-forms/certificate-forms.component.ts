import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CertificateService } from '../certificate.service';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import { MatSnackBar} from '@angular/material/snack-bar';
import { UserService } from '../user.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-certificate-forms',
  templateUrl: './certificate-forms.component.html',
  styleUrls: ['./certificate-forms.component.css']
})
export class CertificateFormsComponent implements OnInit {
  form: FormGroup;

  constructor(
    private router: Router,
    private _certificateService: CertificateService,
    private userService: UserService,
    private formBuilder : FormBuilder,
    private _snackBar: MatSnackBar
    ) {
      this.form = this.formBuilder.group({
        username: ['', Validators.required],
        password: ['', Validators.required]
      })
     }
  hide = true;
 

  CertDTO = {
    begin:"",
    end:"",
    commonName:"",
    organisationUnit:"",
    organisationName:"",
    email:"",
    privateKeyPass : "1234",
    alias: "",
    issuerAlias: "",
    usage: 0,
    username: "",
    password: "",
    userId: null
  }
  selectedIssuer = {
    alias: "",
    serialNumber: "" 
  }
  selectedType = "Root";
  validIssuers = [] as any;
  users = [] as any;
  currentUser = "";
  role = "";

  submit() {
    if(this.isRootSelected()) {
      this.CertDTO.issuerAlias = "";
      this.CertDTO.usage = 0;
      this.createRootCertificate(this.CertDTO);
    }
    else
    {
      this.CertDTO.issuerAlias = this.selectedIssuer.alias;
      if(this.selectedType == "ca")
      {
        this.CertDTO.usage = 0;
      }
      else if(this.selectedType == "signing")
      {
        this.CertDTO.usage = 1;
      }
      else
      {
        this.CertDTO.usage = 2;
      }
      this.createSubCertificate(this.CertDTO)
    }
  }

  ngOnInit(): void {
    const helper = new JwtHelperService();
    this.currentUser = helper.decodeToken(localStorage.getItem('token') || '{}').sub;
    this.role = helper.decodeToken(localStorage.getItem('token') || '{}').roles;
    console.log(helper.decodeToken(localStorage.getItem('token') || '{}'))
    
    if(this.role == "admin")
    {
      this.getAllUsers()
      this.getValidIssuers()
    }
    else
    {
      this.getSubordinateUsers()
      this.getValidIssuersForUser()
    }
  }

  isRootSelected() {
    if(this.selectedType != "Root")
      return false;
    return true;
  }

  getValidIssuers() {
    this._certificateService.getValidIssuers().subscribe(data => this.validIssuers = data); 
  }

  getValidIssuersForUser() {
    this._certificateService.getValidIssuersForUser(this.currentUser).subscribe(data => this.validIssuers = data); 
  }

  getAllUsers() {
    this.userService.getAllUsers().subscribe(data => this.users = data); 
  }

  getSubordinateUsers() {
    this.userService.getSubordinateUsers(this.currentUser).subscribe(data => this.users = data); 
  }

  createRootCertificate(certificate: any) {
    this._certificateService.createRootCertificate(certificate).subscribe(data => console.log("sent root"),
      error => console.log(error));
  }

  createSubCertificate(certificate: any) {
    this._certificateService.createSubCertificate(certificate).subscribe(data => console.log("sent sub"),
      error => console.log(error));
  }

  onSubmit() {
    if(this.form.valid){
      const username = this.form.get('username')?.value;
      const password = this.form.get('password')?.value;

      let data = {
        username: username,
        password: password
      }

      this.userService.register(data).subscribe( data => console.log("uspesno"),
        error =>  this._snackBar.open('Username already exists', 'Close', {duration: 5000})      
        );
        
        
  }
}
 

}
