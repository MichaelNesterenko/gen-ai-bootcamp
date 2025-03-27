package com.epam.training.gen.ai.support.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.epam.training.gen.ai.support.service.SemanticModelService;
import com.epam.training.gen.ai.support.service.SemanticModelService.SemanticModel;

import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/support")
class SupportController {

    @Resource SemanticModelService semanticModelService;

    @GetMapping("/availableModels")
    List<SemanticModel> listAvailableModels() {
        return semanticModelService.retrieveSemanticModels();
    }

}