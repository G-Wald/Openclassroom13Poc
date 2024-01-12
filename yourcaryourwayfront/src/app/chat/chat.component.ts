import { Component, OnInit } from '@angular/core';
import { ChatService } from '../services/chat.service';
import { ChatMessage } from '../models/ChatMessage';


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  messages: ChatMessage[] = [];
  newMessage: string = '';

  constructor(private chatService: ChatService) { }

  ngOnInit() {
    this.loadMessages();
  }

  loadMessages() {
    this.chatService.getMessagesObservable().subscribe(messages => {
      this.messages = messages;
    });

   
    this.chatService.getMessages(new ChatMessage ("tata","sav", "")).subscribe();
  }

  sendMessage() {
    if (this.newMessage.trim() !== '') {
      this.chatService.sendMessage(new ChatMessage ("tata","sav", this.newMessage));
      this.newMessage = '';
    }
  }
}
