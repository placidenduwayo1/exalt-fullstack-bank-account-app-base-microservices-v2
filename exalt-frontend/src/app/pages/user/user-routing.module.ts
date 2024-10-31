import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserManagerComponent } from './user-manager/user-manager.component';
import { getAllUsersResolve } from 'src/app/shared/services/resolves/route.resolve';

const routes: Routes = [
  {
    path:'', component: UserManagerComponent, resolve:{
      allUsers: getAllUsersResolve
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
