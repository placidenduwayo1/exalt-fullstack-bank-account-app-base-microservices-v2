import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import { CustomerManagerComponent } from './customer-manager/customer-manager.component';
import { CustomersListComponent } from './customer-manager/customers-list/customers-list.component';
import { TableModule } from 'primeng/table';
import { SelectButtonModule } from 'primeng/selectbutton';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { PanelModule } from 'primeng/panel';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { SplitterModule } from 'primeng/splitter';
import { ConfirmationService, MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TooltipModule } from 'primeng/tooltip';
import { SidebarModule } from 'primeng/sidebar';
import { DropdownModule } from 'primeng/dropdown';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ArchivedCustomerComponent } from './customer-manager/archived-customer/archived-customer.component';



@NgModule({
  declarations: [
    CustomerManagerComponent,
    CustomersListComponent,
    ArchivedCustomerComponent
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    TableModule,
    SelectButtonModule,
    FormsModule,
    ReactiveFormsModule,
    CardModule,
    InputTextModule,
    PanelModule,
    ButtonModule,
    TagModule,
    SplitterModule,
    MessagesModule,
    ConfirmDialogModule,
    TooltipModule,
    SidebarModule,
    DropdownModule
    
  ],
  providers: [ConfirmationService, MessageService]
})
export class CustomerModule { }
