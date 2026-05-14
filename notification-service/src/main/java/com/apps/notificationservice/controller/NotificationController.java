package com.apps.notificationservice.controller;

import com.apps.notificationservice.dto.RainAlertDTO;
import com.apps.notificationservice.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/rain-alert")
    public ResponseEntity<String> receiveRainAlert(
            @RequestBody RainAlertDTO alert) {
        notificationService.sendRainAlert(alert);
        return ResponseEntity.ok("Alert processed!");
    }
}
