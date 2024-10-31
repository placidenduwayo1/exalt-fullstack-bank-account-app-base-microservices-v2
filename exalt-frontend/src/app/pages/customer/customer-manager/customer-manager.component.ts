import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { severity2, errorMsg, sticky, observableComplete, timeout } from 'src/app/const-variables';
import { Customer } from 'src/app/shared/models/customer/customer.model';
import { CustomerEvent } from 'src/app/shared/models/event.model';
import { CustomerService } from 'src/app/shared/services/customer/customer.service';
import { CustomerEventService } from 'src/app/shared/services/events/event.service';

@Component({
  selector: 'app-customer-manager',
  templateUrl: './customer-manager.component.html',
  styleUrls: ['./customer-manager.component.css']
})
export class CustomerManagerComponent implements OnInit {

  activatedRoute = inject(ActivatedRoute);
  customers: Customer[] = [];
  customerEventService = inject(CustomerEventService);
  customerService = inject(CustomerService);
  isLoading: boolean = true;
  msgService = inject(MessageService);

  ngOnInit(): void {

    setTimeout(() => {
      this.activatedRoute.data.subscribe({
        next: ({ allCustomers }) => {
          this.customers = allCustomers;
          console.log(this.customers);
        },
        error: (err) => {
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

    this.customerEventService.customerEventObservable.subscribe({
      next: (event) => {
        if (event == CustomerEvent.REFRESH) {
          this.customerService.getAll().subscribe({
            next: (data: Customer[]) => {
              this.customers = data;
            },
            error: (err) => {
              alert(`an error occured ${err}`);
            },
            complete: () => {
              console.log('obserable completed');
            }
          });
        }
      }
    });
  }

}
