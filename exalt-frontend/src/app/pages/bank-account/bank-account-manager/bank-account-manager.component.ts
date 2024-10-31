import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BankAccount } from 'src/app/shared/models/bank-account/bank-account.model';
import { BankAccountEvent } from 'src/app/shared/models/event.model';
import { BankAccountService } from 'src/app/shared/services/bank-account/bank-account.service';
import { BankAccountEventService } from 'src/app/shared/services/events/event.service';
import { errorMsg, observableComplete, severity2, sticky, timeout } from '../../../const-variables';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-bank-account-manager',
  templateUrl: './bank-account-manager.component.html',
  styleUrls: ['./bank-account-manager.component.css']
})
export class BankAccountManagerComponent implements OnInit {
  accounts!: BankAccount[];
  activatedRoute = inject(ActivatedRoute);
  bankAccountEventService = inject(BankAccountEventService);
  bankAccountService = inject(BankAccountService);
  msgService = inject(MessageService);
  isLoading: boolean = true;

  ngOnInit(): void {
    setTimeout(() => {
      this.activatedRoute.data.subscribe({
        next: ({ allAccounts }) => {
          this.accounts = allAccounts;
          return this.accounts;
        },
        error: () => {
          this.msgService.add({
            key: 'key2',
            severity: severity2,
            detail: errorMsg,
            sticky: sticky
          });
          return null;
        },
        complete: () => {
          console.log(observableComplete);
        }
      });
      this.isLoading = false;
    }, timeout);

    this.bankAccountEventService.bankAccountEventObservable.subscribe((event: BankAccountEvent) => {
      if (event === BankAccountEvent.REFRESH) {
        this.bankAccountService.getAll().subscribe({
          next: (value: BankAccount[]) => {
            this.accounts = value;
          }
        })
      }
    });
  }

}
