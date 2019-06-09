import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { API_URL, contacts_Api, removeContact, retrieveContacts, updateContact, addContact } from '../app.constants';
import { Contact, RetrieveContactsResponse } from '../contacts-list/contacts-list.component';

@Injectable({
  providedIn: 'root'
})
export class ContactDataService {
  contact:Contact;

  constructor(
    private http:HttpClient
  ) { }

  deleteContact(contactId){
    return this.http.delete(`${API_URL}/${contacts_Api}/${removeContact}/${contactId}`);
  } 

  retrieveContacts(userContactsForm){
    let params = new HttpParams();
params = params.append('email', userContactsForm.email);
params = params.append('pageNumber', userContactsForm.pageNumber);
params = params.append('pageSize', userContactsForm.pageSize);
params = params.append('sortBy', userContactsForm.sortBy);
params = params.append('sortDirection', userContactsForm.sortDirection);

    return this.http.get<RetrieveContactsResponse>(`${API_URL}/${contacts_Api}/${retrieveContacts}`,{params: params});
  }

  updateContact(contact){
    return this.http.put(
          `${API_URL}/${contacts_Api}/${updateContact}`
                , contact);
  }

  createContact(contact){
    return this.http.post(
              `${API_URL}/${contacts_Api}/${addContact}`
                , contact);
  }

  setContactForUpdate(contact){
      this.contact = contact;
  }

  getContactForUpdate(){
    return this.contact;
  }
}
