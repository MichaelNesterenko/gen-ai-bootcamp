package com.epam.training.gen.ai.embedding.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ResolvableType;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.microsoft.semantickernel.aiservices.openai.textembedding.OpenAITextEmbeddingGenerationService;
import com.microsoft.semantickernel.data.VectorStoreTextSearch;
import com.microsoft.semantickernel.data.jdbc.JDBCVectorStore;
import com.microsoft.semantickernel.data.jdbc.JDBCVectorStoreOptions;
import com.microsoft.semantickernel.data.jdbc.JDBCVectorStoreQueryProvider;
import com.microsoft.semantickernel.data.jdbc.JDBCVectorStoreRecordCollectionOptions;
import com.microsoft.semantickernel.data.jdbc.postgres.PostgreSQLVectorStoreQueryProvider;
import com.microsoft.semantickernel.data.vectorstorage.VectorStore;
import com.microsoft.semantickernel.data.vectorstorage.VectorStoreRecordCollection;

import jakarta.annotation.Resource;

@Configuration
public class EmbeddingConfig {
    @Resource ApplicationContext applicationContext;

    @ConfigurationProperties("app.embedding.datasource")
    @Bean DataSource embeddingDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean JDBCVectorStoreQueryProvider embeddingQueryProvider(DataSource embeddingDataSource) {
        return PostgreSQLVectorStoreQueryProvider.builder()
            .withDataSource(embeddingDataSource)
            .build();
    }

    @Bean VectorStore embeddingVectorStore(DataSource embeddingDataSource, JDBCVectorStoreQueryProvider embeddingQueryProvider) {
        return JDBCVectorStore.builder()
            .withDataSource(embeddingDataSource)
            .withOptions(
                JDBCVectorStoreOptions.builder()
                    .withQueryProvider(embeddingQueryProvider)
                    .build()
            )
            .build();
    }

    final Map<ResolvableType, VectorStoreTextSearch<?>> textSearches = new ConcurrentHashMap<>();
    @SuppressWarnings("unchecked")
    @Bean <T> VectorStoreTextSearch<T> vectorStoreTextSearch(InjectionPoint injectionPoint, OpenAIAsyncClient openAiClient) {
        return (VectorStoreTextSearch<T>) textSearches.computeIfAbsent(
            getInjectionType(injectionPoint).getGeneric(0),
            key ->
                VectorStoreTextSearch.builder()
                    .withVectorizedSearch(getOrCreateVectorStoreCollection(key))
                    .withTextEmbeddingGenerationService(
                        OpenAITextEmbeddingGenerationService.builder()
                            .withModelId("text-embedding-ada-002")
                            .withOpenAIAsyncClient(openAiClient)
                            .build())
                    .build()
        );
    }

    final Map<ResolvableType, VectorStoreRecordCollection<?, ?>> vectorCollections = new ConcurrentHashMap<>();
    @Bean @Scope("prototype") <K, R> VectorStoreRecordCollection<K, R> vectorCollection(InjectionPoint injectionPoint) {
        return getOrCreateVectorStoreCollection(getInjectionType(injectionPoint).getGeneric(1));
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private <K, R> VectorStoreRecordCollection<K, R> getOrCreateVectorStoreCollection(ResolvableType key) {
        return (VectorStoreRecordCollection) vectorCollections.computeIfAbsent(key, $key -> {
            VectorStoreRecordCollection<K, R> v = applicationContext.getBean(VectorStore.class).getCollection(
                $key.getRawClass().getSimpleName(),
                JDBCVectorStoreRecordCollectionOptions.builder()
                    .withRecordClass((Class) $key.getRawClass())
                    .withQueryProvider(applicationContext.getBean(JDBCVectorStoreQueryProvider.class))
                    .build()
            );
            return v.createCollectionIfNotExistsAsync().block();
        });
    }

    private ResolvableType getInjectionType(InjectionPoint injectionPoint) {
        return injectionPoint.getField() != null
            ? ResolvableType.forField(injectionPoint.getField())
            : ResolvableType.forMethodParameter(injectionPoint.getMethodParameter());
    }

}
