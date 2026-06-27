import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './order.html',
  styleUrls: ['./order.css']
})
export class Order implements OnInit {
  order: any = null;
  loading = true;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient
  ) {}

  ngOnInit() {
    const orderId = this.route.snapshot.paramMap.get('id');
    if (orderId) {
      this.http.get(`http://localhost:8080/order/${orderId}`).subscribe({
        next: (data) => {
          this.order = data;
          this.loading = false;
          console.log(' Détails de la commande :', this.order);
        },
        error: (err) => {
          this.error = 'Erreur lors du chargement de la commande';
          this.loading = false;
          console.error(err);
        }
      });
    }
  }
  loadOrder() {
  this.loading = true;
  const orderId = this.route.snapshot.paramMap.get('id');
  if (orderId) {
    this.http.get(`http://localhost:8080/order/${orderId}`).subscribe({
      next: (data: any) => {
        this.order = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement de la commande';
        this.loading = false;
        console.error(err);
      }
    });
  }
}
loadAllOrders() {
    this.http.get('http://localhost:8080/order').subscribe({
      next: (data: any) => {
        this.order = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des commandes';
        this.loading = false;
      }
    });
  }
}
