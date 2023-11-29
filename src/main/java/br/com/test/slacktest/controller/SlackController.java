package br.com.test.slacktest.controller;

import br.com.test.slacktest.DTO.SendMessageDTO;
import br.com.test.slacktest.DTO.UsernameDTO;
import br.com.test.slacktest.service.SlackService;
import com.slack.api.methods.SlackApiException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SlackController {

    private final SlackService slackService;

    public SlackController(SlackService slackService) {
        this.slackService = slackService;
    }

    @GetMapping("/conversations-list")
    public List<String> fetchConversationsList(){
        return slackService.fetchConversationsList();
    }

    @GetMapping("/users-list")
    public List<String> fetchUsersList(){
        return slackService.fetchUsersList();
    }

    @GetMapping("/user-id")
    public String getUserIdByName(@RequestBody UsernameDTO usernameDTO){
        return slackService.findUserId(usernameDTO.getName());
    }

    @GetMapping("/conversations-id")
    public String getConversationIdByName(@RequestBody UsernameDTO usernameDTO){
        return slackService.findConversationId(usernameDTO.getName());
    }

    @PostMapping("/message")
    public void sendMessage(@RequestBody SendMessageDTO sendMessageDTO) throws SlackApiException, IOException {
        slackService.sendMessageToSlack(sendMessageDTO);
    }

    @PostMapping("/button")
    public void sendButtonMessage() throws SlackApiException, IOException {
        slackService.sendButtonMessageToSlack();
    }

    @PostMapping("/message-to-user")
    public void sendMessageToUsername(@RequestBody SendMessageDTO sendMessageDTO) throws SlackApiException, IOException {
        slackService.sendMessageToUsername(sendMessageDTO);
    }

    @PostMapping("/message-to-conversation")
    public void sendMessageToConversation(@RequestBody SendMessageDTO sendMessageDTO) throws SlackApiException, IOException {
        slackService.sendMessageToConversation(sendMessageDTO);
    }
}
