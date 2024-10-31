import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { CustomerSwitchStateDto } from "../../models/customer/customer-switch-state-dto.model";
import { Customer } from "../../models/customer/customer.model";
import { GetApis } from "../apis/apis";
import { appHeader } from "../header";
import { Request } from "../../models/customer/request.model";


@Injectable({ providedIn: "root" })
export class CustomerService {
    httpClient = inject(HttpClient)
    customerApi = new GetApis().getCustomerApi();

    getAll(): Observable<Customer[]> {
        return this.httpClient.get<Customer[]>(`${this.customerApi}/customers`);
    }
    create(request: Request): Observable<Customer> {
        return this.httpClient.post<Customer>(`${this.customerApi}/customers`, request, { headers: appHeader });
    }
    getCustomer(customerId: string | any): Observable<Customer> {
        return this.httpClient.get<Customer>(`${this.customerApi}/customers/${customerId}`)
    }
    switchState(switchStateDto: CustomerSwitchStateDto): Observable<Customer> {
        return this.httpClient.post<Customer>(`${this.customerApi}/customers/switch-state`, switchStateDto, { headers: appHeader });
    }

    updateCustomer(custmerId: string, request: Request): Observable<Customer> {
        return this.httpClient.put<Customer>(`${this.customerApi}/customers/${custmerId}`,request,{headers: appHeader});
    }
    getAllArchivedCustomer():Observable<Customer[]>{
        return this.httpClient.get<Customer[]>(`${this.customerApi}/customers/archived`);
    }
}