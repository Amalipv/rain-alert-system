package com.apps.notificationservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
