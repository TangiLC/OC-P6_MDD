import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { ThemeDetailComponent } from '../theme-detail/theme-detail.component';
import { Theme } from '../../interfaces/theme.interface';
import { ThemesService } from '../../services/theme.service';
import { AuthService } from '../../services/auth.service';
import { Observable, combineLatest, map } from 'rxjs';

@Component({
  selector: 'app-themes-list',
  standalone: true,
  imports: [CommonModule, MatCardModule, ThemeDetailComponent],
  templateUrl: './themes-list.component.html',
  styleUrl: './themes-list.component.scss',
})
export class ThemesListComponent implements OnInit {
  @Input() isFullList: boolean = false;
  @Input() searchQuery: string = '';

  themes$: Observable<Theme[]>;

  constructor(
    private themesService: ThemesService,
    private authService: AuthService
  ) {
    this.themes$ = new Observable<Theme[]>();
  }

  ngOnInit(): void {
    this.refreshThemes();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['searchQuery']) {
      this.refreshThemes();
    }
  }

  private filterThemesByTitle(themes: Theme[]): Theme[] {
    if (!this.searchQuery) return themes;
    return themes.filter((theme) =>
      theme.title.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
  }

  refreshThemes(): void {
    if (this.isFullList) {
      this.themes$ = this.themesService.themes$.pipe(
        map((themes) => this.filterThemesByTitle(themes))
      );
    } else {
      this.themes$ = combineLatest([
        this.themesService.themes$,
        this.authService.userInfo$,
      ]).pipe(
        map(([themes, userInfo]) => {
          return userInfo
            ? themes.filter((theme) => userInfo.themesSet.includes(theme.id))
            : [];
        })
      );
    }
  }
}
