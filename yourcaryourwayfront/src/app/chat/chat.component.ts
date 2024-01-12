import { Component, OnInit, OnDestroy } from '@angular/core';
import { ChatMessage } from '../models/ChatMessage';
import { switchMap, takeUntil, interval, Subject} from 'rxjs';
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

  constructor(private chatService: ChatService) { }

  ngOnInit() {
    
    this.chatService.getMessagesObservable().subscribe((messages) => {
    this.messages = messages;
  });
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
