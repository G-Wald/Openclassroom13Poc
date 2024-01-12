import { Component, OnInit } from '@angular/core';
import { ChatService } from '../services/chat.service';
import { ChatMessage } from '../models/ChatMessage';


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  messages: string[] = [];
  newMessage: string = '';

  constructor(private chatService: ChatService) { }

  ngOnInit() {
    this.loadMessages();
  }

  loadMessages() {
    this.chatService.getMessages(new ChatMessage ("taataa","sav", " ")).subscribe(messages => {
      this.messages = messages;
    });
  }

  sendMessage() {
    if (this.newMessage.trim() !== '') {
      console.log(new ChatMessage ("taataa","sav", this.newMessage))
      this.chatService.sendMessage(new ChatMessage ("taataa","sav", this.newMessage)).subscribe((messages) => {
        this.newMessage = '';
      this.messages = messages;
      });
    }
  }
}
