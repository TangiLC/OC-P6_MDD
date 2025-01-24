import { Routes } from '@angular/router';
import { UnauthGuard } from '../../guards/unauth.guard';

export const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'login',
        loadComponent: () => import('../login/login.component').then(m => m.LoginComponent)
      }
    ],
    canActivate: [UnauthGuard],
  },
];