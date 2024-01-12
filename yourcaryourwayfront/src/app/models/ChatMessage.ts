export class ChatMessage {
    senderUsername: string;
    receiverUsername: string;
    messageText: string;

    constructor(senderUsername: string, receiverUsername : string, messageText : string) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.messageText = messageText;
    }
}