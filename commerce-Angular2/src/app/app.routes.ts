import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { Home } from './pages/home/home';
import { login } from './pages/login/login';
import { Register } from './pages/register/register';
import { Product } from './pages/products/products';
import { cartItems } from './pages/cart/cart';
import { ChatComponent } from './chatbot/chatbot';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: Home },
  { path: 'login', component: login },
  { path: 'register', component: Register },
  { path: 'produit/all', component: Product },
  { path: 'products', component: Product },
  { path: 'cart', component: cartItems },
  { path: 'chat', component: ChatComponent },
  { path: '**', redirectTo: '/home' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
