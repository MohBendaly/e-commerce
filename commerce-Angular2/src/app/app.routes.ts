import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { Home } from './pages/home/home';
import { login } from './pages/login/login';
import { Register } from './pages/register/register';
import { Products } from './pages/products/products';
import { cartItems } from './pages/cart/cart';
import { ChatComponent } from './chatbot/chatbot';
import { Order } from './pages/order/order';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: Home },
  { path: 'login', component: login },
  { path: 'register', component: Register },
  { path: 'produits/all', component: Products },
  { path: 'products', component: Products },
  { path: 'cart', component: cartItems },
  { path: 'chat', component: ChatComponent },
  { path: '**', redirectTo: '/home' },
  { path: 'order', component: Order },
  { path: 'order/:id', component: Order },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
