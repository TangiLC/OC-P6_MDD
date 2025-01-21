import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HttpClientModule } from '@angular/common/http';

import { AuthRoutingModule } from './auth-routing.module';
import { NavbarComponent } from './components/navbar/navbar.component';
//import { LoginComponent } from './components/login/login.component';
//import { RegisterComponent } from './components/register/register.component';

import { AuthService } from './services/auth.service';

@NgModule({
  declarations: [NavbarComponent],
  exports:[NavbarComponent],
  imports: [
    CommonModule,
    AuthRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatToolbarModule
  ],
  providers: [AuthService],
})
export class AuthModule { }
