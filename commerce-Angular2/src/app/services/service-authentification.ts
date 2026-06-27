import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class ServiceAuthentification {
  private apiUrl = 'http://localhost:8080/api/auth';
  
  constructor(private http: HttpClient) {}

  login(data: any) {
    return this.http.post(this.apiUrl + '/login', data);
  }

  register(data: any) {
    return this.http.post(this.apiUrl + '/register', data);
  }

  logout(): void {
    localStorage.removeItem('token');
  }
}

