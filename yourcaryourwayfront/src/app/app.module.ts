import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ChatComponent } from './chat/chat.component';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.routing';
import { HttpClientModule } from '@angular/common/http';
import { ChatService } from './services/chat.service';
import { AuthService } from './services/auth.service';

@NgModule({
  declarations: [
    AppComponent,
    ChatComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [AuthService,ChatService],
  bootstrap: [AppComponent]
})
export class AppModule { }
