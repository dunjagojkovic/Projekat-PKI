import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {MatSelectModule} from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { FrontpageComponent } from './frontpage/frontpage.component';
import { HomepageComponent } from './homepage/homepage.component';
import { CertificateFormsComponent } from './certificate-forms/certificate-forms.component';
import { CertificateService } from './certificate.service';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule} from '@angular/material/snack-bar';
import { UserService } from './user.service';
import { PasswordResetComponent } from './password-reset/password-reset.component';
import { SuccessfulActivationComponent } from './successful-activation/successful-activation.component';
import { UserActivationComponent } from './user-activation/user-activation.component';
import { FrontPageCodeComponent } from './front-page-code/front-page-code.component';




@NgModule({
  declarations: [
    AppComponent,
    FrontpageComponent,
    HomepageComponent,
    CertificateFormsComponent,
    PasswordResetComponent,
    SuccessfulActivationComponent,
    UserActivationComponent,
    FrontPageCodeComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
    MatButtonModule,
    MatToolbarModule,
    FormsModule,
    ReactiveFormsModule,
    MatSnackBarModule

  ],
  providers: [HttpClientModule, CertificateService, UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
