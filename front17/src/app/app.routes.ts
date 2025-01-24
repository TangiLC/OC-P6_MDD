import { Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { UnauthGuard } from './guards/unauth.guard';
import { NotFoundComponent } from './pages/not-found/not-found.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  {
    path: 'home',
    loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent),
    canActivate: [UnauthGuard],
  },
  {
    path: 'auth',
    loadChildren: () => import('./pages/auth/auth.routes').then(m => m.routes),
    canActivate: [UnauthGuard],
  },
  /*{
    path: 'me',
    loadChildren: () => import('./pages/me/me.module').then((m) => m.MeModule),
    canActivate: [AuthGuard],
  },*/
  /*{
    path: 'articles',
    loadChildren: () =>
      import('./pages/articles/articles.module').then((m) => m.ArticlesModule),
    canActivate: [AuthGuard],
  },*/
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