import { Component, OnInit } from '@angular/core';
import { LoginForm } from 'src/app/models/login-form';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/models/user';
import { SpinnerService } from 'src/app/services/spinner.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  protected readonly loginForm: LoginForm = {
      username: '',
      password: ''
  }
  private readonly authService: AuthService;
  private readonly spinner: SpinnerService;
  public hasLoggedIn: boolean = false;
  public loginError: boolean = false;
  public error: string = '';

  constructor(auth: AuthService, spinner: SpinnerService) {
    this.authService = auth;
    this.spinner = spinner;
  }

  ngOnInit(): void {
    if(this.authService.hasLoggedIn()) {
      /*
       * Chrome cache sometime prevents from componets from loading porperly. This ensure that after login we start fresh.
       * @TODO: Use router to redirect in production.
       */
      window.location.href = '/movie-list';
    }
  }

  public onSubmit(form: NgForm): void {
    this.spinner.show();
    this.authService.login(this.loginForm).subscribe({
      next: response => {
        const user = response.body as User;
        user.authorization = response.headers.get('Authorization');
        this.authService.saveUser(user);
        this.loginError = false;
        this.hasLoggedIn = true;
        this.spinner.hide();
        window.location.href = '/movie-list';
      },
      error: err => {
        this.error = (err.error && err.error.status && err.error.status === 'UNAUTHORIZED') ? 'Invalid username or password' : err.error.message;
        this.loginError = true;
        this.spinner.hide();
        form.resetForm();
      }
    });
  };

}
