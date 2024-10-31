import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { UserManagerComponent } from './user-manager/user-manager.component';
import { UsersListComponent } from './user-manager/users-list/users-list.component';
import { TableModule } from 'primeng/table';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { SidebarModule } from 'primeng/sidebar';
import { TooltipModule } from 'primeng/tooltip';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessagesModule } from 'primeng/messages';
import { ConfirmationService, MessageService } from 'primeng/api';


@NgModule({
  declarations: [
    UserManagerComponent,
    UsersListComponent
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    TableModule,
    CardModule,
    ButtonModule,
    SidebarModule,
    TooltipModule,
    FormsModule,
    ReactiveFormsModule,
    InputTextModule,
    ConfirmDialogModule,
    MessagesModule
  ],
  providers: [ConfirmationService, MessageService]
})
export class UserModule { }
