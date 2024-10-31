import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { errorMsg, observableComplete, severity2, sticky, timeout } from 'src/app/const-variables';


@Component({
  selector: 'app-transfer-list',
  templateUrl: './transfer-list.component.html',
  styleUrls: ['./transfer-list.component.css']
})
export class TransferListComponent implements OnInit {
  activatedRoute = inject(ActivatedRoute);
  transfers!: any[];
  msgService = inject(MessageService)
  isLoading: boolean = true;

  ngOnInit(): void {

    setTimeout(() => {
      this.activatedRoute.data.subscribe({
        next: ({ allTransfers }) => {
          this.transfers = allTransfers;
          return this.transfers;
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
    },
      timeout);
  }
}
