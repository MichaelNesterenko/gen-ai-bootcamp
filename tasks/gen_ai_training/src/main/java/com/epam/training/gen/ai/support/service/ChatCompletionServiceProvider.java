package com.epam.training.gen.ai.support.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;

import jakarta.annotation.Resource;

@Service
public class ChatCompletionServiceProvider {
    @Resource OpenAIAsyncClient openAIAsyncClient;
    
    final Map<String, ChatCompletionService> modelSpecificChatCompletionServices = new ConcurrentHashMap<>();

    public ChatCompletionService getChatCompletionService(String modelId) {
        return modelSpecificChatCompletionServices.computeIfAbsent(
            modelId,
            $_modelId ->
                OpenAIChatCompletion.builder()
                .withModelId($_modelId)
                .withOpenAIAsyncClient(openAIAsyncClient)
                .build()
        );
    }
}
