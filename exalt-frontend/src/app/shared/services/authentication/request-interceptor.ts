import {  HttpErrorResponse, HttpHandlerFn, HttpHeaders, HttpInterceptorFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { UserAuthenticationService } from "./auth-user.service";
import { catchError, retry } from "rxjs";
import { errorMsg, errorServer, sessionError, severity2, sticky } from "src/app/const-variables";
import { MessageService } from "primeng/api";

interface RetryConfig {
    count:number;
    delay: number;
}

export const appHttpInterceptor: HttpInterceptorFn = (inputRequest: HttpRequest<any>, next: HttpHandlerFn) => {
    let retryConfig: RetryConfig = {
        count: 3,
        delay: 500
    };

    if (inputRequest.url.includes('api-security/login')) {
        return next(inputRequest).pipe(
            retry(retryConfig)
        );
    }
    const jwt = inject(UserAuthenticationService).getAuthenticationToken();
    const msgService = inject(MessageService);
    let authRequest = inputRequest;
    if (jwt) {
        const headers = new HttpHeaders().append('Authorization', `Bearer ${jwt}`);
        authRequest = inputRequest.clone({ headers: headers })
    }

    return next(authRequest).pipe(
        retry(retryConfig),
        catchError((err: HttpErrorResponse)=>{
            if(err.status===401 || err.status===403){
                
                msgService.add({
                    key: 'key2',
                    severity: severity2,
                    detail: sessionError,
                    sticky: sticky,
                 });
            }
            else if(err.status===500 || err.status===502){
                msgService.add({
                    key: 'key2',
                    severity: severity2,
                    detail: errorServer,
                    sticky: sticky,
                 });
            }
            
             throw new Error(errorMsg);
        })
    );
}