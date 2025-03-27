package com.epam.training.gen.ai.support.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.Data;

@Service
public class SemanticModelService {

    final RestClient restClient;

    public SemanticModelService(@Value("${client-openai-endpoint-model-list}") String modelListUrl, @Value("${client-openai-key}") String accessToken) {
        restClient =
            RestClient.builder()
                .baseUrl(modelListUrl)
                .defaultHeader("Api-Key", accessToken)
                .build();
    }

    public List<SemanticModel> retrieveSemanticModels() {
        return restClient.get().retrieve()
            .body(SemanticModelListResponse.class).getData();
    }

    @Data
    public static class SemanticModelListResponse {
        List<SemanticModel> data;
    }

    @Data
    public static class SemanticModel {
        String id;
        String model;
        String display_name;
        String icon_url;
        String description;
        String reference;
        String owner;
        String object;
        String status;
        long created_at;
        long updated_at;
        Map<String, Boolean> features;
        Map<String, Object> defaults;
        List<String> description_keywords;
        int max_retry_attempts;
        String lifecycle_status;
    }
}
