# JobOffers

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-7.0-47A248)](https://www.mongodb.com/)
[![Redis](https://img.shields.io/badge/Redis-cache-red)](https://redis.io/)
[![Gradle](https://img.shields.io/badge/Gradle-wrapper-02303A)](https://gradle.org/)

**JobOffers** is a Spring Boot REST API for collecting, storing and serving job offers. It retrieves offers from an external HTTP service, persists them in MongoDB, prevents duplicate offers based on their URL and exposes endpoints for browsing and adding offers. The API also provides user registration and JWT-based authentication.

## Table of contents

- [Features](#features)
- [Technology stack](#technology-stack)
- [Application architecture](#application-architecture)
- [REST API](#rest-api)
- [Running the application](#running-the-application)
- [Configuration](#configuration)
- [Caching and scheduled fetching](#caching-and-scheduled-fetching)
- [Testing](#testing)
- [API documentation](#api-documentation)

## Features

- Fetch job offers from an external HTTP API.
- Map external offer data to the application's domain model.
- Store offers in MongoDB.
- Avoid saving offers whose URL is already present in the database.
- Retrieve all offers or a single offer by its ID.
- Add an offer through the REST API.
- Register users with BCrypt-encoded passwords.
- Authenticate users and issue signed JWT tokens.
- Protect application endpoints with stateless JWT authentication.
- Cache the list of offers in Redis (configured with a 60-minute TTL).
- Run scheduled offer fetching every three hours.
- Return HTTP error responses for missing offers and duplicate offer URLs.
- Validate offer request fields.

## Technology stack

| Area | Technologies |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.1.1 |
| Build | Gradle |
| REST API | Spring Web MVC |
| Persistence | Spring Data MongoDB, MongoDB 7 |
| Cache | Spring Cache, Spring Data Redis, Jedis, Redis |
| Security | Spring Security, Auth0 Java JWT, BCrypt |
| HTTP client | RestTemplate |
| API documentation | Springdoc OpenAPI / Swagger UI |
| Testing | JUnit, Mockito, AssertJ, Awaitility |
| Integration testing | Testcontainers, WireMock |
| Local infrastructure | Docker Compose |

## Application architecture

The code is organized into two main areas:

- `domain` – application/domain logic, models, DTOs, repositories and facades.
- `infrastructure` – HTTP controllers, security configuration, JWT filter, external HTTP client, scheduler, cache configuration and error handling.

Main offer flow:

1. `HttpOffersScheduler` triggers the scheduled job.
2. `OfferFacade` delegates the operation to `OfferService`.
3. `OfferService` retrieves external data through the `OfferFetchable` abstraction.
4. `OfferMapper` maps external responses to the `Offer` domain model.
5. Existing offer URLs are filtered out.
6. New offers are saved through `OfferRepository` to MongoDB.

The `OfferFetchable` interface separates offer-fetching logic from the service, which also makes the external integration replaceable in tests.

```text
External Job Offers API
          |
          v
     OfferHttpClient
          |
          v
    OfferFetchable
          |
          v
     OfferService
          |
          v
      OfferFacade
          |
          v
   OfferRepository
          |
          v
       MongoDB
```

## REST API

All offer endpoints require a valid JWT bearer token. The registration and token endpoints are public.

### Authentication

| Method | Endpoint | Description | Authentication |
|---|---|---|---|
| `POST` | `/register` | Register a new user | Not required |
| `POST` | `/token` | Authenticate and obtain a JWT | Not required |

Register a user:

```http
POST /register
Content-Type: application/json

{
  "name": "your-username",
  "password": "your-password"
}
```

Authenticate:

```http
POST /token
Content-Type: application/json

{
  "username": "your-username",
  "password": "your-password"
}
```

The token response contains the JWT and username. Send the token in the `Authorization` header when calling protected endpoints:

```http
Authorization: Bearer <your-jwt-token>
```

### Offers

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/offers` | Get all stored offers |
| `GET` | `/offers/{id}` | Get an offer by ID |
| `POST` | `/offers` | Save a new offer |

Get all offers:

```http
GET /offers
Authorization: Bearer <your-jwt-token>
```

Get one offer:

```http
GET /offers/{id}
Authorization: Bearer <your-jwt-token>
```

Create an offer:

```http
POST /offers
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "companyName": "Example Company",
  "position": "Java Developer",
  "salary": "10000-15000 PLN",
  "offerUrl": "https://example.com/jobs/java-developer"
}
```

The offer request requires non-empty values for `companyName`, `position`, `salary` and `offerUrl`.

### Error handling

The application includes handlers for selected API errors:

| Scenario | HTTP status | Message / behavior |
|---|---:|---|
| Offer ID does not exist | `404 Not Found` | `Offer with id {id} not found` |
| Offer URL already exists | `409 Conflict` | `Offer url already exists` |
| Invalid or missing authentication | `401 Unauthorized` | Request is not authorized |
| Invalid offer fields | `400 Bad Request` | Validation error response |

## Running the application

### Prerequisites

- Java 21
- Docker Desktop or Docker Engine with Docker Compose
- Git (to clone the repository)

### 1. Clone the repository

```bash
git clone https://github.com/barabash94/JobOffers.git
cd JobOffers
```

### 2. Start infrastructure services

The project includes a `docker-compose.yml` file with MongoDB, Mongo Express, Redis and Redis Commander.

```bash
docker compose up -d
```

Services and local ports:

| Service | Port | Purpose |
|---|---:|---|
| MongoDB | `27017` | Database |
| Mongo Express | `8081` | MongoDB web interface |
| Redis | `63792` (host) | Cache |
| Redis Commander | `8082` | Redis web interface |

The MongoDB initialization script creates an `admin` user for the `offers` database. The application properties use this local development account.

### 3. Run the application

On macOS/Linux:

```bash
./gradlew bootRun
```

On Windows:

```powershell
.\gradlew.bat bootRun
```

The application uses the default Spring Boot HTTP port (`8080`).

### 4. Stop infrastructure services

```bash
docker compose down
```

To remove the containers and their persisted data volumes, use `docker compose down -v`. This deletes local database data.

## Configuration

The main configuration is in `src/main/resources/application.properties`.

| Property | Purpose | Default in project |
|---|---|---|
| `spring.mongodb.uri` | MongoDB connection URI | `mongodb://admin:admin@localhost:27017/offers` |
| `offer.http.client.config.uri` | External offers API host | `https://fake-java-offers-api.onrender.com` |
| `offer.http.client.config.port` | External offers API port | `443` |
| `offer.http.client.config.connectionTimeout` | HTTP connection timeout (ms) | `1000` |
| `offer.http.client.config.readTimeout` | HTTP read timeout (ms) | `1000` |
| `http.offers.scheduler.request.delay` | Delay between scheduled executions | `PT3H` |
| `scheduling.enabled` | Enables scheduler configuration | `true` |
| `auth.jwt.secret` | JWT signing secret | Development placeholder |
| `auth.jwt.expiration-days` | JWT lifetime in days | `7` |
| `auth.jwt.issuer` | JWT issuer | `job-offers` |
| `spring.data.redis.host` | Redis hostname | `localhost` |
| `spring.data.redis.port` | Redis host port | `63792` |
| `spring.cache.redis.time-to-live` | Cache entry TTL | `PT60M` |

**Before deploying outside a local development environment**, replace the JWT development secret and local database credentials. Prefer environment variables or an external secrets manager rather than committing credentials to source control.

The external HTTP client builds its request URL from the configured URI, port and `/offers` path. Make sure the configured host and port match the external service.

## Caching and scheduled fetching

- The `findAllOffers()` operation is annotated with `@Cacheable` and uses the `jobOffers` cache.
- Redis is configured as the cache provider.
- The configured cache time-to-live is 60 minutes.
- `HttpOffersScheduler` invokes the offer-fetching flow with a fixed delay configured by `http.offers.scheduler.request.delay`.
- The default delay is `PT3H` (three hours).
- The scheduler configuration is conditional on `scheduling.enabled`.

## Testing

The project separates unit tests from integration tests.

Run unit tests:

```bash
./gradlew test
```

Run integration tests:

```bash
./gradlew integrationTest
```

Run the full verification lifecycle (unit tests and integration tests):

```bash
./gradlew check
```

On Windows, use `.\gradlew.bat` instead of `./gradlew`.

The integration test source set is located in `src/integration/java`. The project uses:

- **JUnit** for test execution.
- **Mockito** for test doubles.
- **AssertJ** for fluent assertions.
- **WireMock** to stub the external HTTP API.
- **Testcontainers** to run MongoDB in integration tests.
- **Awaitility** for waiting on asynchronous behavior.

## API documentation

When the application is running, Springdoc exposes the OpenAPI specification and Swagger UI at:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

