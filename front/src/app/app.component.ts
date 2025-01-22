import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter, Observable } from 'rxjs';
import { AuthService } from './pages/auth/services/auth.service';
import { UserInformation } from './interfaces/userInformation.interface';
import { NavbarComponent } from './pages/auth/components/navbar/navbar.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  isNavbarHidden = true;
  currentUrl: string = '';
  screenWidth: number = window.innerWidth;

  constructor(private router: Router, private authService: AuthService) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.currentUrl = event.url;
        this.updateNavbarVisibility();
      }
    });

    window.addEventListener('resize', () => {
      this.screenWidth = window.innerWidth;
      this.updateNavbarVisibility();
    });
  }

  private updateNavbarVisibility(): void {
    if (this.currentUrl === '/home') {
      this.isNavbarHidden = true;
    } else if (
      (this.currentUrl === '/login' || this.currentUrl === '/register') &&
      this.screenWidth < 758
    ) {
      this.isNavbarHidden = true;
    } else {
      this.isNavbarHidden = false;
    }
  }
}
