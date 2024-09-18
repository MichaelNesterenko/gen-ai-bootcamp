package com.epam.training.gen.ai.semantic.client;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.microsoft.semantickernel.connectors.ai.openai.util.ClientType;
import com.microsoft.semantickernel.connectors.ai.openai.util.OpenAIClientProvider;
import com.microsoft.semantickernel.exceptions.ConfigurationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Configuration
public class OpenAIConfiguration {

    @Bean
    public OpenAIAsyncClient openAIAsyncClient(@Value("${client-azureopenai-key}") String clientAzureOpenAiKey,
                                               @Value("${client-azureopenai-endpoint}") String clientAzureOpenAiEndPoint,
                                               @Value("${client-azureopenai-deployment-name}") String clientAzureOpenAiDeploymentName) throws ConfigurationException {
        var configuredSettings = Map.of(
                "client.azureopenai.key", clientAzureOpenAiKey,
                "client.azureopenai.endpoint", clientAzureOpenAiEndPoint,
                "client.azureopenai.deploymentname", clientAzureOpenAiDeploymentName);
        OpenAIClientProvider provider = new OpenAIClientProvider(configuredSettings, ClientType.AZURE_OPEN_AI);
        return provider.getAsyncClient();
    }

}

