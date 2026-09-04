# Rain Alert System 🌧️

A microservices-based application that fetches weather data
and sends SMS alerts to family members when rain is expected.

## Architecture

```
                    ┌─────────────────┐
                    │   API Gateway   │
                    │  (Port: 8080)   │
                    │ JWT Validation  │
                    └────────┬────────┘
                             │
              ┌──────────────┼──────────────┐
              ↓              ↓              ↓
    ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
    │   Forecast   │  │    Auth      │  │Notification  │
    │   Service    │  │   Service    │  │   Service    │
    │  Port: 8081  │  │  Port: 8084  │  │  Port: 8082  │
    └──────┬───────┘  └──────────────┘  └──────┬───────┘
           │                                    │
           │         ┌──────────────┐           │
           │         │    Eureka    │           │
           │         │  Port: 8761  │           │
           │         └──────────────┘           │
           │                                    ↓
           │    Kafka Topic: rain-alerts    ┌──────────┐
           └───────────────────────────────►│  Twilio  │
                                            │   SMS    │
                                            └──────────┘
```

## Services

| Service              | Port | Description                        |
|----------------------|------|------------------------------------|
| ra-eureka-server     | 8761 | Service Registry                   |
| forecast-service     | 8081 | Fetches weather from WeatherAPI    |
| notification-service | 8082 | Sends SMS via Twilio               |
| api-gateway          | 8080 | Single entry point + JWT validation|
| auth-service         | 8084 | User registration + JWT tokens     |

## Tech Stack

- Java 17 + Spring Boot 3.2.0
- Spring Cloud 2023.0.0 (Eureka, API Gateway)
- Apache Kafka — event driven communication
- Twilio — SMS notifications
- WeatherAPI.com — weather data
- Docker + Docker Compose
- JWT Security
- Spring Profiles (kafka/lite)
- PostgreSQL
- Maven Multi-module

## Spring Profiles

| Profile | Usage             | Communication       |
|---------|-------------------|---------------------|
| kafka   | Local development | Async via Kafka     |
| lite    | AWS deployment    | Direct REST call    |

## How to Run

### Prerequisites
- Java 17
- Maven
- Docker Desktop
- Twilio account
- WeatherAPI key

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/Amalipv/rain-alert-system.git

# 2. Create .env file with your secrets (see Environment Variables below)

# 3. Build all services
mvn clean package -DskipTests

# 4. Start everything
docker-compose up --build
```

### Test the application

```bash
# Register a user
POST http://localhost:8080/auth/register
Content-Type: application/json
{
  "username": "testuser",
  "password": "password123"
}

# Login and get JWT token
POST http://localhost:8080/auth/login
Content-Type: application/json
{
  "username": "testuser",
  "password": "password123"
}

# Trigger weather check (use token from login response)
POST