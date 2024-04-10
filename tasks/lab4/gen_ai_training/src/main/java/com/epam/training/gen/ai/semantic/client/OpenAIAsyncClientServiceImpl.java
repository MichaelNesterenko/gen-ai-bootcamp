package com.epam.training.gen.ai.semantic.client;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.microsoft.semantickernel.connectors.ai.openai.util.ClientType;
import com.microsoft.semantickernel.connectors.ai.openai.util.OpenAIClientProvider;
import com.microsoft.semantickernel.exceptions.ConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class OpenAIAsyncClientServiceImpl implements OpenAIAsyncClientService {

    private final Map<String, String> configuredSettings;

    public OpenAIAsyncClientServiceImpl(
            @Value("${client-azureopenai-key}") String clientAzureOpenAiKey,
            @Value("${client-azureopenai-endpoint}") String clientAzureOpenAiEndPoint,
            @Value("${client-azureopenai-deployment-name}") String clientAzureOpenAiDeploymentName) {
        this.configuredSettings = Map.of(
                "client.azureopenai.key", clientAzureOpenAiKey,
                "client.azureopenai.endpoint", clientAzureOpenAiEndPoint,
                "client.azureopenai.deploymentname", clientAzureOpenAiDeploymentName
        );    }

    @Override
    public OpenAIAsyncClient get() {
        OpenAIClientProvider provider = new OpenAIClientProvider(configuredSettings, ClientType.AZURE_OPEN_AI);
        try {
            return provider.getAsyncClient();
        } catch (ConfigurationException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
