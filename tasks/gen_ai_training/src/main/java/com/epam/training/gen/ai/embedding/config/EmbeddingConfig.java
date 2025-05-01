package com.epam.training.gen.ai.embedding.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ResolvableType;

import com.microsoft.semantickernel.data.jdbc.JDBCVectorStore;
import com.microsoft.semantickernel.data.jdbc.JDBCVectorStoreOptions;
import com.microsoft.semantickernel.data.jdbc.JDBCVectorStoreQueryProvider;
import com.microsoft.semantickernel.data.jdbc.JDBCVectorStoreRecordCollectionOptions;
import com.microsoft.semantickernel.data.jdbc.postgres.PostgreSQLVectorStoreQueryProvider;
import com.microsoft.semantickernel.data.vectorstorage.VectorStore;
import com.microsoft.semantickernel.data.vectorstorage.VectorStoreRecordCollection;

@Configuration
public class EmbeddingConfig {
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

    Map<Class<?>, VectorStoreRecordCollection<?, ?>> vectorCollections = new ConcurrentHashMap<>();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean @Scope("prototype") <K, R> VectorStoreRecordCollection<K, R> vectorCollection(
        InjectionPoint injectionPoint, VectorStore vectorStore, JDBCVectorStoreQueryProvider embeddingQueryProvider
    ) {
        var target = getInjectionType(injectionPoint).getGeneric(1);
        var collectionName = target.getRawClass().getSimpleName();

        return (VectorStoreRecordCollection) vectorCollections.computeIfAbsent(
            target.getRawClass(),
            $ -> {
                VectorStoreRecordCollection<K, R> v = vectorStore.getCollection(
                    collectionName,
                    JDBCVectorStoreRecordCollectionOptions.builder()
                        .withRecordClass((Class) target.getRawClass())
                        .withQueryProvider(embeddingQueryProvider)
                        .build()
                );
                return v.createCollectionIfNotExistsAsync().block();
            }
        );
    }
    private ResolvableType getInjectionType(InjectionPoint injectionPoint) {
        return injectionPoint.getField() != null
            ? ResolvableType.forField(injectionPoint.getField())
            : ResolvableType.forMethodParameter(injectionPoint.getMethodParameter());
    }
}
