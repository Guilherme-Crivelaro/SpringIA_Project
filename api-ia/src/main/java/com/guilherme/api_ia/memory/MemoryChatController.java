package com.guilherme.api_ia.memory;

import com.guilherme.api_ia.chat.ChatMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-memory")
public class MemoryChatController {

  private final MemoryChatService memoryChatService;

  public MemoryChatController(MemoryChatService memoryChatService) {
    this.memoryChatService = memoryChatService;
  }

  @PostMapping("/{chatId}")
  ChatMessage simpleChat(@PathVariable String chatId, @RequestBody ChatMessage message) {
    var response = this.memoryChatService.chat(message.message(), chatId);
    return new ChatMessage(response, "ASSISTANT");
  }

  @GetMapping("/{chatId}")
  public List<ChatMessage> getChatMessages(@PathVariable String chatId) {
    return this.memoryChatService.getChatMessages(chatId);
  }

  @PostMapping("/start")
  NewChatResponse startNewChat(@RequestBody ChatMessage message ){
      return this.memoryChatService.createNewChat(message.message());
  }

  @GetMapping
  public List<Chat> getAllChats() {
    return this.memoryChatService.getAllChats();
  }

}
