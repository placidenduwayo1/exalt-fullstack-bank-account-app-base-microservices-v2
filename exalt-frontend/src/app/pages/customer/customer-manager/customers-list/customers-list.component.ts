import { Component, Input, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { acceptLabel, rejectLabel, confirmMsg, severity1, detailMsg1, severity2, detailMsg2, errorMsg, observableComplete, sticky, timeout }
  from 'src/app/const-variables';
import { BankAccount } from 'src/app/shared/models/bank-account/bank-account.model';
import { CustomerSwitchStateDto } from 'src/app/shared/models/customer/customer-switch-state-dto.model';
import { Customer } from 'src/app/shared/models/customer/customer.model';
import { Request } from 'src/app/shared/models/customer/request.model';
import { BankAccountEvent, CustomerEvent } from 'src/app/shared/models/event.model';
import { BankAccountService } from 'src/app/shared/services/bank-account/bank-account.service';
import { CustomerService } from 'src/app/shared/services/customer/customer.service';
import { CustomerEventService, BankAccountEventService } from 'src/app/shared/services/events/event.service';

interface BankAccountType {
  type: string;
}

@Component({
  selector: 'app-customers-list',
  templateUrl: './customers-list.component.html',
  styleUrls: ['./customers-list.component.css']
})
export class CustomersListComponent implements OnInit {
  @Input() customers!: Customer[];
  @Input() isLoading!: boolean;
  router = inject(Router);
  accountTypes: BankAccountType[] = [{ type: 'current' }, { type: 'saving' }];
  sidebarVisible1: boolean = false;
  sidebarVisible2: boolean = false;
  sidebarPosition: string = 'right';
  accountFormRequest!: FormGroup;
  fb = inject(FormBuilder);
  confirmSevice = inject(ConfirmationService)
  messageService = inject(MessageService);
  bankAccountService = inject(BankAccountService);
  customerService = inject(CustomerService);
  customerEventService = inject(CustomerEventService);
  bankAccountEventService = inject(BankAccountEventService);

  customerRequest!: FormGroup;

  private minLen: number = 2;
  private min: number = 1;
  isCreating: boolean = true;

  getCustomerStateServerity(state: string): string {
    if (state == 'archive') {
      return 'danger'
    }
    else if (state == 'active') {
      return 'success'
    }
    else {
      return 'danger'
    }
  }

  customerId!: string;

  ngOnInit(): void {
    this.accountFormRequest = this.fb.group({
      customer: [{ value: '', disabled: true }],
      type: ['', Validators.required],
      balance: ['', Validators.required]
    });

    //build customer form
    this.customerRequest = this.fb.group({
      customer: this.fb.group({
        firstname: ['', [Validators.required, Validators.minLength(this.minLen)]],
        lastname: ['', [Validators.required, Validators.minLength(this.minLen)]],
        email: ['', [Validators.required, Validators.minLength(4)]],
      }),
      address: this.fb.group({
        streetNum: ['', [Validators.required, Validators.min(this.min)]],
        streetName: ['', [Validators.required, Validators.minLength(this.minLen)]],
        poBox: ['', [Validators.required, Validators.min(this.min)]],
        city: ['', [Validators.required, Validators.minLength(this.minLen)]],
        country: ['', [Validators.required, Validators.minLength(this.minLen)]]
      })
    });

    this.bankAccountEventService.bankAccountEventObservable.subscribe({
      next: (event: BankAccountEvent) => {
        if (event === BankAccountEvent.BANK_ACCOUNT_CREATE) {

          this.confirmSevice.confirm({
            acceptLabel: acceptLabel,
            rejectLabel: rejectLabel,
            message: confirmMsg,

            accept: () => {
              let bankAccount: BankAccount = this.accountFormRequest.value;
              bankAccount.customerId = this.selectedCustomer.customerId;
              bankAccount.type = this.accountFormRequest.get('type')?.value.type;
              setTimeout(() => {
                this.bankAccountService.create(bankAccount).subscribe({
                  next: (data: BankAccount) => {
                    console.log(data);
                    this.messageService.add({
                      key: "key1",
                      severity: severity1,
                      detail: detailMsg1,
                      sticky: true
                    });
                    this.bankAccountEventService.publishEvent(BankAccountEvent.REFRESH);
                    return data;
                  },
                  error: () => {
                    this.messageService.add({
                      key: "key2",
                      severity: severity2,
                      detail: errorMsg,
                      sticky: true
                    });
                    return null;
                  },
                  complete: () => {
                    console.log(observableComplete);
                  }
                });
                this.isCreating = false;

              }, timeout);
            },
            reject: () => {
              this.messageService.add({
                key: "key2",
                severity: severity2,
                detail: detailMsg2,
                sticky: true
              });
            }
          });
        }
      }
    });

    this.customerEventService.customerEventObservable.subscribe({
      next: (event: CustomerEvent) => {
        switch (event) {
          case CustomerEvent.CUSTOMER_STATE_SWITCH:
            this.confirmSevice.confirm({
              acceptLabel: acceptLabel,
              rejectLabel: rejectLabel,
              message: confirmMsg,
              accept: () => {
                let switchStateDto: CustomerSwitchStateDto = new CustomerSwitchStateDto();
                switchStateDto.customerId = this.selectedCustomer.customerId;
                if (this.selectedCustomer.state == 'active') {
                  switchStateDto.state = 'archive';
                  this.customerService.switchState(switchStateDto).subscribe({
                    next: (value: Customer) => {
                      this.messageService.add({
                        key: 'key1',
                        severity: severity1,
                        detail: detailMsg1,
                        sticky: true
                      });
                      this.customerEventService.publishEvent(CustomerEvent.REFRESH);
                      return value;
                    }
                  });
                }
                else if (this.selectedCustomer.state == 'archive') {
                  switchStateDto.state = 'active';
                  this.customerService.switchState(switchStateDto).subscribe({
                    next: (value: Customer) => {
                      this.messageService.add({
                        key: 'key1',
                        severity: severity1,
                        detail: detailMsg1,
                        sticky: true
                      });
                      this.customerEventService.publishEvent(CustomerEvent.REFRESH);
                      return value;
                    }
                  });
                }
              },
              reject: () => {
                this.messageService.add({
                  key: 'key2',
                  severity: severity2,
                  detail: detailMsg2,
                  sticky: true
                });
                return null;
              }
            });
            break;

          case CustomerEvent.CUSTOMER_UPDATE:
            this.confirmSevice.confirm({
              acceptLabel: acceptLabel,
              rejectLabel: rejectLabel,
              message: confirmMsg,
              accept: () => {
                let request: Request = new Request();
                request.addressDto = this.customerRequest.value.address;
                request.customerDto = this.customerRequest.value.customer;
                this.customerService.updateCustomer(this.selectedCustomer.customerId, request).subscribe({
                  next: (value: Customer) => {
                    this.messageService.add({
                      key: 'key1',
                      severity: severity1,
                      detail: detailMsg1,
                      sticky: true
                    });
                    this.customerEventService.publishEvent(CustomerEvent.REFRESH);
                    return value;
                  },
                  error: () => {
                    this.messageService.add({
                      key: 'key2',
                      severity: severity2,
                      detail: errorMsg,
                      sticky: true
                    });
                    return null;
                  },
                  complete: () => {
                    console.log(observableComplete);
                  }
                })
              },
              reject: () => {
                this.messageService.add({
                  key: 'key2',
                  severity: severity2,
                  detail: detailMsg2,
                  sticky: true
                });
              }
            });
            break;
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
  }

  selectedCustomer!: Customer;

  loadCustomer(customer: Customer) {
    this.selectedCustomer = customer;
  }

  onCreateCustomerAccount() {
    this.bankAccountEventService.publishEvent(BankAccountEvent.BANK_ACCOUNT_CREATE);
  }

  onCustomerSwitchState(customer: Customer) {
    this.selectedCustomer = customer;
    this.customerEventService.publishEvent(CustomerEvent.CUSTOMER_STATE_SWITCH);
  }

  onUpdateCustomer() {
    this.customerEventService.publishEvent(CustomerEvent.CUSTOMER_UPDATE);
  }

  first: number = Number(localStorage.getItem('customerPage'))
  onTablePageChange($event: any) {
    this.first = $event.first;
    localStorage.setItem('customerPage', this.first.toString());
  }
}
