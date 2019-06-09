import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../service/registration.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

   email:String=''
   password:String=''
   confirmPassword:String=''
   messages: Array<string>;
   loadingMessage:String=''
  constructor(private router: Router,private registrationService:RegistrationService) { }

  ngOnInit() {
    this.loadingMessage = ''

  }

  handleRegistrationSubmition() {
    this.loadingMessage = 'loading Please Wait !'
    this.messages = new Array<string>();
    this.registrationService.executeRegistrationForm(this.email ,
       this.password , this.confirmPassword).subscribe(data => {
        this.router.navigate(['confirmation-mail-sent'])
      },
      error => {
        this.loadingMessage = ''
        this.messages = error.error.messages;
        //console.log(error.error.messages)
      })
  }
}
