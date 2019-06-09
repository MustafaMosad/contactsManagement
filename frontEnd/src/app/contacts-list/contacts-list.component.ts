import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ContactDataService } from '../service/contact-data.service';
import { Router } from '@angular/router';
import { merge } from 'rxjs';
import { LoginService } from '../service/login.service';
import { DialogService } from '../service/dialog.service';

export class RetrieveContactsResponse{
  constructor(public contacts:Contact[] , public totalContacts:number){

  }
}
export class Contact {
  constructor(public firstName: string,
    public lastName: string,
    public email: string,
    public phone: string,
    public userEmail: string,
    public contactId: string) {

  }

}

class UserContactsForm {
  constructor(public email: string,
    public pageNumber: Number,
    public pageSize: Number,
    public sortBy: string,
    public sortDirection: string) {


  }

}

@Component({
  selector: 'app-contacts-list',
  templateUrl: './contacts-list.component.html',
  styleUrls: ['./contacts-list.component.css']
})

export class ContactsListComponent implements OnInit {
  displayedColumns: string[] = ['firstName', 'lastName', 'email', 'phone' , 'actions'];
  contacts = null;
  userContactsForm:UserContactsForm;
  totalContacts:number;
  message:string 
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;


  constructor(private router :Router ,private contactDataService: ContactDataService ,
    private loginService:LoginService , private dialogService: DialogService) {
    console.log('on construct')

  }

  ngOnInit() {
    this.setDafaults();
    this.refreshContacts();
        
   
    merge( this.sort.sortChange ,  this.paginator.page).subscribe(()=> {
      
      this.userContactsForm.pageNumber = this.paginator.pageIndex ;
      this.userContactsForm.pageSize = this.paginator.pageSize;

      if (this.sort.direction) {
          this.userContactsForm.sortBy = this.sort.active
          this.userContactsForm.sortDirection = this.sort.direction
      }else{
        this.userContactsForm.sortBy = 'id'
        this.userContactsForm.sortDirection = 'asc'
      }
      this.refreshContacts();

  });
    
  }
  setDafaults(){
    this.userContactsForm = new UserContactsForm(this.loginService.getAuthenticatedUser(),
    0,5,'id','asc');
  }

   handleCreateButton(){
     this.router.navigate(['createContact']);
   }
   handleUpdateButton(contact){
     console.log(contact.contactId);
     this.contactDataService.setContactForUpdate(contact);
    this.router.navigate(['updateContact']);

   }
  refreshContacts() {
    this.contactDataService.retrieveContacts(this.userContactsForm).subscribe(
      response => {
        console.log(response.totalContacts);
        if(!this.contacts){
        this.contacts = new MatTableDataSource(response.contacts);
        }else{
          this.contacts = response.contacts;
        }

        this.contacts.paginator = this.paginator;
        this.contacts.sort = this.sort;     
         this.totalContacts = response.totalContacts;
        console.log('inside refresh')
        console.log(this.paginator.length);
      }
    )
  }

  deleteContact(contactId) {
    console.log(`delete todo ${contactId}`)
    this.dialogService.openConfirmDialog('Are you sure to delete this record ?')
    .afterClosed().subscribe(res =>{
      if(res){
        this.contactDataService.deleteContact(contactId).subscribe(
          response => {
            console.log(response);
            this.message = `Contact Deleted Successfully!`;
            this.refreshContacts();
          }
        )
      }
    });
  
  }
}
