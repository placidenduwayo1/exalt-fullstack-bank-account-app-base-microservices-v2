import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerManagerComponent } from './customer-manager/customer-manager.component';
import { getAllArchivedCustomerResolve, getAllCustomersResolve } from 'src/app/shared/services/resolves/route.resolve';
import { ArchivedCustomerComponent } from './customer-manager/archived-customer/archived-customer.component';

const routes: Routes = [
  {
    path:'',component: CustomerManagerComponent,
    resolve:{
      allCustomers: getAllCustomersResolve
    }
  },
  {
    path:'archived', component: ArchivedCustomerComponent, resolve:{
      allArchivedCustomers: getAllArchivedCustomerResolve
    }
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
