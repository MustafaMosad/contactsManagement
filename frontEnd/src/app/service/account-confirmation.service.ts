import { API_URL, account_confirmation_Api } from './../app.constants';
import { Injectable } from '@angular/core';
import {HttpParams, HttpHeaders, HttpClient } from '@angular/common/http';
import {map} from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class AccountConfirmationService {

  constructor(private http: HttpClient) { }


  executeRegistrationConfirmation(token){

      return this.http.post<any>(
        `${API_URL}/${account_confirmation_Api}`,{
          token
        }).pipe(
          map(
            data => {
              return data;
            }
          )
        );
  }
}
