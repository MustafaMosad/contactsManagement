import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Host } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule ,ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { ErrorComponent } from './error/error.component';
import { HomeComponent } from './home/home.component';
import { AccountConfirmationComponent } from './account-confirmation/account-confirmation.component';
import { ConfirmationMailSentComponent } from './confirmation-mail-sent/confirmation-mail-sent.component';
import { RoutGuardService } from './service/rout-guard.service';
import { ContactsListComponent } from './contacts-list/contacts-list.component';
import { MenuComponent } from './menu/menu.component';
import { FooterComponent } from './footer/footer.component';
import { HttpInterceptorAuthenticationService } from './service/http-interceptor-authentication.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';
import { AddNewContactComponent } from './add-new-contact/add-new-contact.component';
import { UpdateContactComponent } from './update-contact/update-contact.component';
import { MatConfirmDialogComponent } from './mat-confirm-dialog/mat-confirm-dialog.component';
import {MatDialogModule} from '@angular/material';

const appRoutes: Routes = [
  {path:'',component: LoginComponent},
  {path:'updateContact',component: UpdateContactComponent , canActivate:[RoutGuardService]},
  {path:'createContact',component:AddNewContactComponent , canActivate:[RoutGuardService]}, 
  {path:'contacts',component : ContactsListComponent , canActivate:[RoutGuardService]},
  {path:'confirmation-mail-sent',component : ConfirmationMailSentComponent },
  {path:'confirm-account',component: AccountConfirmationComponent },
  {path:'home',component:HomeComponent , canActivate:[RoutGuardService]},
  {path: 'login' , component: LoginComponent},
  { path: 'registration', component: RegistrationComponent } ,
  { path: '**', component: ErrorComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    ErrorComponent,
    HomeComponent,
    AccountConfirmationComponent,
    ConfirmationMailSentComponent,
    ContactsListComponent,
    MenuComponent,
    FooterComponent,
    AddNewContactComponent,
    UpdateContactComponent,
    MatConfirmDialogComponent
  ],
  imports: [
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    ),
    // other imports here
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    MaterialModule,
    MatDialogModule,
    ReactiveFormsModule,
    BrowserAnimationsModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorAuthenticationService, multi: true }

  ],
  bootstrap: [AppComponent],
  entryComponents:[MatConfirmDialogComponent]

})
export class AppModule { }
