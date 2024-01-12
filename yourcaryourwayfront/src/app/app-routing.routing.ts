import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { ChatComponent } from './chat/chat.component';

//Add guards
const routes: Routes = [
  { path: '', component: ChatComponent   }
];

@NgModule({
    imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}