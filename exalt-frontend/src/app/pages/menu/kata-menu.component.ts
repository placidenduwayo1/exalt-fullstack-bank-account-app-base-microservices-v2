import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { CustomerEvent, UserEvent } from 'src/app/shared/models/event.model';
import { UserAuthenticationService } from 'src/app/shared/services/authentication/auth-user.service';
import { UserService } from 'src/app/shared/services/user/user.service';
import { acceptLabel, confirmMsg, detailMsg1, detailMsg2, errorMsg, observableComplete, rejectLabel, severity1, severity2, sticky }
  from '../../const-variables';
import { RoleDto } from 'src/app/shared/models/auth-user/role-dto.model';
import { Customer } from 'src/app/shared/models/customer/customer.model';
import { CustomerService } from 'src/app/shared/services/customer/customer.service';
import { UserEventService, CustomerEventService } from 'src/app/shared/services/events/event.service';
import { Request } from 'src/app/shared/models/customer/request.model';
@Component({
  selector: 'app-kata-menu',
  templateUrl: './kata-menu.component.html',
  styleUrls: ['./kata-menu.component.css']
})
export class KataMenuComponent {

  roleForm!: FormGroup;
  customerRequest!: FormGroup;
  fb = inject(FormBuilder);

  items!: MenuItem[];
  activeItem!: MenuItem;
  private minLen: number = 2;
  private min: number = 1;

  private router = inject(Router);
  private authService = inject(UserAuthenticationService);

  private decodedJwt: any = this.authService.decodedJwt();
  private highlyAuthorized: boolean = this.authService.hasHighAuthority(this.decodedJwt);
  private userEventService = inject(UserEventService);
  private userService = inject(UserService);
  private confirmService = inject(ConfirmationService);
  private msgService = inject(MessageService);
  private customerEventService = inject(CustomerEventService);
  private customerService = inject(CustomerService);

  connectedUser: any = {
    "username": this.decodedJwt.sub,
    "roles": this.decodedJwt.scope
  }

  roleCreateSidebarVisible: boolean = false;
  customerSidebarVisible: boolean = false;
  rolesListSidebarVisible: boolean = false;
  sideBarPisition: string = "right";
  usersListSidebarVisible: boolean = false;
  roles!: any[];

  ngOnInit(): void {

    this.roleForm = this.fb.group({
      roleName: ['', Validators.required]
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

    this.userEventService.userEventObservable.subscribe((event: UserEvent) => {
      if (event === UserEvent.ROLE_CREATE) {
        console.log(event);
        this.confirmService.confirm({
          acceptLabel: acceptLabel,
          rejectLabel: rejectLabel,
          message: confirmMsg,

          accept: () => {
            this.userService.createRole(this.roleForm.value).subscribe({
              next: (value: RoleDto) => {
                this.msgService.add({
                  key: "key1",
                  severity: severity1,
                  detail: detailMsg1,
                  sticky: true
                });
                return value;
              },
              error: () => {
                this.msgService.add({
                  key: 'key2',
                  severity: severity2,
                  detail: errorMsg,
                  sticky: sticky
                });
                return null;
              }
            });
          },
          reject: () => {
            this.msgService.add({
              key: "key2",
              severity: severity2,
              detail: detailMsg2,
              sticky: true
            });
            return null;
          }

        });
      }
    });

    // create customer with form data
    this.customerEventService.customerEventObservable.subscribe((event: CustomerEvent) => {
      if (event == CustomerEvent.CUSTOMER_CREATE) {
        console.log(event);
        let request: Request = new Request();
        request.customerDto = this.customerRequest.value.customer;
        request.addressDto = this.customerRequest.value.address;
        console.log(request)
        this.confirmService.confirm({
          acceptLabel: acceptLabel,
          rejectLabel: rejectLabel,
          message: confirmMsg,
          accept: () => {
            this.customerService.create(request).subscribe({
              next: (data: Customer) => {
                console.log(data);
                this.msgService.add({
                  key: "key1",
                  severity: severity1,
                  detail: detailMsg1,
                  sticky: true
                });
                this.customerEventService.publishEvent(CustomerEvent.REFRESH);
                return data;
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
          },
          reject: () => {
            this.msgService.add({
              key: "key2",
              severity: severity2,
              detail: detailMsg2,
              sticky: true
            });
          }
        });
      }
    });

    this.items = [
  
      {
        label: 'api-customer', icon: 'pi pi-server',
        items: [
          {
            label: 'customers', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl("/session/customers")
          },
          {
            label: 'create', icon: 'pi pi-plus-circle',
            command: () => this.customerSidebarVisible = true
          },
          {
            label: 'archive', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl("/session/customers/archived")
          }
        ],
        visible: this.highlyAuthorized
      },
      {
        label: 'api-bank-account', icon: 'pi pi-server',
        items: [
          {
            label: 'bank-accounts', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl("session/accounts")
          },
          {
            label: 'suspended', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl('/session/accounts/suspended')
          },
          {
            label: 'à-risque', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl('/session/accounts/to-risk')
          }
        ],
        visible: this.highlyAuthorized
      },
      {
        label: 'api-operations', icon: 'pi pi-server',
        items: [
          {
            label: 'depot/retrait', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl('session/operations')
          },
          {
            label: 'transfert', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl('session/operations/transfers')
          },
          {
            label: 'deposits', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl('session/operations/deposit')
          },
          {
            label: 'withdrawals', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl('session/operations/withdrawal')
          }
        ],
        visible: this.highlyAuthorized
      },
      {
        label: 'api-users', icon: 'pi pi-user',
        items: [
          {
            label: 'users', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl("session/users")
          },
          {
            label: 'roles', icon: 'pi pi-list',
            command: () => {
              this.userService.getAllRoles().subscribe({
                next: (value: any[]) => {
                  this.roles = value;
                  this.rolesListSidebarVisible = true;
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
            }
          },
          {
            label: 'create-role', icon: 'pi pi-plus-circle',
            command: () => this.roleCreateSidebarVisible = true
          }
        ],
         visible: this.highlyAuthorized
      }
    ];

    this.activeItem = this.items[0];
  }

  createRole() {
    this.userEventService.publishEvent(UserEvent.ROLE_CREATE);
  }
  createCustomer() {
    this.customerEventService.publishEvent(CustomerEvent.CUSTOMER_CREATE);
  }

  logout() {
    this.authService.logout();
    this.router.navigateByUrl('index1')
  }
  first: number = Number(window.localStorage.getItem('rolesPage'))
  onTablePageChange($event: any) {
    this.first = $event.first;
    window.localStorage.setItem('rolesPage', this.first.toString());
  }
}
