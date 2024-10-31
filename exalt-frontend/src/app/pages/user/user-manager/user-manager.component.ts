import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { errorMsg, observableComplete, severity2, sticky, timeout } from 'src/app/const-variables';
import { UserEvent } from 'src/app/shared/models/event.model';
import { UserEventService } from 'src/app/shared/services/events/event.service';
import { UserService } from 'src/app/shared/services/user/user.service';

@Component({
  selector: 'app-user-manager',
  templateUrl: './user-manager.component.html',
  styleUrls: ['./user-manager.component.css']
})
export class UserManagerComponent implements OnInit {

  activatedRoute = inject(ActivatedRoute);
  userEventService = inject(UserEventService);
  userService = inject(UserService);
  msgService = inject(MessageService);

  users!: any[];
  isLoading: boolean = true;

  ngOnInit(): void {
    
    setTimeout(() => {
      this.activatedRoute.data.subscribe({
        next: ({ allUsers }) => {
          this.users = allUsers;
          return this.users;
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

    this.userEventService.userEventObservable.subscribe((event: UserEvent) => {
      if (event === UserEvent.REFRESH) {
        this.userService.getAllUsers().subscribe((data: any[]) => {
          this.users = data;
        });
      }
    });
  }

}
