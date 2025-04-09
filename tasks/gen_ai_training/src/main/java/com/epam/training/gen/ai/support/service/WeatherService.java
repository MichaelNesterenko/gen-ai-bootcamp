package com.epam.training.gen.ai.support.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import lombok.Data;

import java.util.function.Supplier;

@Service
public class WeatherService {
    final RestClient restClient;

    public WeatherService(@Value("${weather_api_key}") String apiKey, @Value("${weather_api_url}") String weatherUrl) {
        this.restClient = RestClient.builder()
            .baseUrl(weatherUrl)
            .defaultUriVariables(Map.of("key", apiKey))
            .build();
    }

    public CurrentWeatherMessage getCurrentWeather(String location) {
        return withWeatherException(() ->
            restClient.get()
                .uri(b ->
                    authenticatedUri(b)
                        .path("current.json")
                        .queryParam("q", location)
                        .queryParam("aqi", "yes")
                        .build()
                )
                .retrieve().body(CurrentWeatherMessage.class)
        );
    }
    public ForecastWeatherMessage getForecastWeather(String location, int days) {
        return withWeatherException(() ->
            restClient.get()
                .uri(b ->
                    authenticatedUri(b)
                        .path("forecast.json")
                        .queryParam("q", location)
                        .queryParam("days", days)
                        .queryParam("aqi", "yes")
                        .build()
                )
                .retrieve().body(ForecastWeatherMessage.class)
        );
    }

    <T> T withWeatherException(Supplier<T> action) {
        try {
            return action.get();
        } catch (HttpClientErrorException.BadRequest e) {
            throw new WeatherException(e.getResponseBodyAs(WeatherErrorMessage.class).getError().getMessage(), e);
        }
    }

    UriBuilder authenticatedUri(UriBuilder b) {
        return b.queryParam("key", "{key}");
    }

    @Data
    public static class CurrentWeatherMessage {
        Location location;
        CurrentWeather current;
    }

    @Data
    public static class ForecastWeatherMessage {
        Location location;
        Forecast forecast;
        
        @Data
        public static class Forecast {
            List<DayWeather> forecastday;

            @Data
            public static class DayWeather {
                String date;
                int date_epoch;
                AvgWeather day;

                @Data
                public static class AvgWeather {
                    BigDecimal maxtemp_c;
                    BigDecimal maxtemp_f;
                    BigDecimal mintemp_c;
                    BigDecimal mintemp_f;
                    BigDecimal avgtemp_c;
                    BigDecimal avgtemp_f;
                    BigDecimal maxwind_mph;
                    BigDecimal maxwind_kph;
                    int totalprecip_mm;
                    int totalprecip_in;
                    int avgvis_km;
                    int avgvis_miles;
                    int avghumidity;
                    int daily_will_it_rain;
                    int daily_chance_of_rain;
                    int daily_will_it_snow;
                    int daily_chance_of_snow;
                    CurrentWeather.Condition condition;
                }
            }
        }
    }

    @Data
    public static class CurrentWeather {
        String last_updated;
        int last_updated_epoch;
        BigDecimal temp_c;
        BigDecimal temp_f;
        BigDecimal feelslike_c;
        BigDecimal feelslike_f;
        BigDecimal windchill_c;
        BigDecimal windchill_f;
        BigDecimal heatindex_c;
        BigDecimal heatindex_f;
        BigDecimal dewpoint_c;
        BigDecimal dewpoint_f;
        Condition condition;
        BigDecimal wind_mph;
        BigDecimal wind_kph;
        int wind_degree;
        String wind_dir;
        BigDecimal pressure_mb;
        BigDecimal pressure_in;
        BigDecimal precip_mm;
        BigDecimal precip_in;
        int humidity;
        int cloud;
        int is_day;
        BigDecimal uv;
        BigDecimal gust_mph;
        BigDecimal gust_kph;

        AirQuality air_quality;

        @Data
        public static class Condition {
            String text;
            String icon;
            int code;
        }

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

    @Data
    public static class Location {
        String name;
        String region;
        String country;
        BigDecimal lat;
        BigDecimal lon;
        String tz_id;
        long localtime_epoch;
        String localtime;
    }

    @Data
    static class WeatherErrorMessage {
        ErrorMessage error;

        @Data
        public static class ErrorMessage {
            String code;
            String message;
        }
    }

    public static class WeatherException extends RuntimeException {
        public WeatherException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
