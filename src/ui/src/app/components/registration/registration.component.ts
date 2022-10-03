import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ErrorMessage } from 'src/app/models/error-message';
import { RegistrationForm } from 'src/app/models/registration-form';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';
import { SpinnerService } from 'src/app/services/spinner.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  protected readonly registrationForm: RegistrationForm = {
      username: '',
      password: '',
      confirmPassword: ''
  }
  private readonly authService: AuthService;
  private readonly spinner: SpinnerService;
  public registrationError: boolean = false;
  public error: string = '';
  public registrationSuccess: boolean = false;

  constructor(auth: AuthService, spinner: SpinnerService) {
    this.authService = auth;
    this.spinner = spinner;
  }

  ngOnInit(): void {
  }

  public onSubmit(form: NgForm): void {
    this.spinner.show();
    this.authService.register(this.registrationForm).subscribe({
      next: response => {
        const user = response.body as User;
        this.spinner.hide();
        this.registrationError = false;
        this.registrationSuccess = true;
        this.error = '';
        form.resetForm();
      },
      error: err => {
        this.error = this.parseErrors(err);
        this.registrationError = true;
        this.spinner.hide();
      }
    });
  };

  public parseErrors(err: any): string {
    let message = '';
    const errors = new Set<string>();
    if ((err.error && err.error.status && err.error.status === 'BAD_REQUEST')) {
      if (err.error.message && err.error.message.length > 0) {
        err.error.message.forEach((m: ErrorMessage) => {
          if (m.message) {
            errors.add(m.message);
          }
        });
      }
    } else {
        message = err.error.message ? `Error: ${err.error.message}` : '';
    }
    if (errors.size > 0) {
        message = 'Error: Following error(s) occurred: <ul>';
        [...errors].map(v => {
          message += `<li>${v}</li>`;
          return v;
        });
        message += '</ul>';
    }
    return message;
  }

}
