package com.epam.training.gen.ai.embedding.plugin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.training.gen.ai.embedding.model.TextEmbedding;
import com.epam.training.gen.ai.support.plugin.SemanticPlugin;
import com.microsoft.semantickernel.data.VectorStoreTextSearch;
import com.microsoft.semantickernel.data.textsearch.KernelSearchResults;
import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service @Slf4j
public class TextSearchPlugin implements SemanticPlugin {
    @Resource VectorStoreTextSearch<TextEmbedding> textSearch;

    @DefineKernelFunction(
        name = "get_additional_information",
        description = "provide additional user defined information",
        returnType = "java.util.List"
    )
    public Mono<List<String>> search(
        @KernelFunctionParameter(name = "query", description = "original user prompt") String query
    ) {
        return textSearch.searchAsync(query, null).map(KernelSearchResults::getResults);
    }
}
