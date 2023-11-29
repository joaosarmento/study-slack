package br.com.test.slacktest.DTO;

public class SendMessageDTO {

    String channel;
    String message;

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public String getMessage() {
        return message;
    }
}
