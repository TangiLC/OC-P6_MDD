import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './pages/auth/services/auth.service';
import { UserInformation } from './interfaces/userInformation.interface';
import { NavbarComponent } from './pages/auth/components/navbar/navbar.component';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(
    private router: Router,
    private authService: AuthService) {
  }

}
