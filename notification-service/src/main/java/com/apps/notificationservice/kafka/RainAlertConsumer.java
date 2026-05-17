package com.apps.notificationservice.kafka;

import com.apps.notificationservice.dto.RainAlertDTO;
import com.apps.notificationservice.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RainAlertConsumer {

    private final NotificationService notificationService;

    public RainAlertConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "rain-alerts",
            groupId = "notification-group"
    )
    public void consumeAlert(RainAlertDTO alert) {
        log.info("Received alert from Kafka for: {}", alert.getCity());
        notificationService.sendRainAlert(alert);
    }
}