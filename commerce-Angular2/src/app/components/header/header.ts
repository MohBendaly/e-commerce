import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd, RouterModule } from '@angular/router';
import { ServiceAuthentification } from '../../services/service-authentification';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.html',
  styleUrls: ['./header.css'],
})
export class HeaderComponent {
  currentUrl = '';

  links = [
    { label: 'Accueil', route: '/home' },
    { label: 'Produits', route: '/produit/all', hideOnHome: true },
    { label: 'Panier', route: '/cart' },
    { label: 'Chat', route: '/chat' },
    { label: 'Connexion', route: '/login' },
    { label: 'Inscription', route: '/register' },
  ];

  constructor(private router: Router, private auth: ServiceAuthentification) {
    this.currentUrl = this.router.url;
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.currentUrl = event.urlAfterRedirects;
      }
    });
  }

  get isHome(): boolean {
    return this.currentUrl === '/home' || this.currentUrl === '/';
  }

  get isLogin(): boolean {
    return this.currentUrl === '/login';
  }

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

  get displayedLinks() {
    return this.links.filter(link => !link.hideOnHome || !this.isHome);
  }
}
