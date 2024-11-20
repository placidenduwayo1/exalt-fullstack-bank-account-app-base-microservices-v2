import { environment } from "src/environments/environment";


export class GetApis {

    securityApi = environment.authServiceApi;
    customerApi = environment.customerApi;
    bankAccountApi = environment.bankAccountApi;
    operationApi = environment.operationApi;

    getSecurityApi():string {
        return `${this.securityApi}/api/security`;
    }

    getCustomerApi(): string {
        return `${this.customerApi}/api/customer`;
    }
    getBankAccountApi(): string {
        return `${this.bankAccountApi}/api/bank-account`;
    }
    getOperationApi(): string {
        return `${this.operationApi}/api/operation`;
    }
}