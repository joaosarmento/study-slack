package br.com.test.slacktest.DTO;

public class SendMessageDTO {

    String id;
    String message;

    public SendMessageDTO(String id, String message){
        this.id = id;
        this. message = message;
    }

    public void setId(String id) { this.id = id; }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }
}
