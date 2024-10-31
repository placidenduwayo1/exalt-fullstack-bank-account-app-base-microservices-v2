import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { errorMsg, observableComplete, severity2, sticky, timeout } from 'src/app/const-variables';
import { Operation } from 'src/app/shared/models/operation/operation.model';

@Component({
  selector: 'app-deposit-operation',
  templateUrl: './deposit-operation.component.html',
  styleUrls: ['./deposit-operation.component.css']
})
export class DepositOperationComponent implements OnInit {
  route = inject(ActivatedRoute);
  deposits !: Operation[];
  msgService = inject(MessageService);
  isLoading: boolean = true;

  ngOnInit(): void {
    setTimeout(() => {
      this.route.data.subscribe({
        next: ({ depositOperations }) => {
          this.deposits = depositOperations;
          return this.deposits;
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
  }

  getOperationTypeSeverity(type: string): string {
    if (type == 'depot') {
      return 'success'
    }
    else {
      return 'danger'
    }
  }
}
