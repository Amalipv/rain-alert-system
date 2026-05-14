package com.apps.notificationservice.service;

import com.apps.notificationservice.config.TwilioConfig;
import com.apps.notificationservice.dto.RainAlertDTO;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


@Service
@Slf4j
public class NotificationService {

    private final TwilioConfig twilioConfig;

    @Value("${mom.phone-number}")
    private String momPhoneNumber;

    public NotificationService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    public void sendRainAlert(RainAlertDTO alert) {
        // Always print to console
        printAlert(alert);

        // Send SMS to mom!
        sendSMS(alert);
    }

    public void printAlert(RainAlertDTO alert) {
        if (alert.isWillItRain()) {
            // Rain expected — tell mom to postpone washing!
            System.out.println("=================================");
            System.out.println("🌧️  RAIN ALERT for: " + alert.getCity());
            System.out.println("Chance of rain: " + alert.getChanceOfRain() + "%");
            System.out.println("Message to Mom: " + alert.getMessage());
            System.out.println("Mom, please postpone washing clothes today!");
            System.out.println("=================================");
        } else {
            // No rain — safe to wash!
            System.out.println("=================================");
            System.out.println("☀️  NO RAIN for: " + alert.getCity());
            System.out.println("Message to Mom: " + alert.getMessage());
            System.out.println("Mom, safe to wash clothes today!");
            System.out.println("=================================");
        }
        log.info("Alert sent for city: {}, willRain: {}",
                alert.getCity(), alert.isWillItRain());
    }

    private void sendSMS(RainAlertDTO alert) {
        try {
            String smsMessage = alert.isWillItRain()
                    ? "🌧️ Rain Alert! " + alert.getChanceOfRain() +
                    "% chance of rain in " + alert.getCity() +
                    ". Please postpone washing clothes today!"
                    : "☀️ No rain today in " + alert.getCity() +
                    ". Safe to wash clothes!";

            Message message = Message.creator(
                    new PhoneNumber(momPhoneNumber),           // to — mom's number
                    new PhoneNumber(twilioConfig.getPhoneNumber()), // from — Twilio number
                    smsMessage
            ).create();

            log.info("SMS sent! SID: {}", message.getSid());
            System.out.println("✅ SMS sent to Mom! SID: " + message.getSid());

        } catch (Exception e) {
            log.error("Failed to send SMS: {}", e.getMessage());
            System.out.println("❌ SMS failed: " + e.getMessage());
        }
    }
}

