# Rain Alert System 🌧️

A microservices-based application that fetches weather data
and sends SMS alerts to family members when rain is expected.

## Architecture
ForecastService → WeatherAPI
↓
Eureka Service Discovery
↓
NotificationService → Twilio SMS

## Services

| Service | Port | Description |
|---|---|---|
| ra-eureka-server | 8761 | Service Registry |
| forecast-service | 8081 | Fetches weather data |
| notification-service | 8082 | Sends SMS alerts |

## Tech Stack

- Java 17
- Spring Boot 3.2.0
- Spring Cloud 2023.0.0 (Eureka)
- WeatherAPI.com
- Twilio SMS
- Maven Multi-module

## How to Run

1. Start Eureka Server (port 8761)
2. Start Notification Service (port 8082)
3. Start Forecast Service (port 8081)
4. Trigger check:
   POST http://localhost:8081/api/v1/forecast/check

## Environment Variables
TWILIO_ACCOUNT_SID=your_sid
TWILIO_AUTH_TOKEN=your_token
TWILIO_PHONE_NUMBER=your_twilio_number
MOM_PHONE_NUMBER=recipients_number
WEATHER_API_KEY=your_api_key
WEATHER_CITY=lat,long