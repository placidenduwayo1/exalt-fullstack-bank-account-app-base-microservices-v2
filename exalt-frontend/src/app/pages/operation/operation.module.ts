import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OperationRoutingModule } from './operation-routing.module';
import { OperationManagerComponent } from './operation-manager/operation-manager.component';
import { OperationsListComponent } from './operation-manager/operations-list/operations-list.component';
import { TableModule } from 'primeng/table';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { MessagesModule } from 'primeng/messages';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { ConfirmationService, FilterService, MessageService } from 'primeng/api';
import { InputTextModule } from 'primeng/inputtext';
import { TransferListComponent } from './operation-manager/transfer-list/transfer-list.component';
import { TransfersComponent } from './operation-manager/transfer-list/transfers/transfers.component';
import { DepositOperationComponent } from './operation-manager/deposit-operation/deposit-operation.component';
import { WithdrawalOperationComponent } from './operation-manager/withdrawal-operation/withdrawal-operation.component';


@NgModule({
  declarations: [
    OperationManagerComponent,
    OperationsListComponent,
    TransferListComponent,
    TransfersComponent,
    DepositOperationComponent,
    WithdrawalOperationComponent
  ],
  imports: [
    CommonModule,
    OperationRoutingModule,
    TableModule,
    CardModule,
    TagModule,
    MessagesModule,
    ConfirmDialogModule,
    ReactiveFormsModule,
    DropdownModule,
    FormsModule,
    InputTextModule
  ],
  providers:[ConfirmationService, MessageService]
})
export class OperationModule { }
