import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable, tap } from "rxjs";
import { BankAccount } from "../../models/bank-account/bank-account.model";
import { GetApis } from "../apis/apis";
import { appHeader } from "../header";
import { AccountSwitchStateDto } from "../../models/bank-account/account-swithstate-dto.model";
import { BankAccountOverdraftDto } from "../../models/bank-account/account-overdraft-dto.model";
import { BankAccountInterestRateDto } from "../../models/bank-account/account-interest-rate-dto.model";

@Injectable({ providedIn: "root" })
export class BankAccountService {

    httpClient = inject(HttpClient);
    bankAccountApi = new GetApis().getBankAccountApi();

    getAll(): Observable<BankAccount[]> {
        return this.httpClient.get<BankAccount[]>(`${this.bankAccountApi}/accounts`)
    }

    create(bankAccount: BankAccount): Observable<BankAccount> {
        return this.httpClient.post<BankAccount>(`${this.bankAccountApi}/accounts`, bankAccount,
            { headers: appHeader }
        )
    }
    getBankAcount(accountId: string): Observable<BankAccount> {
        return this.httpClient.get<BankAccount>(`${this.bankAccountApi}/accounts/${accountId}`);
    }

    switchState(accountSwitchStateDto: AccountSwitchStateDto): Observable<BankAccount> {
        return this.httpClient.post<BankAccount>(`${this.bankAccountApi}/accounts/switch-state`, accountSwitchStateDto,
            { headers: appHeader }
        )
    }
    changeOverdraft(overdraftDto: BankAccountOverdraftDto): Observable<BankAccount> {
        return this.httpClient.post<BankAccount>(`${this.bankAccountApi}/accounts/overdraft`, overdraftDto,
            { headers: appHeader });
    }
    changeInterestate(iRateDto: BankAccountInterestRateDto): Observable<BankAccount> {
        return this.httpClient.post<BankAccount>(`${this.bankAccountApi}/accounts/interest-rate`, iRateDto,
            { headers: appHeader });
    }

    getAllSuspendedAccounts(): Observable<BankAccount[]> {
        return this.httpClient.get<BankAccount[]>(`${this.bankAccountApi}/accounts/suspended`)
            .pipe(
                tap((data: BankAccount[]) => console.log(data))
            );
    }

    getAllRiskAccounts(): Observable<BankAccount[]>{
        return this.httpClient.get<BankAccount[]>( `${this.bankAccountApi}/accounts/to-risk`)
        .pipe(
            tap((data:BankAccount[])=>{
                console.log(data);
            })
        );
    }
}