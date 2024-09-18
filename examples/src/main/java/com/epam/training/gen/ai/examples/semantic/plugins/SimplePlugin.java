package com.epam.training.gen.ai.examples.semantic.plugins;

import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimplePlugin {

    @DefineKernelFunction(name = "makeSimpleAction", description = "Makes a simple action on data.")
    public String getBingSearchUrl(
            @KernelFunctionParameter(description = "Data on which to do action", name = "query") String query) {
        log.info("Simple plugin was called with query: [{}]", query);
        return query;
    }
}
