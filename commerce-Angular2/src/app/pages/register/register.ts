import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, HttpClientModule, RouterModule], 
  templateUrl: './register.html',
  styleUrls: ['./register.css']
})

export class Register implements OnInit { 
  registerForm: FormGroup = new FormGroup({});
  errorMessage = '';
  isSubmitting = false;
  private apiUrl = 'http://localhost:8080/api/auth/register';
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private router = inject(Router);

  constructor() {}

  ngOnInit() {
    this.registerForm = this.fb.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['USER', Validators.required]
    });
  }

  onSubmit() {
    if (this.registerForm.invalid || this.isSubmitting) {
      if (this.registerForm.invalid) {
        this.errorMessage = 'Veuillez compléter tous les champs correctement.';
      }
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';

    this.http.post(this.apiUrl, this.registerForm.value).subscribe({
      next: (response) => {
        console.log('Succès', response);
        this.isSubmitting = false;
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error(err);
        this.isSubmitting = false;
        this.errorMessage = err?.error?.message || 'Erreur lors de l\'inscription';
      }
    });
  }
}