import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { errorMsg, observableComplete, severity2, sticky, timeout } from 'src/app/const-variables';
import { Customer } from 'src/app/shared/models/customer/customer.model';

@Component({
  selector: 'app-archived-customer',
  templateUrl: './archived-customer.component.html',
  styleUrls: ['./archived-customer.component.css']
})
export class ArchivedCustomerComponent implements OnInit {
  archivedCustomers!: Customer[];
  activatedRoute = inject(ActivatedRoute);
  isLoading: boolean = true;
  msgService = inject(MessageService);
  tableIsmpty:boolean = false;

  ngOnInit(): void {
    setTimeout(() => {
      this.activatedRoute.data.subscribe({
        next: ({ allArchivedCustomers }) => {
          this.archivedCustomers = allArchivedCustomers;
          if(this.archivedCustomers.length==0){
            this.tableIsmpty = true
          }

          return this.archivedCustomers;
        },
        error: () => {
          this.msgService.add({
            key: 'key2',
            severity: severity2,
            detail: errorMsg,
            sticky: sticky
          });
          return null;
        }, complete: () => {
          console.log(observableComplete);
        }
      });
      this.isLoading = false;
    }, timeout);
  }

  getCustomerStateServerity(state: string): string {
    if (state == 'archive') {
      return 'danger'
    }
    else {
      return 'danger'
    }
  }
}
