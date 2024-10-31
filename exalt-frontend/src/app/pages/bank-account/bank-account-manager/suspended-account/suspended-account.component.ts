import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { errorMsg, observableComplete, severity2, sticky, timeout } from 'src/app/const-variables';
import { BankAccount } from 'src/app/shared/models/bank-account/bank-account.model';

@Component({
  selector: 'app-suspended-account',
  templateUrl: './suspended-account.component.html',
  styleUrls: ['./suspended-account.component.css']
})
export class SuspendedAccountComponent implements OnInit {
  activatedRoute = inject(ActivatedRoute);
  suspendedAcounts!: BankAccount[];
  messageService = inject(MessageService);
  isLoading: boolean = true;
  tableEmpty: boolean = false;

  ngOnInit(): void {
    setTimeout(() => {
      this.activatedRoute.data.subscribe({
        next: ({ allSuspendedAccounts }) => {
          this.suspendedAcounts = allSuspendedAccounts;
          if(this.suspendedAcounts.length==0){
            this.tableEmpty=true;
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
