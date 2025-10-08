package com.guilherme.api_ia;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public MyController(@NotNull ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.build();
    }
}
