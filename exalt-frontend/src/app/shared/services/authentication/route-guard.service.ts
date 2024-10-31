import { inject } from "@angular/core";
import { CanActivateFn } from "@angular/router";
import { UserAuthenticationService } from "./auth-user.service";
import { jwtDecode } from "jwt-decode";

export const isTokenExpiredServiceGuard: CanActivateFn = () => {
    let jwt = inject(UserAuthenticationService).getAuthenticationToken();
    if (jwt) {
        if (jwtDecode(jwt).exp) {
            return true;
        }
        else {

            return false;
        }
    }
    return false;
}