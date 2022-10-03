import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConfigComponent } from '../components/config/config.component';
import { LoginComponent } from '../components/login/login.component';
import { MovieListComponent } from '../components/movie-list/movie-list.component';
import { RegistrationComponent } from '../components/registration/registration.component';
import { Authorisation } from '../guards/authorisation.guard';

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegistrationComponent},
  {path: 'movie-list', component: MovieListComponent, canActivate:[Authorisation]},
  {path: 'config', component: ConfigComponent, canActivate:[Authorisation], data: {
    role: 'ADMIN'
  }}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
