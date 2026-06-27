import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartItems, CartService } from '../../services/cartservice'; // Keep this import
import { Router , RouterModule} from '@angular/router';
import { HeaderComponent } from '../../components/header/header';
import { OrderService } from '../../services/serviceorder';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, HeaderComponent, RouterModule],
  templateUrl: './cart.html',
  styleUrls: ['./cart.css'],
})

export class cartItems implements OnInit {
  private router = inject(Router);
   cartItems: CartItems[] = [];
  total: number = 0;

  constructor(private cartService: CartService, 
    private  orderService: OrderService,
     ) {} 
 ngOnInit() {
  const saved = localStorage.getItem('cartItems');
  if (saved) {
    
    this.cartItems = JSON.parse(saved);
    this.cartItems = this.cartItems.filter(item => item.produit !== null);
    this.total = parseFloat(localStorage.getItem('cartTotal') || '0');
  }
  this.loadCart(); 
}

loadCart() {
  this.cartService.getCartItems().subscribe({
    next: (items) => {
      console.log('Items reçus :', items);
      //  Filtrer les articles avec produit valide
      this.cartItems = items.filter(item => item.produit !== null && item.quantite > 0);  
      //  Recalculer le total si nécessaire
      this.cartItems = this.cartItems.map(item => ({
        ...item,
        total: item.total || (item.produit?.prix || 0) * item.quantite
      }));  
      //  Calculer le total général
      this.total = this.cartItems.reduce((sum, item) => sum + (item.total || 0), 0);
      console.log(' Total général calculé :', this.total);

      this.cartItems.forEach(item => {
      console.log(` ${item.produit?.nom}: quantite=${item.quantite}, prix=${item.produit?.prix}, total=${item.total}`);
});
    },
    error: (err) => console.error(err)
  });
}

 clearCart(): void {
    if (confirm('Voulez-vous vraiment vider votre panier ?')) {
      this.cartService.clearCart().subscribe({
        next: () => {
          this.cartItems = [];
          this.total = 0;
          alert('Panier vidé avec succès');
        },
        error: (err: any) => console.error('Erreur lors du vidage', err) // Type 'err' explicitly
      });
    }
    
  }
   checkout(): void {
  if (this.cartItems.length === 0) {
    alert('Votre panier est vide');
    return;
  }

  const validItems = this.cartItems.filter(item => item.produit !== null && item.quantite > 0);
  if (validItems.length === 0) {
    alert('Aucun article valide dans le panier');
    return;
  }

  const orderData = {
    userId: 1,
    total: this.total,
    items: validItems.map(item => ({
      produitId: item.produit.id,
      quantite: item.quantite,
      prix: item.produit.prix
    }))
  };

  this.cartService.createOrder(orderData).subscribe({
    next: (order) => {
      console.log(' Commande créée :', order);
      this.cartService.clearCart().subscribe(() => {
        this.cartItems = [];
        this.total = 0;
        this.router.navigate(['/order', order.id]);
      });
    },
    error: (err) => {
      console.error('❌ Erreur :', err);
      alert('Erreur lors de la création de la commande');
    }
  });
}

  updateQuantity(id: number, newQuantity: number): void {
  if (newQuantity < 1) return;
  this.cartService.updateQuantity(id, newQuantity).subscribe({
    next: () => this.loadCart(),
    error: (err: any) => console.error(err) // Type 'err' explicitly
  });
}

removeItem(id: number) {
  this.cartService.removeFromCart(id).subscribe({
    next: () => {
      console.log('Suppression OK, rechargement dans 500ms');
      setTimeout(() => this.loadCart(), 500);
    },
    error: (err: any) => console.error(err) // Type 'err' explicitly
  });
}
}
