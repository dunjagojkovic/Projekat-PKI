import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { FormBuilder, Validators} from '@angular/forms';
import { MatSnackBar} from '@angular/material/snack-bar';
import { UserService } from '../user.service';


@Component({
  selector: 'app-front-page-code',
  templateUrl: './front-page-code.component.html',
  styleUrls: ['./front-page-code.component.css']
})
export class FrontPageCodeComponent implements OnInit {
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
      code: ['', Validators.required]
    })
  }


  ngOnInit(): void {
  }



  onSubmit() {
    if(this.form.valid){
      const username = this.form.get('username')?.value;
      const code = this.form.get('code')?.value;

      let data = {
        username: username,
        code: code
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

  sendCode() {
    const username = this.form.get('username')?.value
    console.log(username)

    let data = {
      username: username
    }
     console.log(username);

    this.service.sendEmailCode(data).subscribe((any: any) => {
      this._snackBar.open('We sent a code to your email!', 'Close', {duration: 2000});
      console.log(any);
    }, error => {
      console.log(error)
      this._snackBar.open('Something is wrong!', 'Close', {duration: 2000})})

  }
}
