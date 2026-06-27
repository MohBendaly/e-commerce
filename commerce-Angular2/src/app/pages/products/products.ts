import { Component, OnInit, OnDestroy, inject, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { CartService } from '../../services/cartservice';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../../components/header/header';
import { Subscription } from 'rxjs'; 
import { Product, ProductService } from '../../services/product.service';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, HeaderComponent],
  templateUrl: './products.html',
  styleUrls: ['./products.css']
})
export class Products implements OnInit, OnDestroy {
  private route = inject(ActivatedRoute);
  private cartservice = inject(CartService);
  private router = inject(Router);
  private productService = inject(ProductService);
  private cdr = inject(ChangeDetectorRef); // Pour forcer le rafraîchissement

  filteredProducts: Product[] = [];
  loading = true;
  error: string | null = null;
  categoryLabel: string = '';

  private queryParamsSubscription: Subscription | undefined; // For cleanup

  constructor() {} // Constructeur vide car on utilise inject()
  
  ngOnInit() {
    console.log('🔍 ngOnInit appelé');
      this.route.queryParams.subscribe(params => {
    console.log('📌 QueryParams reçus :', params);
  
  });
    this.queryParamsSubscription = this.route.queryParams.subscribe((params) => {
      this.loadProducts(params);
    });
  }

  ngOnDestroy(): void {
    this.queryParamsSubscription?.unsubscribe();
  }

  loadProducts(params: any) {
    console.log('loadProducts method called with params:', params);
    const idsParam = params['ids'];
    if (idsParam) {
      const ids = idsParam.split(',').map((id: string) => Number(id));
      console.log('loadProducts: Detected IDs in query params:', ids);
      this.loadProductsByIds(ids);
    } else {
      console.log('loadProducts: No IDs in query params, loading all products.');
      this.loadAllProducts();
    }
  }

  loadAllProducts() {
    this.loading = true;
    this.error = null;
    console.log('loadAllProducts method called. Setting loading to true.');
    this.productService.getAllProducts().subscribe({
    next: (data) => {
      this.filteredProducts = data;
      this.loading = false;
      console.log('loadAllProducts: Products received successfully:', data);
      console.log('loadAllProducts: Loading set to false. filteredProducts length:', this.filteredProducts.length);
      this.cdr.detectChanges(); // Force le rafraîchissement de l'interface
    },
    error: (err) => {
      console.error('loadAllProducts: Error loading products:', err);
      this.error = "Erreur lors du chargement des produits.";
      this.loading = false;
      this.filteredProducts = []; // Ensure no products are shown on error
    }
  });
}

  loadProductsByIds(ids: number[]) {
    this.loading = true;
    this.error = null;
    console.log('loadProductsByIds method called with IDs:', ids, '. Setting loading to true.');
    this.productService.getProductsByIds(ids).subscribe({
    next: (data) => {
      this.filteredProducts = data;
      this.loading = false;
      console.log('loadProductsByIds: Products received successfully:', data);
      console.log('loadProductsByIds: Loading set to false. filteredProducts length:', this.filteredProducts.length);
      this.cdr.detectChanges(); // Force le rafraîchissement de l'interface
    },
    error: (err) => {
      console.error('loadProductsByIds: Error loading products by IDs:', err);
      this.error = "Erreur lors du filtrage des produits.";
      this.loading = false;
      this.filteredProducts = []; // Ensure no products are shown on error
    }
  });
}

  onImageError(event: Event) {
    const img = event.target as HTMLImageElement;
    img.src = '/assets/produits/placeholder.jpg';
    console.warn('Image failed to load:', img.src);
  }
                                                               
 addToCart(product: Product) {
  // 1. Sécurité : on s'assure que le produit et son ID existent
  if (!product || !product.id) {
    console.error("Le produit ou l'ID du produit est manquant", product);
    alert("Impossible d'ajouter le produit : données manquantes.");
    return;
  }
  // 2. Appel au service avec l'ID et la quantité fixe (1)
  this.cartservice.addToCart(product.id, 1).subscribe({
    next: (response) => {
      console.log('✅ Ajouté au panier :', response);
      alert(`"${product.nom}" a été ajouté au panier !`);
    },
    error: (err: HttpErrorResponse) => {
      console.error("Erreur lors de l'ajout :", err);
      // Extrait le message d'erreur spécifique du backend s'il existe
      let errorMessage = "Une erreur est survenue lors de l'ajout au panier.";
      if (err.error && typeof err.error === 'string') {
        errorMessage = err.error;
      } else if (err.message) {
        errorMessage = err.message;
      }
      alert(errorMessage); // Affiche l'erreur à l'utilisateur
    }
  });
}
}