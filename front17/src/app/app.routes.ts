import { Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { UnauthGuard } from './guards/unauth.guard';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { ThemesGuard } from './guards/themes.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  {
    path: 'home',
    loadComponent: () =>
      import('./pages/home/home.component').then((m) => m.HomeComponent),
    canActivate: [UnauthGuard],
  },
  {
    path: 'auth',
    loadChildren: () =>
      import('./pages/auth/auth.routes').then((m) => m.routes),
    canActivate: [UnauthGuard],
  },
  {
    path: 'profil',
    loadComponent: () =>
      import('./pages/user-profile/user-profile.component').then(
        (m) => m.UserProfile
      ),
    canActivate: [AuthGuard, ThemesGuard],
  },
  {
    path: 'article/:id',
    loadComponent: () =>
      import('./pages/article/article.component').then(
        (m) => m.ArticleComponent
      ),
    canActivate: [AuthGuard, ThemesGuard],
  },
  {
    path: 'theme/:id',
    loadComponent: () =>
      import('./pages/theme/theme.component').then((m) => m.ThemeComponent),
    canActivate: [AuthGuard, ThemesGuard],
  },
  /*{
    path: 'themes',
    loadChildren: () =>
      import('./pages/themes/themes.module').then((m) => m.ThemesModule),
    canActivate: [AuthGuard],
  },*/
  {
    path: '**',
    component: NotFoundComponent,
  },
];
