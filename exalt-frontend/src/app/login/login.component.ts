import { Component, OnInit, inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserAuthenticationService } from '../shared/services/authentication/auth-user.service';
import { JwtDto } from '../shared/models/auth-user/jwt-dto.model';
import { ConfirmationService, MessageService } from 'primeng/api';
import { acceptLabel, confirmMsg, detailMsg1, detailMsg2, errorMsg, rejectLabel, severity1, severity2 } from '../const-variables';
import { UserEventService } from '../shared/services/events/event.service';
import { UserEvent } from '../shared/models/event.model';
import { UserService } from '../shared/services/user/user.service';
import { UserDto } from '../shared/models/auth-user/user-dto.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private authService = inject(UserAuthenticationService);
  private confirmService = inject(ConfirmationService);
  private messageService = inject(MessageService);
  private userEventService = inject(UserEventService);
  private userService = inject(UserService);

  registerForm!: FormGroup;

  users!: any[];


  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      pwd: ['', Validators.required]
    });

    this.registerForm = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      pwd: ['', Validators.required],
      pwd1: ['', Validators.required]
    });

    /*s'il est déjà loggé c'est à dire que le token est deja setté dans le local storage:
     localStorage.setItem('token', token), même s'il revient on login, du moment qu'il n'a pas cliqué sur logout,
     il reste connecté et sera toujour redirgé vers la session*/
    if (this.authService.loggedIn()) {
      this.router.navigateByUrl("/session");
    }

    this.userEventService.userEventObservable.subscribe((event: UserEvent) => {
      switch (event) {
        case UserEvent.LOGIN:
          console.log(event);
          this.confirmService.confirm({
            acceptLabel: acceptLabel,
            rejectLabel: rejectLabel,
            message: confirmMsg,
            accept: () => {
              let jwtDto: JwtDto = this.loginForm.value;
              jwtDto.grantType = 'username&pwd';
              jwtDto.withRefreshToken = true;
              jwtDto.refreshToken = "";

              this.authService.login(jwtDto).subscribe({
                next: (value: Map<string, string>) => {
                  console.log(value);
                  this.router.navigateByUrl("/session");
                  return value;
                },
                error: (err) => {
                  this.messageService.add({
                    key: 'key2',
                    severity: severity2,
                    detail: 'login error occurred',
                    sticky: true
                  });
                },
                complete: () => {
                  console.log('observable completed');
                }
              });
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

        case UserEvent.REGISTER:
          console.log(event);
          this.confirmService.confirm({
            acceptLabel: acceptLabel,
            rejectLabel: rejectLabel,
            message: confirmMsg,

            accept: () => {
              this.userService.createUser(this.registerForm.value).subscribe({
                next: (user: any) => {
                  this.messageService.add({
                    key: "key1",
                    severity: severity1,
                    detail: `successfully registered,login: "${user.username}"`,
                    sticky: true
                  });
                  return user;
                },
                error: () => {
                  this.messageService.add({
                    key: 'key2',
                    severity: severity2,
                    detail: errorMsg,
                    sticky: true
                  });
                },
                complete: () => {
                  console.log('Observable completed');
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
      }
    });
  }

  onLogin() {
    this.userEventService.publishEvent(UserEvent.LOGIN);
  }

  onRegister() {
    this.userEventService.publishEvent(UserEvent.REGISTER);
  }
}
