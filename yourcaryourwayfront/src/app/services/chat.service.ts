import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject} from 'rxjs';
import { ChatMessage } from '../models/ChatMessage';
import { Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private apiUrl = 'http://localhost:3000/api';

  private stompClient: any

  constructor(private http: HttpClient, private authService : AuthService) {
    this.initializeWebSocketConnection();
  }

  private initializeWebSocketConnection() {
    const url = '//localhost:3000/chat-socket';
    const socket = new SockJS(url);
    this.stompClient = Stomp.over(socket)
    }

  public messagesSubject: BehaviorSubject<ChatMessage> = new BehaviorSubject<ChatMessage>(new ChatMessage("","",""));


  handleIncomingMessage() {
    this.stompClient.connect({}, ()=>{
      let destination: string;
      if (this.authService.isSAVUser()) {
        destination = '/topic/messages';
      } else {
        const username = this.authService.username;
        destination = `/topic/${username}/messages`;
      }

      this.stompClient.subscribe(destination, (messages: any) => {
        const messageContent = JSON.parse(messages.body);
        const chatMessage = new ChatMessage(
          messageContent.senderUsername,
          messageContent.receiverUsername,
          messageContent.messageText
        );
        this.messagesSubject.next(chatMessage);
      })
    },
    (error : any) => {
      console.error('WebSocket Error:', error);
    }
    )
  }

  sendMessage(message: ChatMessage): void {
    this.stompClient.send(`/app/chat`, {}, JSON.stringify(message));
  }

  loadMessages(message: ChatMessage) {
    return this.http.post<ChatMessage[]>(`${this.apiUrl}/messages`, message);
  }
}