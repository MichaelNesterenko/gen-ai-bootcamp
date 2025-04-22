package com.epam.training.gen.ai.support.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.epam.training.gen.ai.support.service.SemanticModelService;
import com.epam.training.gen.ai.support.service.WeatherService;
import com.epam.training.gen.ai.support.service.SemanticModelService.SemanticModel;
import com.epam.training.gen.ai.support.service.WeatherService.CurrentWeatherMessage;

import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/support")
class SupportController {

    @Resource SemanticModelService semanticModelService;
    @Resource WeatherService weatherService;

    @GetMapping("/availableModels")
    List<SemanticModel> listAvailableModels() {
        return semanticModelService.retrieveSemanticModels();
    }

    @GetMapping("/weather/current")
    CurrentWeatherMessage getCurrentWeather(@RequestParam("location") String location) {
        return weatherService.getCurrentWeather(location);
    }
}