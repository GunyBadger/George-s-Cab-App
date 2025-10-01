# Cab Booking System

Simple Spring Boot app (Web + JPA + MySQL) with two microservices:
- **Fare**: `GET /fare?distance={km}` → returns fare (rate 15.0 per km)
- **Booking**:
  - `POST /book` → create a booking (calculates fare via `/fare`)
  - `GET /book` → list all bookings
  - `GET /book/{id}` → get one booking
- **Health**: `GET /health` → "OK"

## Tech
Java 21 • Spring Boot 3 • Spring Web • Spring Data JPA • MySQL • Thymeleaf (welcome page)

## Run locally

**Prereqs**: MySQL running and a schema named `cabdb`.

```bash
mvn clean package
mvn spring-boot:run
# or
java -jar target/Cab-booking-system-0.0.1-SNAPSHOT.jar
