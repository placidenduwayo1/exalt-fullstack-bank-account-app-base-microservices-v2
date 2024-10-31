import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { severity2, errorMsg, sticky, observableComplete, timeout } from 'src/app/const-variables';
import { BankAccount } from 'src/app/shared/models/bank-account/bank-account.model';

@Component({
  selector: 'app-risk-bank-account',
  templateUrl: './risk-bank-account.component.html',
  styleUrls: ['./risk-bank-account.component.css']
})
export class RiskBankAccountComponent implements OnInit {

  activatedRoute = inject(ActivatedRoute);
  riskAccounts!: BankAccount[];
  messageService = inject(MessageService);
  isLoading: boolean = true;
  tableEmpty: boolean = false;
  ngOnInit(): void {
    setTimeout(() => {
      this.activatedRoute.data.subscribe({
        next: ({ allRiskAccounts }) => {
          this.riskAccounts = allRiskAccounts;
          if (this.riskAccounts.length == 0) {
            this.tableEmpty = true;
          }
        },
        error: () => {
          this.messageService.add({
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
    }, timeout)
  }

}
