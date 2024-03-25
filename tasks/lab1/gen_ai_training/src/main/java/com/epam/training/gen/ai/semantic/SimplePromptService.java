package com.epam.training.gen.ai.semantic;

import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.epam.training.gen.ai.semantic.client.OpenAIAsyncClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SimplePromptService {

    public static final String GREETING_MESSAGE = "Say hi";

    private final OpenAIAsyncClientService aiClientService;
    private final String deploymentOrModelName;

    public SimplePromptService(OpenAIAsyncClientService aiClientService,
                               @Value("${client-azureopenai-deployment-name}")  String deploymentOrModelName) {
        this.aiClientService = aiClientService;
        this.deploymentOrModelName = deploymentOrModelName;
    }

    public List<String> getChatCompletions() {

        ChatCompletions completions = aiClientService.get()
                .getChatCompletions(
                        deploymentOrModelName,
                        new ChatCompletionsOptions(
                                List.of(new ChatRequestUserMessage(GREETING_MESSAGE))))
                .block();
        List<String> messages = completions.getChoices().stream()
                .map(c -> c.getMessage().getContent())
                .collect(Collectors.toList());
        log.info(messages.toString());
        return messages;
    }
}


