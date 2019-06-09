import { Component, OnInit } from '@angular/core';
import { ContactDataService } from '../service/contact-data.service';
import { Router } from '@angular/router';
import { LoginService } from '../service/login.service';

export class ContactForm {
  constructor(public firstName: string,
    public lastName: string,
    public email: string,
    public phone: string,
    public userEmail: string) {

  }
}

@Component({
  selector: 'app-add-new-contact',
  templateUrl: './add-new-contact.component.html',
  styleUrls: ['./add-new-contact.component.css']
})
export class AddNewContactComponent implements OnInit {
  messages: Array<string>;

  firstName: string
   lastName: string
   email: string
   phone: string
   userEmail: string

  constructor(private router:Router ,private loginService:LoginService, private contactService:ContactDataService ) { }

  ngOnInit() {
  }

  handleAddContactSubmition(){
    this.userEmail = this.loginService.getAuthenticatedUser();
    this.contactService.createContact(new ContactForm(this.firstName,this.lastName,this.email,this.phone,this.userEmail)).subscribe(data => {
        this.router.navigate(['contacts'])
      },
      error => {
        this.messages = error.error.messages;
        //console.log(error.error.messages)
      })
   }
}
