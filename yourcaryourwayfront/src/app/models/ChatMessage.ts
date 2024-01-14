export class ChatMessage {
    senderUsername: String;
    receiverUsername: String;
    messageText: String;

    constructor(senderUsername: String, receiverUsername : String, messageText : String) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.messageText = messageText;
    }
}