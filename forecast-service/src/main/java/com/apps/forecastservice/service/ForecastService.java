package com.apps.forecastservice.service;

import com.apps.forecastservice.WeatherMapAPIClient;
import com.apps.forecastservice.client.NotificationClient;
import com.apps.forecastservice.dto.RainAlertDTO;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
@Slf4j
public class ForecastService {

    private final WeatherMapAPIClient forecastAPIClient;
    private final NotificationClient notificationClient;

    @Value("${weather.api.rain-threshold}")
    private int rainThreshold;

    public ForecastService(WeatherMapAPIClient forecastAPIClient,
                           NotificationClient notificationClient) {
        this.forecastAPIClient = forecastAPIClient;
        this.notificationClient = notificationClient;
    }

    public void checkRainAndNotify() {
        try {
            log.info("Fetching weather data...");

            // Call your existing API client
            JsonObject weatherData = forecastAPIClient.getWeatherData();

            // Extract rain info
            JsonObject dayDetails = weatherData
                    .get("forecast").getAsJsonObject()
                    .get("forecastday").getAsJsonArray()
                    .get(0).getAsJsonObject()
                    .get("day").getAsJsonObject();

            boolean willItRain = dayDetails
                    .get("daily_will_it_rain").getAsBoolean();
            int chanceOfRain = dayDetails
                    .get("daily_chance_of_rain").getAsInt();
            String city = weatherData
                    .get("location").getAsJsonObject()
                    .get("name").getAsString();

            log.info("City: {}, Will it rain: {}, Chance: {}%",
                    city, willItRain, chanceOfRain);

            boolean shouldAlert = chanceOfRain >= rainThreshold;

            // Build alert message
            String message = shouldAlert
                    ? "Rain expected today with " + chanceOfRain + "% chance. Please postpone washing clothes!"
                    : "No rain expected today. Safe to wash clothes!";

            // Build DTO
            RainAlertDTO alert = RainAlertDTO.builder()
                    .city(city)
                    .willItRain(shouldAlert)
                    .chanceOfRain(chanceOfRain)
                    .message(message)
                    .build();

            // Send to notification service
            notificationClient.sendRainAlert(alert);

        } catch (Exception e) {
            log.error("Error checking weather: {}", e.getMessage());
        }
    }
}