import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BankAccountManagerComponent } from './bank-account-manager/bank-account-manager.component';
import { getAllBankAccountsResolve, getAllSuspendedAccountsResolve, getAllRiskAccountsResolve }
  from 'src/app/shared/services/resolves/route.resolve';
import { SuspendedAccountComponent } from './bank-account-manager/suspended-account/suspended-account.component';
import { RiskBankAccountComponent } from './bank-account-manager/risk-bank-account/risk-bank-account.component';

const routes: Routes = [
  {
    path: '', component: BankAccountManagerComponent,
    resolve: {
      allAccounts: getAllBankAccountsResolve
    }
  },
  {
    path: 'suspended', component: SuspendedAccountComponent, resolve: {
      allSuspendedAccounts: getAllSuspendedAccountsResolve
    }
  },
  {
    path: 'to-risk', component: RiskBankAccountComponent, resolve: {
      allRiskAccounts: getAllRiskAccountsResolve
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BankAccountRoutingModule { }
