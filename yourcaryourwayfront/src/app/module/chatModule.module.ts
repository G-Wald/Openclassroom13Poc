import {ModuleWithProviders, NgModule} from '@angular/core';
import { ChatService } from '../services/chat.service';

@NgModule({
})
class ChatModule {

    public static forRoot(): ModuleWithProviders<ChatModule> {
        return {
            ngModule: ChatModule,
            providers: [
                ChatService,
            ],
        };
    }
}

export {ChatModule};