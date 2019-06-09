import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { LoginService } from './login.service';


@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorAuthenticationService implements HttpInterceptor{

  constructor(
    private loginService: LoginService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler){
    console.log('inerceptor')
    let authorizationToken = this.loginService.getAuthenticatedToken();

    if(authorizationToken) { 
      request = request.clone({
        setHeaders : {
            Authorization : authorizationToken
          }
        }) 
    }
    return next.handle(request);
  }


}