import { Component, OnInit, OnDestroy } from '@angular/core';
import { ChatMessage } from '../models/ChatMessage';
import { switchMap, takeUntil, interval, Subject, Observer, Observable, pipe} from 'rxjs';
import { ChatService } from '../services/chat.service';
import { AuthService } from '../services/auth.service';



@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {
  messages: ChatMessage[] = [];
  conversations: { [username: string]: ChatMessage[] } = {}; 
  newMessage: string = '';
  private destroy$: Subject<void> = new Subject<void>();

  constructor(private chatService: ChatService, public authService : AuthService) { 
    this.chatService.messagesSubject.subscribe((messages)=>
    {
      if(this.authService.isSAVUser()){
        messages.forEach((message) => {
          const conversationKey = this.getConversationKey(message);
          if (!this.conversations[conversationKey]) {
            this.conversations[conversationKey] = [];
          }
          this.conversations[conversationKey].push(message);
        })
      }else{
        this.messages.push(...messages)
      }
    })
  }

  ngOnInit() {
    this.chatService.loadMessages(new ChatMessage ("tata","sav", this.newMessage)).subscribe((messages) => 
    {
    if(this.authService.isSAVUser()){
      messages.forEach((message) => {
        const conversationKey = this.getConversationKey(message);
        if (!this.conversations[conversationKey]) {
          this.conversations[conversationKey] = [];
        }
        this.conversations[conversationKey].push(message);
      })
    }else{
      this.messages = messages
    }
  });
    this.chatService.handleIncomingMessage();
  }

  sendMessage(receiver : String) {
    if (this.newMessage.trim() !== '') {
      this.chatService.sendMessage(new ChatMessage (this.authService.username, receiver, this.newMessage));
      this.newMessage = '';
    }
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  getConversationKey(message: ChatMessage): string {
    if(message.senderUsername === "sav"){
      return `${message.receiverUsername}`;
    }
    return `${message.senderUsername}`
  }

}
