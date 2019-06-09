import { Component, OnInit } from '@angular/core';
import { ContactDataService } from '../service/contact-data.service';
import { Router } from '@angular/router';
import { LoginService } from '../service/login.service';
import { Contact } from '../contacts-list/contacts-list.component';

export class ContactForm {
  constructor(public firstName: string,
    public lastName: string,
    public email: string,
    public phone: string,
    public contactId: string) {

  }
}
@Component({
  selector: 'app-update-contact',
  templateUrl: './update-contact.component.html',
  styleUrls: ['./update-contact.component.css']
})
export class UpdateContactComponent implements OnInit {

  messages: Array<string>;

  contact:Contact;

  constructor(private router: Router, private loginService: LoginService, private contactService: ContactDataService) { }

  ngOnInit() {
    this.contact = this.contactService.getContactForUpdate();
  }
  handleUpdateContactSubmition() {
    this.contact.userEmail = this.loginService.getAuthenticatedUser();
    this.contactService.updateContact(this.contact).subscribe(data => {
      this.router.navigate(['contacts'])

    },
      error => {
        this.messages = error.error.messages;
        //console.log(error.error.messages)
      })
  }

}

