import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OperationManagerComponent } from './operation-manager/operation-manager.component';
import { getAllDepositOperationResolve, getAllOperationsResolve, getAllTransferResolve, getAllWithdrawalsOperationResolve } from 'src/app/shared/services/resolves/route.resolve';
import { TransferListComponent } from './operation-manager/transfer-list/transfer-list.component';
import { DepositOperationComponent } from './operation-manager/deposit-operation/deposit-operation.component';
import { WithdrawalOperationComponent } from './operation-manager/withdrawal-operation/withdrawal-operation.component';

const routes: Routes = [
  {
    path: '', component: OperationManagerComponent,
    resolve: {
      allOperations: getAllOperationsResolve
    }
  },
  {
    path: 'transfers', component: TransferListComponent, resolve:{
      allTransfers: getAllTransferResolve
    }
  },
  {
    path:'deposit', component: DepositOperationComponent, resolve:{
      depositOperations: getAllDepositOperationResolve
    }
  },
  {
    path: 'withdrawal', component: WithdrawalOperationComponent,resolve:{
      withdrawalOperations: getAllWithdrawalsOperationResolve
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OperationRoutingModule { }
