package com.epam.training.gen.ai.support.config;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.epam.training.gen.ai.support.plugin.SemanticPlugin;
import com.epam.training.gen.ai.util.Util;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.InvocationReturnMode;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.plugin.KernelPluginFactory;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class SemanticKernelConfig {
    
    @Bean OpenAIAsyncClient openAIAsyncClient(@Value("${client-openai-key}") String credential, @Value("${client-openai-endpoint}") String endpoint) {
        return new OpenAIClientBuilder()
                .credential(new AzureKeyCredential(credential))
                .endpoint(endpoint)
                .buildAsyncClient();
    }

    @Bean InvocationContext invocationContext(ObjectMapper objectMapper) {
        return InvocationContext.builder()
            .withReturnMode(InvocationReturnMode.LAST_MESSAGE_ONLY)
            .withToolCallBehavior(ToolCallBehavior.allowAllKernelFunctions(true))
            .withPromptExecutionSettings(PromptExecutionSettings.builder()
                .withTemperature(1.0)
                .withMaxTokens(4096)
                .build())
            .build();
    }

    @Bean Function<String, Kernel> semanticKernelProivder(OpenAIAsyncClient openAIAsyncClient, Collection<SemanticPlugin> plugins) {
        Map<String, Kernel> modelSpecificChatCompletionServices = new ConcurrentHashMap<>();
        var kernelPlugins = plugins.stream()
            .map(p -> KernelPluginFactory.createFromObject(p, p.getClass().getSimpleName()))
            .toList();

        return modelId -> modelSpecificChatCompletionServices.computeIfAbsent(
            modelId,
            $_modelId ->
                Util.tap(
                    Kernel.builder()
                        .withAIService(
                            ChatCompletionService.class,
                            OpenAIChatCompletion.builder()
                                .withModelId($_modelId)
                                .withOpenAIAsyncClient(openAIAsyncClient)
                                .build()
                        ),
                    k -> kernelPlugins.forEach(k::withPlugin)
                ).build()
        );
    }

}
