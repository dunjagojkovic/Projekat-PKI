import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FrontpageComponent } from './frontpage/frontpage.component';
import { HomepageComponent } from './homepage/homepage.component';
import { CertificateFormsComponent } from './certificate-forms/certificate-forms.component';

const routes: Routes = [
  { path: '', component: FrontpageComponent},
  { path: 'home', component: HomepageComponent},
  { path: 'certificate', component: CertificateFormsComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
