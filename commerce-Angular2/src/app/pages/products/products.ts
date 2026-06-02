import { Component, OnInit, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { CartService } from '../../services/cartservice';
import { ServiceProduct } from '../../services/service-Product';
@Component({
  selector: 'app-Products',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './Products.html',
  styleUrls: ['./Products.css']
})
 
export class Product implements OnInit {
  private http = inject(HttpClient);      
  private cartservice = inject(CartService)  
 
   products: any[] = [];
   loading = true; 
   error: string | null = null;
  
   constructor(private produitService: ServiceProduct) {}
 ngOnInit() {
   
    const cached = localStorage.getItem('Products');
    if (cached) {
      this.products = JSON.parse(cached);
      this.loading = false;
      console.log('Produits chargés depuis le cache');
    }

 this.http.get<any[]>('http://localhost:8080/produit/all').subscribe({
      next: (data) => {
        this.products = data;
        this.loading = false;
        localStorage.setItem('Products', JSON.stringify(data));  // ← sauvegarde
        console.log('Produits mis à jour depuis l\'API');
      },
      error: (err) => {
        console.error('Erreur API :', err);
        this.loading = false;
        if (!cached) {
          this.products = [];
        }
      }
    });
  }

  onImageError(event: Event) {
    const img = event.target as HTMLImageElement;
    img.src = '/assets/produits/placeholder.jpg'; // Chemin vers une image de remplacement
  }

  addToCart(Product: any) {
      console.log('Ajout au panier:', Product);
      console.log('Produit complet :', JSON.stringify(Product));
      console.log('ID utilisé :', Product.id);
     this.cartservice.addToCart(Product.id, 1).subscribe({
      next: (response) => {
        console.log('Succès :', response);
        alert(`${Product.nom} ajouté au panier !`);
      },
      error: (err) => {
        console.error('Erreur :', err);
        alert('Erreur lors de l\'ajout au panier');
      }
    });
  }
 }

