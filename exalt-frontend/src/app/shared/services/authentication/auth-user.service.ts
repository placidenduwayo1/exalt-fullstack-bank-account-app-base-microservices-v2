import { Injectable, inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";
import { GetApis } from "../apis/apis";
import { JwtDto } from "../../models/auth-user/jwt-dto.model";
import { appHeader } from "../header";
import { jwtDecode } from "jwt-decode";

const ACCESS_TOKEN_KEY = 'access-token-key';

@Injectable({ providedIn: "root" })
export class UserAuthenticationService {

    securityApi: string = new GetApis().getSecurityApi();
    httpClient = inject(HttpClient);

    setAuthenticationToken(jwt: string): void {
        if (jwt !== null) {
            window.localStorage.setItem(ACCESS_TOKEN_KEY, jwt);
        }

    }

    getAuthenticationToken(): string | null {
        return window.localStorage.getItem(ACCESS_TOKEN_KEY);
    }

    login(jwtDto: JwtDto): Observable<Map<string, string>> {
        return this.httpClient.post<Map<string, string>>(`${this.securityApi}/login`, jwtDto,
            { headers: appHeader })
            .pipe(
                tap((data: any) => {
                    let jwt: string = data[ACCESS_TOKEN_KEY];
                    this.setAuthenticationToken(jwt);
                })
            );
    }

    logout(): void {
        window.localStorage.removeItem(ACCESS_TOKEN_KEY);
    }

    loggedIn(): boolean {
        if (this.getAuthenticationToken()) {
            return true;
        }
        return false;
    }

    isJwtExpired(jwt: any): boolean {
        let decodedJwt: any = jwtDecode(jwt);
        if (decodedJwt.exp * 1000 < Date.now()) {
            return true;
        }
        return false;
    }

    decodedJwt(): any {
        let jwt = this.getAuthenticationToken();
        if (jwt) {
            return jwtDecode(jwt);
        }
        return null;
    }

    hasHighAuthority(decodedJwt: any) : any {
        let highAuthorities: string[] = ['Admin', 'HR', 'DHR', 'CTO', 'CEO'];
        if(decodedJwt.scope.some((authority: string) => highAuthorities.includes(authority))){
            return true;
        }
        return false;
    }
    
    hasLowAuthority(decodedJwt: any): any {
        let lowAuthorities: string [] = ['User','Engineer'];
        if(decodedJwt.scope.some((auth: string)=>lowAuthorities.includes(auth))){
            return true;
        }
        return false;
    }
}