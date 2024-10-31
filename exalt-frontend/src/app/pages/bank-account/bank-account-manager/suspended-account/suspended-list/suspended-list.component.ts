import { Component, Input } from '@angular/core';
import { BankAccount } from 'src/app/shared/models/bank-account/bank-account.model';

@Component({
  selector: 'app-suspended-list',
  templateUrl: './suspended-list.component.html',
  styleUrls: ['./suspended-list.component.css']
})
export class SuspendedListComponent {

  @Input () suspendedAcounts!: BankAccount[];
  @Input () isLoading!: boolean;
  @Input () tableEmpty!: boolean;

  getAccountStateSeverity(state: string): string {
    switch (state) {
      case 'suspended':
        return 'danger';
      default:
        return "danger";
    }
  }

  getCustomerStateSeverity(state: string): string {
    switch (state) {
      case 'active':
        return 'success';
      case 'archive':
        return 'danger';
      default:
        return "danger";
    }
  }

  getAccountTypeSeverity(type: string): string {
    switch (type) {
      case 'saving':
        return 'primary'
      case 'current':
        return 'warning'
      default:
        return 'danger'
    }
  }

  getAccountBalanceSeverity(account: BankAccount): string {
    if (account.balance > 2000) {
      return 'success';
    }
    else if (account.balance <= 2000 && account.balance > 500) {
      return 'warning'
    }
    else return 'danger'
  }
}
