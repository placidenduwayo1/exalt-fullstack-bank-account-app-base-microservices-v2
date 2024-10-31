import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { GetApis } from "../apis/apis";
import { UserDto } from "../../models/auth-user/user-dto.model";
import { Observable } from "rxjs";
import { appHeader } from "../header";
import { RoleDto } from "../../models/auth-user/role-dto.model";
import { UserRoleDto } from "../../models/auth-user/user-role-dto";
import { UserUpdateDto } from "../../models/auth-user/user-update-dto";

@Injectable({ providedIn: "root" })
export class UserService {
    httpClient = inject(HttpClient);
    gatewayApi = new GetApis().getSecurityApi();

    createUser(userDto: UserDto): Observable<UserDto> {
        return this.httpClient.post<UserDto>(`${this.gatewayApi}/users`, userDto, { headers: appHeader });
    }
    getAllUsers(): Observable<any[]> {
        return this.httpClient.get<any[]>(`${this.gatewayApi}/users`);
    }

    createRole(roleDto: RoleDto): Observable<RoleDto> {
        return this.httpClient.post<RoleDto>(`${this.gatewayApi}/roles`, roleDto, { headers: appHeader });
    }

    userAddRole(dto: UserRoleDto): Observable<UserRoleDto> {
        return this.httpClient.post<UserRoleDto>(`${this.gatewayApi}/users/add-role`, dto, { headers: appHeader });
    }

    userRemoveRole(dto: UserRoleDto): Observable<UserRoleDto> {
        return this.httpClient.post<UserRoleDto>(`${this.gatewayApi}/users/remove-role`, dto, { headers: appHeader });
    }

    deleteUser(userId: string): Observable<void> {
        return this.httpClient.delete<void>(`${this.gatewayApi}/users/${userId}`);
    }

    editUserInformation(userId: number, dto: UserUpdateDto): Observable<UserUpdateDto> {
        return this.httpClient.put<UserUpdateDto>(`${this.gatewayApi}/users/${userId}`, dto, { headers: appHeader })
    }
    getAllRoles(): Observable<any[]> {
        return this.httpClient.get<any[]>(`${this.gatewayApi}/roles`);
    }
}