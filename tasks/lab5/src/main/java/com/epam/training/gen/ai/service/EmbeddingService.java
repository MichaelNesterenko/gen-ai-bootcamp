package com.epam.training.gen.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmbeddingService {

    private final VectorStore vectorStore;

    public void addDocument(File file) {
    }

    public void addDocument(String text) {
    }

    public List<Document> getDocuments(String searchText) {
        return List.of();
    }

    private Document convertPdfToDocument(File pdfFile) {
        return new Document();
    }
}
