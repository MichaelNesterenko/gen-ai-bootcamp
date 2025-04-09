package com.epam.training.gen.ai.chat.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;

import jakarta.annotation.Resource;
import lombok.Data;

import static com.epam.training.gen.ai.util.Util.CheckedFunction;
import static com.epam.training.gen.ai.util.Util.tap;
import static com.epam.training.gen.ai.util.Util.asUnchecked;;

@RestController
public class ChatController {
    
    @Resource InvocationContext semanticContext;

    @Resource Function<String, Kernel> semanticKernelProivder;

    final AtomicInteger sessionCounter = new AtomicInteger();
    final ConcurrentMap<Integer, ChatHistory> chatSession = new ConcurrentHashMap<>();

    @PostMapping("/chat") @ResponseStatus(code = HttpStatus.CREATED) ChatSessionMessage newChatSession() {
        var chatId = sessionCounter.getAndIncrement();

        chatSession.put(chatId, new ChatHistory());

        return tap(new ChatSessionMessage(), session -> session.setId(chatId));
    }
    
    @PostMapping("/chat/{modelId}/{chatId}/message") ResponseEntity<Object> newChatMessage(
        @PathVariable String modelId,
        @PathVariable int chatId,
        @RequestBody ConversationInputMessage input
    ) {
        return asChatSessionInteractionResponse(
            withChatSessionLock(chatId, chatSession -> {
                chatSession.addUserMessage(input.getMessage());

                var kernel = semanticKernelProivder.apply(modelId);
                var response = kernel.getService(ChatCompletionService.class)
                    .getChatMessageContentsAsync(chatSession, kernel, semanticContext)
                    .block();
                response.forEach(chatSession::addMessage);

                return tap(
                    new ConversationResponseMessage(),
                    message -> message.setMessage(
                        response.stream()
                            .map(ChatMessageContent::getContent)
                            .collect(Collectors.joining("\n"))
                    )
                );
            })
        );
    }

    @GetMapping("/chat/{id}/history") ResponseEntity<Object> retrieveChatSession(@PathVariable int id) {
        return asChatSessionInteractionResponse(
            withChatSessionLock(id, chatSession -> tap(new ChatSessionMessage(), message -> {
                message.setId(id);
                message.setMessages(
                    chatSession.getMessages().stream()
                        .map(in -> tap(new ChatSessionMessage.MessageEntryMessage(), out -> {
                            out.setType(in.getAuthorRole().toString());
                            out.setContents(in.getContent());
                        }))
                        .toList()
                    );
            }))
        );
    }

    @SuppressWarnings("unchecked")
    private ResponseEntity<Object> asChatSessionInteractionResponse(Optional<?> input) {
        return (ResponseEntity<Object>) input.<ResponseEntity<?>>map(ResponseEntity.ok()::body)
            .orElseGet(this::sessionNotFoundMessage);
    }

    @SuppressWarnings("unchecked")
    private <T> Optional<T> withChatSessionLock(int id, CheckedFunction<ChatHistory, T> action) {
        var response = new Object[1];

        chatSession.compute(id, ($, chatSession) -> {
            response[0] = Optional.ofNullable(chatSession).map(asUnchecked(action));
            return chatSession;
        });

        return (Optional<T>) response[0];
    }

    private ResponseEntity<ErrorMessage> sessionNotFoundMessage() {
        return ResponseEntity.badRequest().body(tap(new ErrorMessage(), error -> error.setMessage("session not found")));
    }

    @Data static class ChatSessionMessage {
        int id;
        List<MessageEntryMessage> messages = new ArrayList<>();

        @Data static class MessageEntryMessage {
            String type;
            String contents;
        }
    }

    @Data static class ConversationInputMessage {
        String message;
    }
    @Data static class ConversationResponseMessage {
        String message;
    }

    @Data static class ErrorMessage {
        String message;
    }

}
