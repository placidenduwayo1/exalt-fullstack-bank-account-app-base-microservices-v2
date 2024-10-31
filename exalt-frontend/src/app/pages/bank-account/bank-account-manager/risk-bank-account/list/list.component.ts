import { Component, Input } from '@angular/core';
import { BankAccount } from 'src/app/shared/models/bank-account/bank-account.model';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent {

  @Input () riskAccounts!: BankAccount[];
  @Input () isLoading!: boolean;
  @Input () tableEmpty!: boolean;

  getAccountStateSeverity(state: string): string {
    switch (state) {
      case 'active':
        return 'success';
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
