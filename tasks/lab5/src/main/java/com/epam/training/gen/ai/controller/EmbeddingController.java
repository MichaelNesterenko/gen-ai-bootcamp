package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmbeddingController {

    private final EmbeddingClient embeddingClient;
    private final EmbeddingService embeddingService;

    @GetMapping("/ai/embedding")
    public Map embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of();
    }

    @PostMapping("/ai/embedding")
    public void addDocument(@RequestBody String document) {
    }

    @GetMapping("/ai/embedding/search")
    public List search(@RequestParam(value = "message", defaultValue = "prompt engineering") String message) {
        return List.of();
    }

    @PostMapping("/ai/upload-embedding")
    public void uploadFile(@RequestParam("file") MultipartFile file) {
    }

    @GetMapping("/ai/dimensions")
    public int getDimensions() {
    }

    private File getUploadedFile(MultipartFile file) {
    }
}