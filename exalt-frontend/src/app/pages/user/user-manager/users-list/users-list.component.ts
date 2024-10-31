import { Component, Input, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { acceptLabel, confirmMsg, detailMsg1, detailMsg2, errorMsg, observableComplete, rejectLabel, severity1, severity2, sticky }
  from 'src/app/const-variables';
import { UserRoleDto } from 'src/app/shared/models/auth-user/user-role-dto';
import { UserUpdateDto } from 'src/app/shared/models/auth-user/user-update-dto';
import { UserEvent } from 'src/app/shared/models/event.model';
import { UserEventService } from 'src/app/shared/services/events/event.service';
import { UserService } from 'src/app/shared/services/user/user.service';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {

  @Input() inputUsers!: any[];
  @Input () isLoading!: boolean;

  userRoleSidebarVisible: boolean = false;
  sidebarPosition: string = "right";
  userEditSidebarVisible: boolean = false;

  addRoleForm!: FormGroup;
  editUserForm!: FormGroup;
  fb = inject(FormBuilder);

  userEventService = inject(UserEventService);
  confirmService = inject(ConfirmationService);
  msgService = inject(MessageService);
  userService = inject(UserService);

  headerMsg!: string;


  ngOnInit(): void {

    this.editUserForm = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });

    this.userEventService.userEventObservable.subscribe((event: UserEvent) => {
      switch (event) {
        case UserEvent.ADD_ROLE_BUTTON_CLICKED:
          console.log(event);
          this.headerMsg = "Add Role to User";
          this.addRoleForm = this.fb.group({
            user: [{ value: '', disabled: true }],
            roleName: ['', Validators.required]
          });
          break;
        case UserEvent.REMOVE_ROLE_BUTTON_CLICKED:
          console.log(event);
          this.headerMsg = "Remove Role from User";
          this.addRoleForm = this.fb.group({
            user: [{ value: '', disabled: true }],
            roleName: ['', Validators.required]
          });
          break;

        case UserEvent.USER_ROLE_ADD:
          console.log(event);
          this.confirmService.confirm({
            acceptLabel: acceptLabel,
            rejectLabel: rejectLabel,
            message: confirmMsg,

            accept: () => {
              let dto: UserRoleDto = new UserRoleDto();
              dto.userId = this.selectedUser.userId;
              dto.roleName = this.addRoleForm.value.roleName;
              this.userService.userAddRole(dto).subscribe({
                next: (value: UserRoleDto) => {
                  this.msgService.add({
                    key: 'key1',
                    severity: severity1,
                    detail: detailMsg1,
                    sticky: sticky,
                  });
                  this.userEventService.publishEvent(UserEvent.REFRESH);
                  return value;
                },
                error: () => {
                  this.msgService.add({
                    key: 'key2',
                    severity: severity2,
                    detail: errorMsg,
                    sticky: sticky,
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
                key: 'key2',
                severity: severity2,
                detail: detailMsg2,
                sticky: sticky,
              });
              return null;
            }
          });
          break;

        case UserEvent.USER_ROLE_REMOVE:
          console.log(event);
          this.confirmService.confirm({
            acceptLabel: acceptLabel,
            rejectLabel: rejectLabel,
            message: confirmMsg,

            accept: () => {
              let dto: UserRoleDto = new UserRoleDto();
              dto.userId = this.selectedUser.userId;
              dto.roleName = this.addRoleForm.value.roleName;
              this.userService.userRemoveRole(dto).subscribe({
                next: (value: UserRoleDto) => {
                  this.msgService.add({
                    key: 'key1',
                    severity: severity1,
                    detail: detailMsg1,
                    sticky: sticky
                  });
                  this.userEventService.publishEvent(UserEvent.REFRESH);
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
                },
                complete: () => {
                  console.log(observableComplete);
                }
              });
            },
            reject: () => {
              this.msgService.add({
                key: 'key2',
                severity: severity2,
                detail: detailMsg2,
                sticky: sticky
              });
              return null;
            }
          });
          break;

        case UserEvent.USER_DELETE:
          console.log(event);
          this.confirmService.confirm({
            acceptLabel: acceptLabel,
            rejectLabel: rejectLabel,
            message: confirmMsg,

            accept: () => {
              this.userService.deleteUser(this.userId).subscribe({
                next: (value: void) => {
                  this.msgService.add({
                    key: 'key1',
                    severity: severity1,
                    detail: detailMsg1,
                    sticky: sticky
                  });
                  this.userEventService.publishEvent(UserEvent.REFRESH);
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
                },
                complete: () => {
                  console.log(observableComplete);
                }
              });
            },
            reject: () => {
              this.msgService.add({
                key: 'key2',
                severity: severity2,
                detail: detailMsg2,
                sticky: sticky
              });
              return null;
            }
          });
          break;

        case UserEvent.USER_EDIT:
          console.log(event);
          this.confirmService.confirm({
            acceptLabel: acceptLabel,
            rejectLabel: rejectLabel,
            message: confirmMsg,

            accept: () => {
              this.userService.editUserInformation(this.newSelectedUser.userId, this.editUserForm.value).subscribe({
                next: (value: UserUpdateDto) => {
                  this.msgService.add({
                    key: 'key1',
                    severity: severity1,
                    detail: detailMsg1,
                    sticky: sticky
                  });
                  this.userEventService.publishEvent(UserEvent.REFRESH);
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
                },
                complete: () => {
                  console.log(observableComplete);
                }
              });
            },
            reject: () => {
              this.msgService.add({
                key: 'key2',
                severity: severity2,
                detail: detailMsg2,
                sticky: sticky
              });
              return null;
            }
          });
      }
    });
  }

  selectedUser: any;
  selectedUserNgModel!: string;

  onLoadUser(user: any) {
    this.selectedUser = user;
    this.selectedUserNgModel = user.userId + ' ' + user.username;
  }

  assignUserRole() {
    if (this.headerMsg === 'Add Role to User') {
      this.userEventService.publishEvent(UserEvent.USER_ROLE_ADD);
    }
    else if (this.headerMsg === 'Remove Role from User') {
      this.userEventService.publishEvent(UserEvent.USER_ROLE_REMOVE);
    }
  }
  addButtonClicked() {
    this.userEventService.publishEvent(UserEvent.ADD_ROLE_BUTTON_CLICKED);
  }
  removeButtonClicked() {
    this.userEventService.publishEvent(UserEvent.REMOVE_ROLE_BUTTON_CLICKED);
  }
  userId!: string;
  deleteUser(user: any) {
    this.userId = user.userId;
    this.userEventService.publishEvent(UserEvent.USER_DELETE);
  }

  newSelectedUser: any;
  onLoadUser1(user: any) {
    this.newSelectedUser = user;
  }

  editUser() {
    this.userEventService.publishEvent(UserEvent.USER_EDIT);
  }
  first: number = Number(window.localStorage.getItem('usersPage'))
  onTablePageChange($event : any){
    this.first = $event.first;
    window.localStorage.setItem('usersPage',this.first.toString());
  }
}
