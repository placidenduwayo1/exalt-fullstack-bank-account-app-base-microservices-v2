import { ActivatedRouteSnapshot, ResolveFn } from "@angular/router";
import { BankAccount } from "../../models/bank-account/bank-account.model";
import { inject } from "@angular/core";
import { BankAccountService } from "../bank-account/bank-account.service";
import { Customer } from "../../models/customer/customer.model";
import { CustomerService } from "../customer/customer.service";
import { Operation } from "../../models/operation/operation.model";
import { OperationService } from "../operation/operation.service";
import { UserService } from "../user/user.service";

export const getAllBankAccountsResolve: ResolveFn<BankAccount[]> = () => {
    return inject(BankAccountService).getAll();
}

export const getAllCustomersResolve: ResolveFn<Customer[]> = () => {
    return inject(CustomerService).getAll();
}
export const getAllOperationsResolve: ResolveFn<Operation[]> = () => {
    return inject(OperationService).getAllOperations();
}

export const getCustomerResolve: ResolveFn<Customer> = (route: ActivatedRouteSnapshot) => {
    return inject(CustomerService).getCustomer(route.paramMap.get('customerId'));
}
export const getAllTransferResolve: ResolveFn<any[]> = () => {
    return inject(OperationService).getAllTransfers();
}

export const getAllUsersResolve: ResolveFn<any[]> = () => {
    return inject(UserService).getAllUsers();
}

export const getAllSuspendedAccountsResolve: ResolveFn<BankAccount[]> = () => {
    return inject(BankAccountService).getAllSuspendedAccounts();
}

export const getAllRiskAccountsResolve: ResolveFn<BankAccount[]> = () => {
    return inject(BankAccountService).getAllRiskAccounts();
}

export const getAllArchivedCustomerResolve: ResolveFn<Customer[]> = () => {
    return inject(CustomerService).getAllArchivedCustomer();
}

export const getAllDepositOperationResolve: ResolveFn<BankAccount[]> = () => {
    return inject(OperationService).getAllDepositOperation();
}

export const getAllWithdrawalsOperationResolve: ResolveFn<BankAccount[]> = () => {
    return inject(OperationService).getAllWithdrawalsOperation();
}