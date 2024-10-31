import { Component, Input, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { acceptLabel, confirmMsg, detailMsg1, detailMsg2, errorMsg, observableComplete, rejectLabel, severity1, severity2, sticky, timeout }
  from 'src/app/const-variables';
import { BankAccountInterestRateDto } from 'src/app/shared/models/bank-account/account-interest-rate-dto.model';
import { BankAccountOverdraftDto } from 'src/app/shared/models/bank-account/account-overdraft-dto.model';
import { AccountSwitchStateDto } from 'src/app/shared/models/bank-account/account-swithstate-dto.model';
import { BankAccount } from 'src/app/shared/models/bank-account/bank-account.model';
import { BankAccountEvent } from 'src/app/shared/models/event.model';
import { Operation } from 'src/app/shared/models/operation/operation.model';
import { TransferDto } from 'src/app/shared/models/operation/transfer-dto.model';
import { BankAccountService } from 'src/app/shared/services/bank-account/bank-account.service';
import { BankAccountEventService, OperationEventService } from 'src/app/shared/services/events/event.service';
import { OperationService } from 'src/app/shared/services/operation/operation.service';

interface OperationType {
  type: string;
}


@Component({
  selector: 'app-accounts-list',
  templateUrl: './accounts-list.component.html',
  styleUrls: ['./accounts-list.component.css']
})
export class AccountsListComponent implements OnInit {

  @Input() accountsList: BankAccount[] = [];
  @Input() isLoading!: boolean;
  router = inject(Router);
  bankAccountService = inject(BankAccountService);
  confirmService = inject(ConfirmationService);
  messageService = inject(MessageService);
  bankAccountEventService = inject(BankAccountEventService);
  operationEventService = inject(OperationEventService);

  operationTypes: OperationType[] = [{ type: 'depot' }, { type: 'retrait' }];
  operationService = inject(OperationService);


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

  fb = inject(FormBuilder);
  operationFormRequest!: FormGroup;
  overdraftForm!: FormGroup;
  iRateForm!: FormGroup;
  transferFormRequest!: FormGroup;
  sidebarVisible1: boolean = false;
  sidebarVisible2: boolean = false;
  sidebarVisible3: boolean = false;
  sidebarVisible4: boolean = false;
  isCreating: boolean = true;

  ngOnInit(): void {
    this.operationFormRequest = this.fb.group({
      account: [{ value: '', disabled: true }],
      type: ['', Validators.required],
      mount: ['', Validators.required],
    });
    this.overdraftForm = this.fb.group({
      account: [{ value: '', disabled: true }],
      overdraft: [, Validators.required]
    });

    this.iRateForm = this.fb.group({
      account: [{ value: '', disabled: true }],
      interestRate: ['', Validators.required]
    });

    this.transferFormRequest = this.fb.group({
      origin: [{ value: '', disabled: true }],
      destination: ['', Validators.required],
      mount: ['', Validators.required]
    });

    this.bankAccountEventService.bankAccountEventObservable.subscribe({
      next: (event: BankAccountEvent) => {
        switch (event) {
          case BankAccountEvent.BANK_ACCOUNT_OPERATION_CREATE:
            this.confirmService.confirm({
              acceptLabel: acceptLabel,
              rejectLabel: rejectLabel,
              message: confirmMsg,
              accept: () => {
                let operation: Operation = this.operationFormRequest.value
                operation.accountId = this.selectedAccount.accountId;
                operation.type = this.operationFormRequest.get('type')?.value.type;
                setTimeout(() => {
                  this.operationService.create(operation).subscribe({
                    next: (value: Operation) => {
                      this.messageService.add({
                        key: 'key1',
                        severity: severity1,
                        detail: detailMsg1,
                        sticky: sticky
                      });
                      this.bankAccountEventService.publishEvent(BankAccountEvent.REFRESH);
                      return value;
                    },
                    error: () => {
                      this.messageService.add({
                        key: "key2",
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
                  this.isCreating = false;
                }, timeout);
              },
              reject: () => {
                this.messageService.add({
                  key: 'key2',
                  severity: severity2,
                  detail: detailMsg2,
                  sticky: sticky
                });
                return null;
              }
            });
            break;

          case BankAccountEvent.BANK_ACCOUNT_SWITCH_STATE:
            let accountSwitchStateDto = new AccountSwitchStateDto();
            accountSwitchStateDto.accountId = this.selectedAccount.accountId;

            this.confirmService.confirm({
              acceptLabel: acceptLabel,
              rejectLabel: rejectLabel,
              message: confirmMsg,
              accept: () => {
                if (this.selectedAccount.state === 'active') {
                  accountSwitchStateDto.state = 'suspended';
                  this.bankAccountService.switchState(accountSwitchStateDto).subscribe({
                    next: (value: BankAccount) => {
                      this.messageService.add({
                        key: "key1",
                        severity: severity1,
                        detail: detailMsg1,
                        sticky: sticky
                      });
                      this.bankAccountEventService.publishEvent(BankAccountEvent.REFRESH);
                      return value;
                    },
                    error: () => {
                      this.messageService.add({
                        key: "key2",
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
                else if (this.selectedAccount.state == "suspended") {
                  accountSwitchStateDto.state = 'active';
                  this.bankAccountService.switchState(accountSwitchStateDto).subscribe({
                    next: (value: BankAccount) => {
                      this.messageService.add({
                        key: "key1",
                        severity: severity1,
                        detail: detailMsg1,
                        sticky: sticky
                      });
                      this.bankAccountEventService.publishEvent(BankAccountEvent.REFRESH);
                      return value;
                    },
                    error: () => {
                      this.messageService.add({
                        key: "key2",
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
              },
              reject: () => {

                this.messageService.add({
                  key: "key2",
                  severity: severity2,
                  detail: detailMsg2,
                  sticky: sticky
                });

              }
            });
            break;

          case BankAccountEvent.BANK_ACCOUNT_CHANGE_OVERDRAFT:

            this.confirmService.confirm({
              acceptLabel: acceptLabel,
              rejectLabel: rejectLabel,
              message: confirmMsg,
              accept: () => {
                let accountOverdraftDto = new BankAccountOverdraftDto();
                accountOverdraftDto.accountId = this.selectedAccount.accountId;
                accountOverdraftDto.overdraft = this.overdraftForm.value.overdraft;
                this.bankAccountService.changeOverdraft(accountOverdraftDto).subscribe({
                  next: (value: BankAccount) => {
                    this.messageService.add({
                      key: "key1",
                      severity: severity1,
                      detail: detailMsg1,
                      sticky: sticky
                    });
                    this.bankAccountEventService.publishEvent(BankAccountEvent.REFRESH);
                    return value;
                  },
                  error: () => {
                    this.messageService.add({
                      key: "key2",
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
              },
              reject: () => {
                this.messageService.add({
                  key: "key2",
                  severity: severity2,
                  detail: detailMsg2,
                  sticky: true
                });
                return null;
              }
            });
            break;

          case BankAccountEvent.BANK_ACCOUNT_CHANGE_INTEREST_RATE:

            this.confirmService.confirm({
              acceptLabel: acceptLabel,
              rejectLabel: rejectLabel,
              message: confirmMsg,
              accept: () => {
                let iRateDto = new BankAccountInterestRateDto();
                iRateDto.accountId = this.selectedAccount.accountId;
                iRateDto.interestRate = this.iRateForm.value.interestRate;
                this.bankAccountService.changeInterestate(iRateDto).subscribe({
                  next: (value: BankAccount) => {
                    this.messageService.add({
                      key: "key1",
                      severity: severity1,
                      detail: detailMsg1,
                      sticky: true
                    });
                    this.bankAccountEventService.publishEvent(BankAccountEvent.REFRESH);
                    return value;
                  },
                  error: () => {
                    this.messageService.add({
                      key: "key2",
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
              },
              reject: () => {
                this.messageService.add({
                  key: "key2",
                  severity: severity2,
                  detail: detailMsg2,
                  sticky: true
                });
                return sticky;
              }
            });
            break;

          case BankAccountEvent.BANK_ACCOUNT_TRANSFER_OPERATION:

            this.confirmService.confirm({
              acceptLabel: acceptLabel,
              rejectLabel: rejectLabel,
              message: confirmMsg,
              accept: () => {
                let transferDto: TransferDto = this.transferFormRequest.getRawValue();
                setTimeout(() => {
                  this.operationService.createTransfer(transferDto).subscribe({
                    next: (value: Map<string, BankAccount>) => {
                      this.messageService.add({
                        key: "key1",
                        severity: severity1,
                        detail: detailMsg1,
                        sticky: sticky
                      });

                      this.bankAccountEventService.publishEvent(BankAccountEvent.REFRESH);
                      return value;
                    },
                    error: () => {
                      this.messageService.add({
                        key: "key2",
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
                }, timeout);
              },
              reject: () => {
                this.messageService.add({
                  key: "key2",
                  severity: severity2,
                  detail: detailMsg2,
                  sticky: sticky
                });
                return null;
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
      }, complete: () => {
        console.log(observableComplete);
      }
    });
  }

  selectedAccount!: BankAccount;
  loadAccount(account: BankAccount) {
    this.selectedAccount = account;
    console.log(this.selectedAccount);
  }

  onCreateNewOperation() {
    this.bankAccountEventService.publishEvent(BankAccountEvent.BANK_ACCOUNT_OPERATION_CREATE);
  }
  onSwitchStateAccount(account: BankAccount) {
    this.selectedAccount = account;
    this.bankAccountEventService.publishEvent(BankAccountEvent.BANK_ACCOUNT_SWITCH_STATE);
  }

  changeAccountOverdraft() {
    this.bankAccountEventService.publishEvent(BankAccountEvent.BANK_ACCOUNT_CHANGE_OVERDRAFT);
  }

  changeAccountInterestRate() {
    this.bankAccountEventService.publishEvent(BankAccountEvent.BANK_ACCOUNT_CHANGE_INTEREST_RATE);
  }

  onTransferOperation() {
    this.bankAccountEventService.publishEvent(BankAccountEvent.BANK_ACCOUNT_TRANSFER_OPERATION);
  }
  first: any = Number(localStorage.getItem("accountsPage"));
  onTablePageChange($event: any) {
    this.first = $event.first;
    localStorage.setItem("accountsPage", this.first.toString());
  }
}
