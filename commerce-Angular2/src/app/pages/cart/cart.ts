import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartItems, CartService } from '../../services/cartservice';
import { RouterModule } from '@angular/router';
@Component({
  selector: 'app-cart',
  imports: [CommonModule, RouterModule],
  templateUrl: './cart.html',
  styleUrl: './cart.css',
})
export class cartItems implements OnInit {
   cartItems: CartItems[] = [];
  total: number = 0;

  constructor(private cartService: CartService) {}
 ngOnInit() {
  const saved = localStorage.getItem('cartItems');
  if (saved) {
    this.cartItems = JSON.parse(saved);
    this.cartItems = this.cartItems.filter(item => item.produit !== null);
    this.total = parseFloat(localStorage.getItem('cartTotal') || '0');
  }
  this.loadCart(); // appel API pour mettre à jour
}

loadCart() {
  this.cartService.getCartItems().subscribe({
    next: (items) => {
      console.log('Items reçus :', items);
      this.cartItems = items;
      this.total = items.reduce((sum, item) => sum + (item.total || 0), 0);
    
      localStorage.setItem('cartItems', JSON.stringify(items));
      localStorage.setItem('cartTotal', this.total.toString());
    },
    error: (err) => {
      console.error(err);
       alert('Erreur lors de la suppression');
      const savedItems = localStorage.getItem('cartItems');
      if (savedItems) {
        this.cartItems = JSON.parse(savedItems);
        this.total = parseFloat(localStorage.getItem('cartTotal') || '0');
      }
    }
  });
}

  updateQuantity(id: number, newQuantity: number): void {
  if (newQuantity < 1) return;
  this.cartService.updateQuantity(id, newQuantity).subscribe({
    next: () => this.loadCart(),
    error: (err) => console.error(err)
  });
}

removeItem(id: number) {
  this.cartService.removeFromCart(id).subscribe({
    next: () => {
      console.log('Suppression OK, rechargement dans 500ms');
      setTimeout(() => this.loadCart(), 500);
    },
    error: (err) => console.error(err)
  });
}
}
