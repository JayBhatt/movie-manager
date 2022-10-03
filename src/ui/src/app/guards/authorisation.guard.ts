import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
    providedIn: 'root'
})
export class Authorisation implements CanActivate {

    private readonly authService: AuthService;

    constructor(authService: AuthService) {
        this.authService = authService;
     }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const user = this.authService.hasLoggedIn();
        if (route.data && route.data['role']) {
            const currentUser = this.authService.getCurrentUser();
            if (!currentUser || !currentUser.role) {
                this.authService.logout();
                return false;
            }
            if (currentUser.role.toLocaleLowerCase() !== route.data['role'].toLocaleLowerCase()) {
                this.authService.logout();
                return false;
            }
            return true;
        }
        if (user) {
            return true;
        }
        this.authService.logout();
        return false;
    }
}