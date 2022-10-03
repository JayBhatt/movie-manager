import { Component } from '@angular/core';
import { NavigationEnd, NavigationStart, Route, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  
  protected title:string = 'Movie Manager';

  private readonly authService: AuthService;
  public welcomeMessage: string | undefined = "";
  public hasLoggedIn: boolean = false;
  public isAdmin: boolean = false;
  public hideBranding: boolean = false;
  private readonly router: Router;
  public route: string = '';
  
  constructor(authService: AuthService, router: Router) {
    this.authService = authService;
    this.router = router;
    this.hasLoggedIn = this.authService.hasLoggedIn();
    this.hideBranding = this.hasLoggedIn;
    const user = authService.getCurrentUser();
    this.welcomeMessage = `Welcome <b>${user.email}</b>!!!`;
    this.isAdmin = user.role && user.role.toLowerCase() === AuthService.ADMIN_ROLE.toLowerCase() ? true : false;
}

ngOnInit(): void {
  this.router.events.subscribe(event => {
    if(event instanceof NavigationStart){
        this.route = event.url;
      }
    } 
  );
}

  logout(): void {
    this.authService.logout();
  }

}
