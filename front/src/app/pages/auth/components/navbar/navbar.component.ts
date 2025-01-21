import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  isAuthenticated = false;
  currentUrl: string = '';

  userPicture$ = this.authService.userInfo$.pipe(
    map(
      (userInfo) =>
        //`${environment.apiBaseUrl}/assets/${userInfo?.picture}.png` ||
        'assets/default-avatar.png'
    )
  );

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.isAuthenticated$.subscribe(
      (status) => (this.isAuthenticated = status)
    );

    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.currentUrl = event.url;
      }
    });
  }
}
