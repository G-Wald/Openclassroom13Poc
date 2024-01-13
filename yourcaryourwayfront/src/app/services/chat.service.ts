import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, switchMap} from 'rxjs';
import { ChatMessage } from '../models/ChatMessage';
import { Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private apiUrl = 'http://localhost:3000/api';

  private stompClient: any

  constructor(private http: HttpClient) {
    this.initializeWebSocketConnection();
  }

  private initializeWebSocketConnection() {
    const url = '//localhost:3000/chat-socket';
    const socket = new SockJS(url);
    this.stompClient = Stomp.over(socket)
    }

  public messagesSubject: BehaviorSubject<ChatMessage[]> = new BehaviorSubject<ChatMessage[]>([]);


  handleIncomingMessage() {
    this.stompClient.connect({}, ()=>{
      this.stompClient.subscribe(`/topic/messages`, (messages: any) => {
        const messageContent = JSON.parse(messages.body);
        const currentMessage = this.messagesSubject.getValue();
        currentMessage.push(messageContent);

        this.messagesSubject.next(currentMessage);

      })
    },
    (error : any) => {
      console.error('WebSocket Error:', error);
      // Handle connection error
    }
    
    
    )
  }

  sendMessage(message: ChatMessage): void {
    // Send the message through the WebSocket
    this.stompClient.send(`/app/chat`, {}, JSON.stringify(message));
  }

  loadMessages(message: ChatMessage) {
    return this.http.post<ChatMessage[]>(`${this.apiUrl}/messages`, message);
  }

}

