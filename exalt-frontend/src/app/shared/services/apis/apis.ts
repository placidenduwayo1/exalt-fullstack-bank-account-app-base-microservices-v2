import { environment } from "src/environments/environment";


export class GetApis {

    securityApi = environment.gatewayApi;
    customerApi = environment.gatewayApi;
    bankAccountApi = environment.gatewayApi;
    operationApi = environment.gatewayApi;

    getSecurityApi():string {
        return `${this.securityApi}/api-security`;
    }

    getCustomerApi(): string {
        return `${this.customerApi}/api-customer`;
    }
    getBankAccountApi(): string {
        return `${this.bankAccountApi}/api-bank-account`;
    }
    getOperationApi(): string {
        return `${this.operationApi}/api-operation`;
    }
}