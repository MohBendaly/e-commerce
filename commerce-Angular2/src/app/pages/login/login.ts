import { Input, Component, Output, EventEmitter, OnInit, inject , CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import { FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { HeaderComponent } from '../../components/header/header';


@Component({
   schemas: [CUSTOM_ELEMENTS_SCHEMA],
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HeaderComponent,
  ],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class login implements OnInit {
  public apiUrl = 'http://localhost:8080/api/auth/login';
  
  private http = inject(HttpClient);
  private router = inject(Router);

  form: FormGroup = new FormGroup({
    email: new FormControl(''),
    password: new FormControl(''),
  });

  @Input() error: string = '';
  @Output() submitEM = new EventEmitter<void>();

  constructor() {}

  ngOnInit() {}

  submit() {
    if (this.form.invalid) return;
    
    this.http.post<any>(this.apiUrl, this.form.value).subscribe({
      next: (response: any) => {
        if (response.token) {
          localStorage.setItem('token', response.token);
        }
        this.router.navigate(['/produit/all']);
      },
      error: (err: any) => {
        this.error = 'Email ou mot de passe incorrect';
      }
    });
  }
}