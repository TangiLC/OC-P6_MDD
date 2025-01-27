// theme-detail.component.ts
import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { ArticleService } from '../../services/article.service';
import { map, Observable, switchMap, tap } from 'rxjs';
import { Theme } from '../../interfaces/theme.interface';
import { UserInformation } from '../../interfaces/userInformation.interface';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-theme-detail',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatDividerModule,
    MatIconModule,
    MatButtonModule,
  ],
  templateUrl: './theme-detail.component.html',
  styleUrls: ['./theme-detail.component.scss'],
})
export class ThemeDetailComponent implements OnInit {
  @Input() theme: Theme | null = null;
  @Input() isFullView: Boolean | undefined;
  @Output() themeRefresh = new EventEmitter<void>();

  isFollowing: boolean = false;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private snackBar: MatSnackBar
  ) {}
  ngOnInit(): void {
    this.authService.userInfo$.subscribe((userInfo: UserInformation | null) => {
      if (userInfo && this.theme) {
        this.isFollowing = userInfo.themesSet.includes(this.theme.id);
      }
    });
  }

  followTheme(): void {
    if (this.theme) {
      this.userService
        .addThemeToUser(this.theme.id)
        .pipe(switchMap(() => this.userService.getUserInfo()))
        .subscribe({
          next: (userInfo) => {
            this.snackBar.open('Thème suivi avec succès !', '', {
              duration: 1500,
            });
            this.authService.setUserInfo(userInfo);
            this.themeRefresh.emit();
          },
          error: (error) => {
            this.snackBar.open('Erreur dans le suivi du thème !', '', {
              duration: 1500,
            });
            console.error('Error following theme:', error);
          },
        });
    }
  }

  unfollowTheme(): void {
    if (this.theme) {
      this.userService
        .removeThemeFromUser(this.theme.id)
        .pipe(switchMap(() => this.userService.getUserInfo()))
        .subscribe({
          next: (userInfo) => {
            this.snackBar.open('Thème désabonné avec succès !', '', {
              duration: 1500,
            });
            this.authService.setUserInfo(userInfo);
            this.themeRefresh.emit();
          },
          error: (error) => {
            this.snackBar.open('Erreur dans le suivi du thème !', '', {
              duration: 1500,
            });
            console.error('Error following theme:', error);
          },
        });
    }
  }
}
