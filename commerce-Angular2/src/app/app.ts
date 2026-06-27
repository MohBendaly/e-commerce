import { Component, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { ServiceAuthentification } from './services/service-authentification';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class App {
  isLoggedIn = false;

   constructor(private authService: ServiceAuthentification, private router: Router) {
    this.isLoggedIn = !!localStorage.getItem('token');
  }
  protected readonly title = signal('commerce-Angular');
  messages: { role: string; content: string }[] = [];
   logout(): void {
    this.authService.logout();
    this.isLoggedIn = false;
    this.router.navigate(['/login']);
  }
}
