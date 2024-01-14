// auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:3000/api'; 

  public username : String = "";

  constructor(private http: HttpClient) {}

  login(credentials: { usernameOrEmail: string; password: string }): Observable<any> {
    // Appel à votre API pour l'authentification
    return this.http.post(`${this.apiUrl}/login`, credentials)
    .pipe(
        map((response: any) => 
        this.username = response.username)
    );
  }


  isSAVUser(): boolean {
    return this.username === 'sav';
  }
}
