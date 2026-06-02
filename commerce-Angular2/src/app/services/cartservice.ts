import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


 export interface CartItems{
  id: number;
  quantite: number;
  status: string;
  total: number;
  produit: {
    id: number;
    nom: string;
    prix: number;
    imageUrl: string;
  };
}
@Injectable
(
  { providedIn: 'root' }

)
export class CartService {
  private apiUrl = 'http://localhost:8080/CartItem';
  private http: HttpClient;
 

  constructor(http: HttpClient) {
    this.http = http;
    
  }

// cart.service.ts
addToCart(produitId: number, quantite: number = 1): Observable<CartItems> {
  const payload = {
    quantity: quantite,   // ← "quantity" au lieu de "quantite"
    status: "à vendre",
    total: 0,
    produitId: produitId,
    cartId: 1  
  };
  return this.http.post<CartItems>(`${this.apiUrl}/create`, payload);
}
  getCartItems(): Observable<CartItems[]> {
    const url = `${this.apiUrl}?_=${new Date().getTime()}`;
  return this.http.get<CartItems[]>(url);
}
updateQuantity(id: number, quantity: number): Observable<CartItems> {
  return this.http.put<CartItems>(`${this.apiUrl}/update/${id}`, { quantity });
}

removeFromCart(id: number): Observable<void> {
  const url = `${this.apiUrl}/delete/${id}?_=${new Date().getTime()}`;
  return this.http.delete<void>(url);
}

}