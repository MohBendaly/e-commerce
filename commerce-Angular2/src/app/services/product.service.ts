import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';

export interface Product {
  id: number;
  nom: string;
  description: string;
  prix: number;
  quantite: number;
  imageUrl: string;
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/produit';

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/all`);
  }

  getProductsByIds(ids: number[]): Observable<Product[]> {
    return this.getAllProducts().pipe(
      map(products => products.filter(p => ids.includes(Number(p.id))))
    );
  }
}
