export class ChatMessage {
    SenderUsername: string;
    ReceiverUsername: string;
    MessageText: string;

    constructor(SenderUsername: string, ReceiverUsername : string, MessageText : string) {
        this.SenderUsername = SenderUsername;
        this.ReceiverUsername = ReceiverUsername;
        this.MessageText = MessageText;
    }
}