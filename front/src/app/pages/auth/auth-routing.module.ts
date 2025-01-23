import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
//import { LoginComponent } from './components/login/login.component';
//import { RegisterComponent } from './components/register/register.component';
import { UnauthGuard } from '../../guards/unauth.guard';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'login',
        loadChildren: () =>
          import('./components/login/login.module').then((m) => m.LoginModule),
      },
      //{ path: 'register', component: RegisterComponent },
    ],
    canActivate: [UnauthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuthRoutingModule {}
