package com.apps.forecastservice;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Slf4j
public class WeatherMapAPIClient {
    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.city}")
    private String city;

    @Value("${weather.api.hour}")
    private int hour;


    public JsonObject getWeatherData() throws URISyntaxException {

        HttpResponse response =null;
        try {
            String url = "https://api.weatherapi.com/v1/forecast.json?q="
                    + city + "&days=1&hour=" + hour + "&key=" + apiKey;

            HttpRequest request = HttpRequest.newBuilder().
                    uri(new URI(url))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        String apiResponse = response.body().toString();
        return JsonParser.parseString(apiResponse).getAsJsonObject();
    }
}
