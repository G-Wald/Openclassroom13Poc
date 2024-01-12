import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject, tap } from 'rxjs';
import { ChatMessage } from '../models/ChatMessage';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private apiUrl = 'http://localhost:8080/api'; 

  constructor(private http: HttpClient) { }

  private messagesSubject: Subject<ChatMessage[]> = new Subject<ChatMessage[]>();

  getMessages(message: ChatMessage): Observable<ChatMessage[]> {
    return this.http.post<ChatMessage[]>(`${this.apiUrl}/messages`, message)
    .pipe(
      tap(messages => this.messagesSubject.next(messages)));
  }

  sendMessage(message: ChatMessage): void {
    this.http.post<ChatMessage[]>(`${this.apiUrl}/sendmessage`, message).pipe(
      tap(() => this.getMessages(message).subscribe())
    );
    
  }

  getMessagesObservable(): Observable<ChatMessage[]> {
    return this.messagesSubject.asObservable();
  }
}

