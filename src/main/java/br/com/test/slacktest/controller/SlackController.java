package br.com.test.slacktest.controller;

import br.com.test.slacktest.DTO.ConversationDTO;
import br.com.test.slacktest.DTO.SendMessageDTO;
import br.com.test.slacktest.DTO.UserDTO;
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
    public String getUserIdByName(@RequestBody UserDTO userDTO){
        return slackService.findUserId(userDTO.getName());
    }

    @GetMapping("/conversation-id")
    public String getConversationIdByName(@RequestBody ConversationDTO conversationDTO){
        return slackService.findConversationId(conversationDTO.getName());
    }

    @PostMapping("/message")
    public void sendMessage(@RequestBody SendMessageDTO sendMessageDTO) throws SlackApiException, IOException {
        slackService.sendMessageToSlack(sendMessageDTO);
    }

    @PostMapping("/button")
    public void sendButtonMessage(@RequestBody SendMessageDTO sendMessageDTO) throws SlackApiException, IOException {
        slackService.sendButtonMessageToSlack(sendMessageDTO);
    }

    @PostMapping("/message-to-user")
    public void sendMessageToUsername(@RequestBody UserDTO userDTO) throws SlackApiException, IOException {
        slackService.sendMessageToUsername(userDTO);
    }

    @PostMapping("/message-to-conversation")
    public void sendMessageToConversation(@RequestBody ConversationDTO conversationDTO) throws SlackApiException, IOException {
        slackService.sendMessageToConversation(conversationDTO);
    }
}
