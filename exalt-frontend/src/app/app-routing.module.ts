import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SessionComponent } from './pages/session/session.component';
import { isTokenExpiredServiceGuard } from './shared/services/authentication/route-guard.service';
import { Accueil1Component } from './accueil/accueil1/accueil1.component';
import { Accueil2Component } from './accueil/accueil2/accueil2.component';
import { Accueil3Component } from './accueil/accueil3/accueil3.component';

const routes: Routes = [
  {
    path: 'index1',component: Accueil1Component
  },
  {
    path: 'index2',component: Accueil2Component
  },
  {
    path: 'index3',component: Accueil3Component
  },
  {
    path:'',redirectTo:'index1', pathMatch:'full'
  },
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'session', component: SessionComponent, canActivate: [isTokenExpiredServiceGuard],
    children: [
      {
        path: 'customers', loadChildren: () => import('./pages/customer/customer.module')
          .then(m => m.CustomerModule)
      }
      ,
      {
        path: 'accounts', loadChildren: () => import('./pages/bank-account/bank-account.module')
          .then(m => m.BankAccountModule)
      },
      {
        path: 'operations', loadChildren: () => import('./pages/operation/operation.module')
          .then(m => m.OperationModule)
      },
      {
        path: 'users', loadChildren: () => import('./pages/user/user.module')
          .then(m => m.UserModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
