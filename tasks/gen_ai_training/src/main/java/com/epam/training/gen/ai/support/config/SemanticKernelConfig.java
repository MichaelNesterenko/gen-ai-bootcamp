package com.epam.training.gen.ai.support.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.InvocationReturnMode;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;

@Configuration
public class SemanticKernelConfig {
    
    @Bean OpenAIAsyncClient openAIAsyncClient(@Value("${client-openai-key}") String credential, @Value("${client-openai-endpoint}") String endpoint) {
        return new OpenAIClientBuilder()
                .credential(new AzureKeyCredential(credential))
                .endpoint(endpoint)
                .buildAsyncClient();
    }

    @Bean InvocationContext invocationContext() {
        return InvocationContext.builder()
            .withReturnMode(InvocationReturnMode.LAST_MESSAGE_ONLY)
            .withPromptExecutionSettings(PromptExecutionSettings.builder()
                .withTemperature(1.0)
                .withMaxTokens(4096)
                .build())
            .build();
    }

}
