import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

    private readonly authService: AuthService;

    constructor(authService: AuthService) {
        this.authService = authService;
     }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
            if (err.status === AuthService.AUTH_ERROR) {
                this.authService.logout();
            }
            const error = err.error.message || err.statusText;
            return throwError(() => new Error(error));
        }));
    }
}