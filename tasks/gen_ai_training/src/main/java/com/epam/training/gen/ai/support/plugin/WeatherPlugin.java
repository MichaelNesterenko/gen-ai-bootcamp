package com.epam.training.gen.ai.support.plugin;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.epam.training.gen.ai.support.service.WeatherService;
import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;

import java.util.List;

import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static com.epam.training.gen.ai.util.Util.tap;

@Service @Slf4j
public class WeatherPlugin implements SemanticPLugin {

    @Resource WeatherService weatherService;

    @DefineKernelFunction(name = "get_weather_forecast", description = "retrieve weather forecast")
    public ForecastedWeatherResponse getWeatherForecast(
        @KernelFunctionParameter(name = "location", description = "location where wheather is retrieved") String location,
        @KernelFunctionParameter(name = "days", description = "for how many days to provide forecast") int days
    ) {
        var weather = weatherService.getForecastWeather(location, days);
        var weatherResponse = new ForecastedWeatherResponse();

        weatherResponse.setForecastDays(weather.getForecast().getForecastday().stream()
            .map(day -> tap(new ForecastedWeatherResponse.ForecastWeatherDay(), forecastDay -> {
                forecastDay.setDate(day.getDate());
                forecastDay.setMaximumTemperatureCelsius(day.getDay().getMaxtemp_c());
                forecastDay.setMinimumTemperatureCelsius(day.getDay().getMintemp_c());
                forecastDay.setAverageTemperatureCelsius(day.getDay().getAvgtemp_c());
                forecastDay.setMaximumWindKph(day.getDay().getMaxwind_kph());
            }))
            .toList()
        );

        log.info("weather forecast: {}", weatherResponse);

        return weatherResponse;
    }

    @DefineKernelFunction(name = "get_weather_current", description = "retrieve realtime weather")
    public CurrentWeatherResponse getWeatherCurrent(
        @KernelFunctionParameter(name = "location", description = "location where wheather is retrieved") String location
    ) {
        var weather = weatherService.getCurrentWeather(location);

        var weatherResponse = new CurrentWeatherResponse();
        weatherResponse.setLocation("%s %s %s".formatted(
            weather.getLocation().getCountry(),
            weather.getLocation().getRegion(),
            weather.getLocation().getName()
        ));
        weatherResponse.setCondition(weather.getCurrent().getCondition().getText());
        weatherResponse.setLocalTime(weather.getLocation().getLocaltime());

        weatherResponse.setTemperatureCelsius(weather.getCurrent().getTemp_c());
        weatherResponse.setTemperatureFeelsCelsius(weather.getCurrent().getFeelslike_c());
        weatherResponse.setWindKmph(weather.getCurrent().getWind_kph());
        weatherResponse.setHumidity(weather.getCurrent().getHumidity());

        weatherResponse.setAirQuality(tap(new CurrentWeatherResponse.AirQuality(), aq -> {
            aq.setCo(weather.getCurrent().getAir_quality().getCo());
            aq.setNo2(weather.getCurrent().getAir_quality().getNo2());
            aq.setO3(weather.getCurrent().getAir_quality().getO3());
            aq.setSo2(weather.getCurrent().getAir_quality().getSo2());
            aq.setPm2_5(weather.getCurrent().getAir_quality().getPm2_5());
            aq.setPm10(weather.getCurrent().getAir_quality().getPm10());
            aq.setUsEpaIndex(weather.getCurrent().getAir_quality().getUsEpaIndex());
            aq.setGbDefraIndex(weather.getCurrent().getAir_quality().getGbDefraIndex());
        }));

        log.info("weather current: {}", weatherResponse);

        return weatherResponse;
    }

    @Data
    public static class ForecastedWeatherResponse {
        List<ForecastWeatherDay> forecastDays;
        
        @Data
        public static class ForecastWeatherDay {
            String date;
            BigDecimal maximumTemperatureCelsius;
            BigDecimal minimumTemperatureCelsius;
            BigDecimal averageTemperatureCelsius;
            BigDecimal maximumWindKph;
        }
    }

    @Data
    public static class CurrentWeatherResponse {
        String location;
        String condition;
        String localTime;
        BigDecimal temperatureCelsius;
        BigDecimal temperatureFeelsCelsius;
        BigDecimal windKmph;
        int humidity;

        AirQuality airQuality;

        @Data
        public static class AirQuality {
            BigDecimal co;
            BigDecimal no2;
            BigDecimal o3;
            BigDecimal so2;
            BigDecimal pm2_5;
            BigDecimal pm10;
            int usEpaIndex;
            int gbDefraIndex;
        }
    }
}
