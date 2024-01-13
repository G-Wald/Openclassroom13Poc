import { Component, OnInit, OnDestroy } from '@angular/core';
import { ChatMessage } from '../models/ChatMessage';
import { switchMap, takeUntil, interval, Subject, Observer, Observable, pipe} from 'rxjs';
import { ChatService } from '../services/chat.service';



@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {
  messages: ChatMessage[] = [];
  newMessage: string = '';
  private destroy$: Subject<void> = new Subject<void>();

  constructor(private chatService: ChatService) { 
    this.chatService.messagesSubject.subscribe((messages)=>(this.messages = messages))
  }

  ngOnInit() {
    this.chatService.loadMessages(new ChatMessage ("tata","sav", this.newMessage)).subscribe((messages) => this.messages = messages);
    this.chatService.handleIncomingMessage();
  }

  sendMessage() {
    if (this.newMessage.trim() !== '') {
      this.chatService.sendMessage(new ChatMessage ("tata","sav", this.newMessage));
      this.newMessage = '';
    }
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
