public class MessageModel {
    private String sender;
    private String recipient;
    private String messageBody;
    private String timestamp;
    private MessageType messageType;

    public MessageModel(String sender, String recipient, String messageBody, String timestamp, MessageType messageType) {
        this.sender = sender;
        this.recipient = recipient;
        this.messageBody = messageBody;
        this.timestamp = timestamp;
        this.messageType = messageType;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public enum MessageType {
        INBOX,
        SENT
    }
}
