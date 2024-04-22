package com.epam.lab7.v1.chat;

import com.epam.lab7.v1.documentreader.DocumentService;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;

public record Chat(ChatClient aiClient, VectorStore vectorStore, DocumentService documentService) {
}
