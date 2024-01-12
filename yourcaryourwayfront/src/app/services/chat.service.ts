import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ChatMessage } from '../models/ChatMessage';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private apiUrl = 'http://localhost:8080/api'; // Remplacez ceci par l'URL de votre API

  constructor(private http: HttpClient) { }

  getMessages(message: ChatMessage): Observable<string[]> {
    return this.http.post<string[]>(`${this.apiUrl}/messages`,  message );
  }

  sendMessage(message: ChatMessage): Observable<string[]> {
    return this.http.post<string[]>(`${this.apiUrl}/sendmessage`, message );
  }
}