import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { GetApis } from "../apis/apis";
import { Observable, delay, tap } from "rxjs";
import { Operation } from "../../models/operation/operation.model";
import { appHeader } from "../header";
import { TransferDto } from "../../models/operation/transfer-dto.model";
import { BankAccount } from "../../models/bank-account/bank-account.model";

@Injectable({ providedIn: "root" })
export class OperationService {
    httpClient = inject(HttpClient);
    operationApi = new GetApis().getOperationApi();

    getAllOperations(): Observable<Operation[]> {
        return this.httpClient.get<Operation[]>(`${this.operationApi}/operations`);
    }

    create(operation: Operation): Observable<Operation> {
        return this.httpClient.post<Operation>(`${this.operationApi}/operations`, operation,
            { headers: appHeader });
    }
    createTransfer(transferDto: TransferDto): Observable<Map<string, BankAccount>> {
        return this.httpClient.post<Map<string, BankAccount>>(`${this.operationApi}/operations/transfer`, transferDto,
            { headers: appHeader }
        );
    }
    getAllTransfers(): Observable<any[]> {
        return this.httpClient.get<any[]>(`${this.operationApi}/operations/transfers`);
    }

    getAllDepositOperation(): Observable<BankAccount[]> {
        return this.httpClient.get<BankAccount[]>(`${this.operationApi}/operations/deposit`)
            .pipe(
                tap((data: BankAccount[]) => {
                    console.log('do operation with uploaded data')
                })
            )
    }
    getAllWithdrawalsOperation(): Observable<BankAccount[]> {
        return this.httpClient.get<BankAccount[]>(`${this.operationApi}/operations/withdrawals`)
        .pipe(
            tap((data: BankAccount[]) => {
                console.log('do operation with uploaded data')
            })
        );
    }
}