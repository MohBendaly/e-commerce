import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../../components/header/header';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, HeaderComponent],
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
})
export class Home {
  searchQuery = '';

  categories = [
    { id: 1, titre: 'vêtement', description: 'Tenues et accessoires tendance', produits: [1, 2, 3, 4] },
    { id: 2, titre: 'console nintendo', description: 'Consoles et jeux Nintendo populaires', produits: [5, 6] },
    { id: 3, titre: 'console playstation', description: 'Jeux et accessoires PlayStation', produits: [2] },
  ];

  featuredProducts = [
    { id: 1, nom: 'chaussure ', description: 'Chaussures légères et modernes.', prix: 49.99, imageUrl: 'chaussure.jpg' },
    { id: 2, nom: 'wiiu', description: 'un console de jeu', prix: 79.99, imageUrl: 'wiiu.jpg' },
    { id: 3, nom: 'wii', description: 'console de jeu', prix: 24.99, imageUrl: 'Wii.png' },
    {id: 4, nom: 'ps2', description: 'console de jeu', prix: 19.99, imageUrl: 'ps2.jpg' },
  ];

  filteredCategories = [...this.categories];
  filteredProducts = [...this.featuredProducts];

  constructor(private router: Router) {}

filterByCategory(categoryId: number, productIds: number[]): void {
  this.router.navigate(['/produits/all'], { 
    queryParams: { ids: productIds.join(',') } 
  });
}

  onSearch() {
  const query = this.searchQuery.trim().toLowerCase();
  if (!query) {
    this.filteredCategories = [...this.categories];
    this.filteredProducts = [...this.featuredProducts];
    return;
  }

  this.filteredCategories = this.categories.filter(cat =>
    cat.titre.toLowerCase().includes(query)
  );

  // ✅ Correction : filtrer les produits par nom ou description
  this.filteredProducts = this.featuredProducts.filter(product =>
    product.nom.toLowerCase().includes(query) ||
    product.description.toLowerCase().includes(query)
  );
}

  heroTitre = 'Bienvenue sur notre boutique';
  heroSousTitre = 'Trouvez rapidement vos produits préférés et profitez des meilleures offres.';
  heroBoutonTexte = 'Voir tous les produits';
  heroBoutonRoute = '/produits/all';

  navLinks = [
    { label: 'Accueil', route: '/home' },
    { label: 'Panier', route: '/cart' },
    { label: 'Chat', route: '/chat' },
    { label: 'Connexion', route: '/login' },
    { label: 'Inscription', route: '/register' },
  ];

  searchPlaceholder = 'Chercher un produit...';
}
