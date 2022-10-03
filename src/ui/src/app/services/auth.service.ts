import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Api } from '../models/api';
import { Router } from '@angular/router';
import { StorageService } from './storage.service';
import { User } from '../models/user';
import { UserService } from './user.service';
import { RegistrationForm } from '../models/registration-form';
import { LoginForm } from '../models/login-form';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  /**
   * @NOTE: Dependencies are created as instance variable to improve readability, angular creates these when injecting dependencies in constructor.
   */
  private readonly client: HttpClient;
  private readonly storageService: StorageService;
  private readonly userService: UserService;
  private readonly router: Router;
  public static readonly AUTH_ERROR: number = 401;
  public static readonly ADMIN_ROLE: string = 'admin';

  private options = {
    headers: Api.HEADERS
  }

  constructor(httpClient: HttpClient, storageService: StorageService, userService: UserService, routerInstance: Router) {
    this.client = httpClient;
    this.storageService = storageService;
    this.userService = userService;
    this.router = routerInstance;
  }

  public login(form: LoginForm): Observable<any> {
    const { username, password } = form;
    return this.client.post(
      Api.buildResource('login'),
      {
        username,
        password,
      },
      {headers: this.options.headers, observe: "response"},
    );
  }

  public register(form: RegistrationForm): Observable<any> {
    const { username, password, confirmPassword} = form;
    return this.client.post(
      Api.buildResource('register'),
      {
        email: username,
        password,
        confirmPassword,
      },
      this.options
    );
  }

  public logout(): void {
    this.storageService.remove(UserService.CONFIG_NAME);
    window.location.href = '/login';
  }

  public getUser(): User {
    return this.userService.getUser();
  }

  public hasLoggedIn(): boolean {
    const user: User = this.getUser();
    if(Object.keys(user).length === 0 || !user.authorization || !user.tokenExpiry) {
      return false;
    }
    const now = new Date().valueOf();
    const elapsedTime = Math.floor( now / (1000 * 60 * 60) );
    if (elapsedTime > user.tokenExpiry) {
      this.storageService.remove(UserService.CONFIG_NAME);
      return false;
    }
    return true;
  }

  public saveUser(user: User): void {
    this.userService.saveUser(user);
  }

  public getCurrentUser (): User {
    return this.getUser();
  }

}
