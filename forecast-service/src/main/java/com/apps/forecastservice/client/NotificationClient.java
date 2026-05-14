package com.apps.forecastservice.client;


import com.apps.forecastservice.dto.RainAlertDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class NotificationClient {

    private final RestTemplate restTemplate;

    @Value("${notification.service.url}")
    private String notificationServiceUrl;

    public NotificationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendRainAlert(RainAlertDTO alert) {
        String url = notificationServiceUrl + "/api/v1/notifications/rain-alert";
        try {
            restTemplate.postForObject(url, alert, String.class);
            log.info("Alert sent to notification service successfully!");
        } catch (Exception e) {
            log.error("Failed to call notification service: {}", e.getMessage());
        }
    }
}
