import { TestBed } from '@angular/core/testing';

import { HttpInterceptorAuthenticationService } from './http-interceptor-authentication.service';

describe('HttpInterceptorAuthenticationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HttpInterceptorAuthenticationService = TestBed.get(HttpInterceptorAuthenticationService);
    expect(service).toBeTruthy();
  });
});
