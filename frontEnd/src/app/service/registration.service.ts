import { API_URL, registration_Api } from './../app.constants';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {map} from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

 constructor(private http: HttpClient) { }

  executeRegistrationForm(email , password , confirmPassword){
    return this.http.post<any>(
      `${API_URL}/${registration_Api}`,{
        email,
        password,
        confirmPassword
      }).pipe(
        map(
          data => {
            return data;
          }
        )
      );
  }
}
