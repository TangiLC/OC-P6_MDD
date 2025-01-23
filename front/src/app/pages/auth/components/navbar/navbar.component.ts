import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { filter, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { NavigationEnd, Router } from '@angular/router';
import { shareReplay } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  isAuthenticated = false;
  currentUrl: string = '';
  isMenuOpen = false;
  isNavbarHidden = true;
  userPicture$: Observable<string> = this.authService.userInfo$.pipe(
    map((userInfo) =>
      userInfo?.picture
        ? `assets/profils/${userInfo.picture}.png`
        : 'assets/profils/default.png'
    )
  );

  constructor(public authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.isAuthenticated$.subscribe(
      (status) => (this.isAuthenticated = status)
    );

    this.router.events
      .pipe(
        filter(
          (event): event is NavigationEnd => event instanceof NavigationEnd
        )
      )
      .subscribe((event) => {
        this.currentUrl = event.url;
        this.isMenuOpen = false;
        this.updateNavbarVisibility();
      });

    this.updateNavbarVisibility();

    window.addEventListener('resize', () => this.updateNavbarVisibility());
  }

  private updateNavbarVisibility(): void {
    const screenWidth = window.innerWidth;

    if (this.currentUrl === '/home' || this.currentUrl === '/') {
      this.isNavbarHidden = true;
    } else if (
      (this.currentUrl === '/login' || this.currentUrl === '/register') &&
      screenWidth < 758
    ) {
      this.isNavbarHidden = true;
    } else {
      this.isNavbarHidden = false;
    }
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
}
