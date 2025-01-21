import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { UnauthGuard } from './guards/unauth.guard';
import { NotFoundComponent } from './pages/auth/components/not-found/not-found.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  /*{
    path: 'home',
    loadChildren: () =>
      import('./pages/auth/components/home/home.module').then((m) => m.HomeModule),
    canActivate: [UnauthGuard],
  },*/
  {
    path: 'auth',
    loadChildren: () =>
      import('./pages/auth/auth.module').then((m) => m.AuthModule),
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

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
