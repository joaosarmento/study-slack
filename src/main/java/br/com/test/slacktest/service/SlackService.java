package br.com.test.slacktest.service;

import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Conversation;
import com.slack.api.model.User;
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
    @Value("${token}")
    private String token;
    @Value("${signin_secret}")
    private String signinSecret;
    @Value("${channel_id}")
    private String channelId;

    public List<String> fetchConversationsList() {
        List<String> conversationsList = new ArrayList<>();
        var client = Slack.getInstance().methods();
        var logger = LoggerFactory.getLogger("test-app");
        try {
            var result = client.conversationsList(r -> r
                    .token(token)
            );
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
        var client = Slack.getInstance().methods();
        var logger = LoggerFactory.getLogger("test-app");
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

    public void sendMessageToSlack() throws SlackApiException, IOException {
        var config = new AppConfig();
        config.setSingleTeamBotToken(token);
        config.setSigningSecret(signinSecret);
        var app = new App(config);

        app.client().chatPostMessage(r -> r
                .channel(channelId)
                .text("Oi galera!")
        );
    }

    public void sendButtonMessageToSlack() throws SlackApiException, IOException {
        var config = new AppConfig();
        config.setSingleTeamBotToken(token);
        config.setSigningSecret(signinSecret);
        var app = new App(config);

        app.client().chatPostMessage(r -> r
                .channel(channelId)
                .text("Button")
                .blocks(asBlocks(
                        actions(actions -> actions
                                .elements(asElements(
                                        button(b -> b.text(plainText(pt -> pt.text("Google"))).url("https://www.google.com"))
                                )))
                ))
        );
    }
}
