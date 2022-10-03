import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { User } from '../models/user';

@Injectable()
export class RequestInterceptor implements HttpInterceptor {

    private readonly authService: AuthService;

    constructor(authService: AuthService) {
        this.authService = authService;
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const user: User = this.authService.getUser();
        if (user && user.authorization) {
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${user.authorization}`
                }
            });
        }
        return next.handle(request);
    }
}
