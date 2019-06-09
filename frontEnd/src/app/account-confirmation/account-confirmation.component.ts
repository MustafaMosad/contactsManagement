import { Component, OnInit } from '@angular/core';
import { AccountConfirmationService } from '../service/account-confirmation.service';
import { ActivatedRoute } from "@angular/router";

 
@Component({
  selector: 'app-account-confirmation',
  templateUrl: './account-confirmation.component.html',
  styleUrls: ['./account-confirmation.component.css']
})
export class AccountConfirmationComponent implements OnInit {

  token:String ;
  isValidConfirmation:boolean = true;
  constructor(private accountConfirmationService:AccountConfirmationService ,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.token = this.route.snapshot.queryParamMap.get("token")
  this.route.queryParamMap.subscribe(queryParams => {
    this.token = queryParams.get("token")
  })
    console.log('hellooooo')
    console.log(this.token);
    this.accountConfirmationService.executeRegistrationConfirmation(this.token).subscribe(data => {
    },
    error => {
      this.isValidConfirmation = false;
      console.log('error')
    })
  }

}
