import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { FormBuilder, Validators} from '@angular/forms';
import { MatSnackBar} from '@angular/material/snack-bar';
import { UserService } from '../user.service';

@Component({
  selector: 'app-frontpage',
  templateUrl: './frontpage.component.html',
  styleUrls: ['./frontpage.component.css']
})
export class FrontpageComponent implements OnInit {

  user: any;
  hide = true;
  form: FormGroup;

    constructor(
    private router: Router,
    private formBuilder : FormBuilder,
    private service: UserService,
    private _snackBar: MatSnackBar
    ) {
      this.form = this.formBuilder.group({
        username: ['', Validators.required],
        password: ['', Validators.required]
      })
     }


  ngOnInit(): void {
  }



  onSubmit() {
    if(this.form.valid){
      const username = this.form.get('username')?.value;
      const password = this.form.get('password')?.value;

      let data = {
        username: username,
        password: password
      }

      this.service.login(data).subscribe((any: any) => {
        if(any.token!=null){
          localStorage.setItem('token', any.token);
          this.router.navigateByUrl("/home");
        }
        else
        alert(any);
/*
        this.service.current().subscribe((user: any) => {
          localStorage.setItem('user', JSON.stringify(user)), () => this.router.navigate(["/home"]);
          console.log(user);
        }, error => {
          this._snackBar.open('Incorrect credentials! Please try again.', 'Close', {duration: 2000})});*/
      })
    }
  }

  resetPassword() {
    const username = this.form.get('username')?.value
    console.log(username)
    let data = {
      username: username
    }
    if (username=='' || username==null){
      alert("Enter username first!")
      return;
    }
    this.service.resetPass(data).subscribe((any: any) => {
      this._snackBar.open('Check your email!', 'Close', {duration: 2000});
      console.log(any);
    }, error => {
      console.log(error)
      this._snackBar.open('Something is wrong!', 'Close', {duration: 2000})})
  }
}
