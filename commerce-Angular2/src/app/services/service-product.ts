import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ServiceProduct {
    private apiUrl = 'http://localhost:8080/produit/all';
     constructor(private http: HttpClient) {}

  getProducts() {
    return this.http.get(this.apiUrl);
  }

  addProduct(product: any) {
    return this.http.post(this.apiUrl, product);
  }
}
