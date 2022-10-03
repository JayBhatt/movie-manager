import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from 'src/app/components/app/app.component';
import { ConfigComponent } from 'src/app/components/config/config.component';
import { UserComponent } from 'src/app/components/user/user.component';
import { RegistrationComponent } from 'src/app/components/registration/registration.component';
import { MovieListComponent } from 'src/app/components/movie-list/movie-list.component';
import { LoginComponent } from 'src/app/components/login/login.component';
import { FormsModule }   from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgxSpinnerModule } from "ngx-spinner";

@NgModule({
  declarations: [
    AppComponent,
    ConfigComponent,
    UserComponent,
    RegistrationComponent,
    MovieListComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    RouterModule,
    HttpClientModule,
    BrowserAnimationsModule,
    NgxSpinnerModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class AppModule { }
