import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Product {
    price: number;
    name: string;
    url: string;
}

@Injectable({ providedIn: 'root' })
export class ProductService {
    private readonly baseUrl: string = 'http://localhost:8080';

    constructor(private readonly rest: HttpClient) { }

    getProduct(id: number, userKey: string, plan: string, email: string): Observable<Product> {
        const url = `${this.baseUrl}/api/product/${id}?userKey=${userKey}&plan=${plan}&email=${email}`;
        return this.rest.get<Product>(url);
    }
}