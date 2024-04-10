package com.epam.training.gen.ai.semantic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationHandler implements ApplicationListener<ApplicationReadyEvent> {

    private final SimplePromptService promptService;

    @Autowired
    public StartupApplicationHandler(SimplePromptService promptService) {
        this.promptService = promptService;
    }

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        promptService.getChatCompletions();
    }
}


