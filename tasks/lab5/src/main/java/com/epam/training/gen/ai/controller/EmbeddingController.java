package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmbeddingController {

    private final EmbeddingClient embeddingClient;
    private final EmbeddingService embeddingService;

    @GetMapping("/ai/embedding")
    public Map embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        EmbeddingResponse embeddingResponse = this.embeddingClient.embedForResponse(List.of(message));
        return Map.of("embedding", embeddingResponse);
    }

    @PostMapping("/ai/embedding")
    public void addDocument(@RequestBody String document) {
        this.embeddingService.addDocument(document);
    }

    @GetMapping("/ai/embedding/search")
    public List search(@RequestParam(value = "message", defaultValue = "prompt engineering") String message) {
        return this.embeddingService.getDocuments(message);
    }

    @PostMapping("/ai/upload-embedding")
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        var uploadedFile = getUploadedFile(file);
        try {
            embeddingService.addDocument(uploadedFile);
        } finally {
            uploadedFile.delete();
        }
    }

    @GetMapping("/ai/dimensions")
    public int getDimensions() {
        return embeddingClient.dimensions();
    }

    private File getUploadedFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Path path = Paths.get("target/uploads/" + fileName);

        if (path.toFile().exists()) {
            path.toFile().delete();
        }

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File uploadedFile = path.toFile();

        return uploadedFile;
    }
}