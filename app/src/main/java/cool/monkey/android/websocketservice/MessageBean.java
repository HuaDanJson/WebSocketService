package cool.monkey.android.websocketservice;

public class MessageBean {

    private int userId;
    private String message;
    private int messageId;
    private long createAt;
    private int sendUserId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public int getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(int sendUserId) {
        this.sendUserId = sendUserId;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "userId=" + userId +
                ", message='" + message + '\'' +
                ", messageId=" + messageId +
                ", createAt=" + createAt +
                '}';
    }
}
