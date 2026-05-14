package com.apps.forecastservice.controller;

import com.apps.forecastservice.service.ForecastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/forecast")
public class ForecastController {

    private final ForecastService forecastService;

    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @PostMapping("/check")
    public ResponseEntity<String> checkRain() {
        forecastService.checkRainAndNotify();
        return ResponseEntity.ok("Weather check triggered!");
    }
}
