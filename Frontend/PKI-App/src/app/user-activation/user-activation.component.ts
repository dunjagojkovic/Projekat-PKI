import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../user.service";
import {FormBuilder} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-user-activation',
  templateUrl: './user-activation.component.html',
  styleUrls: ['./user-activation.component.css']
})
export class UserActivationComponent implements OnInit {

  constructor(
    private router: Router,
    private api: UserService,
    private formBuilder : FormBuilder,
    private service: UserService,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    let url = window.location.href
    var code = url.split('/')[4]
    console.log(code)

    this.api.activateAccount(code).subscribe({
      next:(response) =>{
        console.log(response)
        this.router.navigate(['successfullactivation'])
      }, error: (err)=>{
        console.log(err)
      }});
  }


}
