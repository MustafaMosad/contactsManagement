import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../service/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = ''
  password = ''
  errorMessage = ''
  invalidLogin = false

  //Router
  //Angular.giveMeRouter
  //Dependency Injection
  constructor(private router: Router,
    private loginService: LoginService) { }
  ngOnInit() {
    if(this.loginService.isUserLoggedIn){
      this.router.navigate(['home']);
    }
  }
  handleJWTAuthLogin() {
    this.loginService.executeJWTAuthenticationService(this.username, this.password)
        .subscribe(
          data => {
            console.log(data)
            this.router.navigate(['home'])
            this.invalidLogin = false      
          },
          error => {
            console.log(error.status)
            if(error.status === 401){
              this.errorMessage = 'Wrong email or password'
            }else if(error.status === 403){
              this.errorMessage = 'Your email not verfied yet'
            } else {
              this.errorMessage = 'Unexpected Error !'

            }
            this.invalidLogin = true
          }
        )
  }
}
