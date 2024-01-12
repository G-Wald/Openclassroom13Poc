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
  test : Observable<ChatMessage[]>
  private destroy$: Subject<void> = new Subject<void>();

  constructor(private chatService: ChatService) { 
    this.test = this.chatService.GetObs();
    this.test.subscribe({next : (value) => this.messages = value})
  }

  ngOnInit() {
    
    this.chatService.loadMessages(new ChatMessage ("tata","sav", this.newMessage))
    .subscribe({next : (messages) => this.messages = messages});
    console.log(this.test)
    //this.test.subscribe((te) => console.log(te))
    interval(1000).pipe().subscribe((updatedMessages) => {
      console.log('Updated messages:', this.chatService.messagesSubject.getValue());
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
