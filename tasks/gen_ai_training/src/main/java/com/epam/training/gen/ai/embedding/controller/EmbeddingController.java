package com.epam.training.gen.ai.embedding.controller;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.models.EmbeddingItem;
import com.azure.ai.openai.models.EmbeddingsOptions;
import com.epam.training.gen.ai.embedding.model.TextEmbedding;
import com.epam.training.gen.ai.util.Util;
import com.microsoft.semantickernel.data.vectorsearch.VectorSearchResult;
import com.microsoft.semantickernel.data.vectorsearch.VectorSearchResults;
import com.microsoft.semantickernel.data.vectorstorage.VectorStoreRecordCollection;
import jakarta.annotation.Resource;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/embedding")
public class EmbeddingController {
    final MessageDigest keyDigest;

    @Resource OpenAIAsyncClient openaiClient;
    @Resource VectorStoreRecordCollection<String, TextEmbedding> textEmbedding;

    public EmbeddingController() throws NoSuchAlgorithmException {
        this.keyDigest = MessageDigest.getInstance("SHA-512");
    }

    @PostMapping("/generate") Mono<EmbeddingItem> getEmbedding(@RequestBody String text) {
        return generateEmbeddings(text);
    }

    @PostMapping("/store") Mono<Map<String, Object>> storeEmbedding(@RequestBody String text) {
        return generateEmbeddings(text).flatMap(embedding ->
            textEmbedding.upsertAsync(
                Util.tap(new TextEmbedding(), te -> {
                    te.setKey(toSha512(text));
                    te.setPayload(text);
                    te.setEmbedding(embedding.getEmbedding());
                }),
                null
            ).map(key -> Map.of("key", key))
        );
    }

    @PostMapping("/search") Mono<List<VectorSearchResult<TextEmbedding>>> searchEmbedding(@RequestBody String text) {
        return generateEmbeddings(text)
            .flatMap(embedding -> textEmbedding.searchAsync(embedding.getEmbedding(), null))
            .map(VectorSearchResults::getResults);
    }

    private Mono<EmbeddingItem> generateEmbeddings(String text) {
        return openaiClient.getEmbeddings("text-embedding-ada-002", new EmbeddingsOptions(List.of(text)))
            .map(e -> e.getData().get(0));
    }

    String toSha512(String input) {
        return "%0128x".formatted(new BigInteger(1, keyDigest.digest(input.getBytes(StandardCharsets.UTF_8))));
    }
}
