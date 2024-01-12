import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, switchMap} from 'rxjs';
import { ChatMessage } from '../models/ChatMessage';
import { interval} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private apiUrl = 'http://localhost:8080/api'; 

  constructor(private http: HttpClient) {
    /*interval(1000)
      .pipe().subscribe(()=>{
        console.log(this.messagesSubject);

      });*/
      this.getMessages(new ChatMessage("tata","sav",""));
    
    }

  public messagesSubject: BehaviorSubject<ChatMessage[]> = new BehaviorSubject<ChatMessage[]>([]);
  
  testMessage: ChatMessage[] = [];

  getMessages(message: ChatMessage) {
    this.http.post<ChatMessage[]>(`${this.apiUrl}/messages`, message).subscribe(messages =>
      {
        this.messagesSubject.next(messages);
      });
  }

  getMessagesObservable(): Observable<ChatMessage[]> {
    return this.messagesSubject.asObservable();
  }

  sendMessage(message: ChatMessage): void {
    this.http.post<ChatMessage[]>(`${this.apiUrl}/sendmessage`, message).subscribe((messages) => {
      this.messagesSubject.next(messages);
      //this.getMessages(message);
    })
  }
}

