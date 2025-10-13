package com.guilherme.api_ia.memory;

import com.guilherme.api_ia.chat.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoryChatService {

  private static final Logger log = LoggerFactory.getLogger(MemoryChatService.class);
  private final ChatClient chatClient;

  private static final String DescriptionPrompt = "Generat a chat description based on the message, limiting the description to 30 characters";
  private final MemoryChatRepository memoryChatRepository;
  private static final String user_id = "guilherme";


  public MemoryChatService(ChatClient.Builder chatClientBuilder, JdbcChatMemoryRepository jdbcChatMemoryRepository,
                           MemoryChatRepository memoryChatRepository ){
    this.memoryChatRepository = memoryChatRepository;

    ChatMemory chatMemory = MessageWindowChatMemory.builder()
      .chatMemoryRepository(jdbcChatMemoryRepository)
      .maxMessages(10)
      .build();

    this.chatClient = chatClientBuilder
      .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build(),
        new SimpleLoggerAdvisor())
      .build();
  }

  public String chat(String message, String chatId) {
    return this.chatClient.prompt()
      .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, "123456"))
      .user(message)
      .call()
      .content();
  }
  public List<Chat> getAllChats() {
    return this.memoryChatRepository.getAllChatsForUser(user_id);
  }

  public NewChatResponse createNewChat(String message){
    String description = generateDescription(message);
    String chatId = this.memoryChatRepository.generateChatId("user_id", description);
    String response = this.chat(message, chatId);
    return new NewChatResponse(chatId, description, response);

  }

  private String generateDescription(String message){
    return this.chatClient.prompt()
      .user(DescriptionPrompt + message)
      .call()
      .content();
  }
  public List<ChatMessage> getChatMessages(String userId) {
    return this.memoryChatRepository.getChatMessages(userId);
  }
}
