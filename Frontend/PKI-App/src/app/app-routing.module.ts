import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FrontpageComponent } from './frontpage/frontpage.component';
import { HomepageComponent } from './homepage/homepage.component';
import { CertificateFormsComponent } from './certificate-forms/certificate-forms.component';
import {UserActivationComponent} from "./user-activation/user-activation.component";
import {PasswordResetComponent} from "./password-reset/password-reset.component";
import {SuccessfulActivationComponent} from "./successful-activation/successful-activation.component";
import {FrontPageCodeComponent} from "./front-page-code/front-page-code.component";

const routes: Routes = [
  { path: '', component: FrontpageComponent},
  { path: 'frontcode', component: FrontPageCodeComponent},
  { path: 'home', component: HomepageComponent},
  { path: 'certificate', component: CertificateFormsComponent},
  { path: 'activation/:code', component: UserActivationComponent},
  {path: 'reset/:code', component: PasswordResetComponent},
  {path: 'successfullactivation', component: SuccessfulActivationComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
