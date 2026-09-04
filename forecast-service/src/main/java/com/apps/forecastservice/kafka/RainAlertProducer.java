package com.apps.forecastservice.kafka;

import com.apps.forecastservice.dto.RainAlertDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("kafka")
public class RainAlertProducer {

    private final KafkaTemplate<String, RainAlertDTO> kafkaTemplate;
    private static final String TOPIC = "rain-alerts";

    public RainAlertProducer(KafkaTemplate<String, RainAlertDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishAlert(RainAlertDTO alert) {
        kafkaTemplate.send(TOPIC, alert.getCity(), alert)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Alert published to Kafka for: {}", alert.getCity());
                    } else {
                        log.error("Failed to publish alert: {}", ex.getMessage());
                    }
                });
    }
}