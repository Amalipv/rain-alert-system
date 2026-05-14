package com.apps.forecastservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RainAlertDTO {
    private String city;
    private boolean willItRain;
    private int chanceOfRain;  // percentage
    private String message;
}
