package com.epam.training.gen.ai.examples.semantic.history;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.semanticfunctions.KernelFunction;
import com.microsoft.semantickernel.semanticfunctions.KernelFunctionArguments;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SimpleKernelHistory {

    private final Kernel kernel;

    public String processWithHistory(String prompt) {

        var chatHistory = new ChatHistory();

        var response = kernel.invokeAsync(getChat())
                .withArguments(getKernelFunctionArguments(prompt, chatHistory))
                .block();

        chatHistory.addUserMessage(prompt);
        chatHistory.addAssistantMessage(response.getResult());
        log.info("AI answer:" + response.getResult());
        return response.getResult();
    }

    private KernelFunction<String> getChat() {
        return KernelFunction.<String>createFromPrompt("""
                        {{$chatHistory}}
                        <message role="user">{{$request}}</message>""")
                .build();
    }

    private KernelFunctionArguments getKernelFunctionArguments(String prompt, ChatHistory chatHistory) {
        return KernelFunctionArguments.builder()
                .withVariable("request", prompt)
                .withVariable("chatHistory", chatHistory)
                .build();
    }

}
