import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { errorMsg, observableComplete, severity2, sticky, timeout } from 'src/app/const-variables';
import { Operation } from 'src/app/shared/models/operation/operation.model';

@Component({
  selector: 'app-withdrawal-operation',
  templateUrl: './withdrawal-operation.component.html',
  styleUrls: ['./withdrawal-operation.component.css']
})
export class WithdrawalOperationComponent implements OnInit {
  route = inject(ActivatedRoute);
  widrawals !: Operation[];
  msgService = inject(MessageService);
  isLoading: boolean = true;

  ngOnInit(): void {
    setTimeout(() => {
      this.route.data.subscribe({
        next: ({ withdrawalOperations }) => {
          this.widrawals = withdrawalOperations;
          return this.widrawals;
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
    if (type == 'retrait') {
      return 'warning'
    }
    else {
      return 'danger'
    }
  }
}
