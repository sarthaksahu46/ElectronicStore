# Electronic Store Backend

A Spring Boot backend application for an e-commerce platform focused on electronic products.  
The project follows a layered architecture and provides REST APIs for user, product, cart, and order workflows.

## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot
- **Build Tool:** Maven
- **Security:** Spring Security, JWT
- **Database:** MySQL
- **Persistence:** Spring Data JPA, Hibernate
- **Utilities:** Lombok, ModelMapper

## Features

- User registration, authentication, and role-based access
- JWT-based secure API access
- Product and category management
- Cart management for customer sessions
- Order creation and order item tracking
- Validation and exception-aware API responses
- File upload support for profile/product images

## Project Structure

```text
ElectronicStore/
├── src/
│   ├── main/
│   │   ├── java/com/sart/electronix/store/
│   │   │   ├── config/         # Security and app-level configuration
│   │   │   ├── controller/     # REST controllers (API endpoints)
│   │   │   ├── dtos/           # Request/response DTOs
│   │   │   ├── entities/       # JPA entities and enums
│   │   │   ├── exceptions/     # Custom exception handling
│   │   │   ├── repositories/   # Spring Data JPA repositories
│   │   │   ├── security/       # JWT filters, helpers, auth entry points
│   │   │   ├── services/       # Service interfaces
│   │   │   └── services/impl/  # Service implementations
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Setup and Installation

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8+

### 1) Clone the repository

```bash
git clone https://github.com/sarthaksahu46/ElectronicStore.git
cd ElectronicStore
```

### 2) Configure database

Update `src/main/resources/application.properties` with your local MySQL credentials and database name.

### 3) Build the project

```bash
mvn clean install
```

### 4) Run the application

```bash
mvn spring-boot:run
```

The server runs on:

```text
http://localhost:9090
```

## API Overview

The project exposes REST APIs grouped by modules. Example route groups:

- `/auth/**` - authentication and token issuance
- `/users/**` - user profile and account operations
- `/products/**` - product CRUD and listing
- `/categories/**` - category management
- `/carts/**` - cart operations
- `/orders/**` - order placement and tracking

Use tools like Postman or cURL to test endpoints.

## Database Configuration

Database settings are managed in `src/main/resources/application.properties`.

Typical properties include:

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `spring.jpa.hibernate.ddl-auto`
- `spring.jpa.properties.hibernate.dialect`

For production, prefer environment variables or externalized configuration over hardcoded secrets.

## Future Improvements

- Add API versioning (`/api/v1`)
- Improve test coverage (unit/integration)
- Add Docker and docker-compose setup
- Introduce centralized API documentation (Swagger/OpenAPI)
- Add caching (Redis) for high-traffic queries
- Add observability (metrics, tracing, structured logging)

## Author

**Electronic Store Backend Team**

---

If you are contributing, feel free to open issues and submit pull requests.
