package br.com.test.slacktest.service;

import br.com.test.slacktest.DTO.ConversationDTO;
import br.com.test.slacktest.DTO.SendMessageDTO;
import br.com.test.slacktest.DTO.UserDTO;
import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Conversation;
import com.slack.api.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;


import static com.slack.api.model.block.Blocks.actions;
import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.*;

@Service
public class SlackService {
    @Value("${SLACK_BOT_TOKEN}")
    private String token;
    @Value("${SLACK_SIGNING_SECRET}")
    private String signinSecret;

    //GET instances
    MethodsClient client = Slack.getInstance().methods();
    Logger logger = LoggerFactory.getLogger("test-app");

    //POST instances
    public AppConfig configApp(){
        var config = new AppConfig();
        config.setSingleTeamBotToken(token);
        config.setSigningSecret(signinSecret);
        return config;
    }

    public List<String> fetchConversationsList() {
        List<String> conversationsList = new ArrayList<>();
        try {
            var result = client.conversationsList(r -> r.token(token));

            for (Conversation conversation : result.getChannels()) {
                conversationsList.add(conversation.getName());
            }
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }

        return conversationsList;
    }

    public List<String> fetchUsersList() {
        List<String> usersList = new ArrayList<>();

        try {
            var result = client.usersList(r -> r
                    .token(token)
            );
            for (User user : result.getMembers()) {
                usersList.add(user.getName());
            }
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }
        return usersList;
    }

    public String findUserId(String username) {
        try {
            var result = client.usersList(r -> r.token(token));

            for (User user : result.getMembers()) {
                if(user.getName().equals(username)) return user.getId();
            }
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }
        return "Não Encontrado";
    }

    public String findConversationId(String channelName) {
        try {
            var result = client.conversationsList(r -> r.token(token));

            for (Conversation conversation : result.getChannels()) {
                if(conversation.getName().equals(channelName)) return conversation.getId();
            }
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }
        return "Não Encontrado";
    }

    public void sendMessageToSlack(SendMessageDTO sendMessageDTO) throws SlackApiException, IOException {
        App app = new App(configApp());

        app.client().chatPostMessage(r -> r
                .channel(sendMessageDTO.getId())
                .text(sendMessageDTO.getMessage() + " <@U067PL14NCB>")
        );
    }

    public void sendButtonMessageToSlack(SendMessageDTO sendMessageDTO) throws SlackApiException, IOException {
        var app = new App(configApp());

        app.client().chatPostMessage(r -> r
                .channel(sendMessageDTO.getId())
                .text("Button")
                .blocks(asBlocks(
                        actions(actions -> actions
                                .elements(asElements(
                                        button(b -> b
                                                .text(plainText(pt -> pt.text(sendMessageDTO.getMessage())))
                                                .url("https://www.google.com"))
                                )))
                ))
        );
    }

    public void sendMessageToUsername(UserDTO userDTO) throws SlackApiException, IOException {
        String id = findUserId(userDTO.getName());

        sendMessageToSlack(new SendMessageDTO(id, userDTO.getMessage()));
    }

    public void sendMessageToConversation(ConversationDTO conversationDTO) throws SlackApiException, IOException {
        String id = findConversationId(conversationDTO.getName());

        sendMessageToSlack(new SendMessageDTO(id, conversationDTO.getMessage()));
    }
}
