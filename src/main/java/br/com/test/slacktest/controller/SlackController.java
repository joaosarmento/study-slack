package br.com.test.slacktest.controller;

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

    @PostMapping("/message")
    public void sendMessage() throws SlackApiException, IOException {
        slackService.sendMessageToSlack();
    }

    @PostMapping("/button")
    public void sendButtonMessage() throws SlackApiException, IOException {
        slackService.sendButtonMessageToSlack();
    }
}
