# Subscription Management System

This project is a backend implementation of a **Subscription Management System**, developed as part of an onboarding task. It includes APIs for authentication, user management, and subscription management, built using **Spring Boot 3.4.5**, **Java 21**, and **h2 database**.

---

## Table of Contents

1. [Technologies Used](#technologies-used)
2. [Security Implementation](#security-implementation)
3. [Database and ORM](#database-and-orm)
4. [Validation](#validation)
5. [API Documentation](#api-documentation)
6. [Project Structure](#project-structure)
7. [Exception Handling](#exception-handling)
8. [Caching](Caching)
9. [Logging](Logging)
10. [Setup Instructions](#setup-instructions)

---

## Technologies Used

- **Framework**: Spring Boot 3.4.5
- **Java Version**: JDK 21
- **Database**: h2
- **ORM**: Hibernate
- **Security**: Spring Security with JWT (JSON Web Tokens)
- **Password Hashing**: BCryptPasswordEncoder
- **Validation**: Java Bean Validation API
- **API Documentation**: Swagger
- **Build Tool**: Maven

---

## Security Implementation

### JWT Authentication

- Implements JWT for securing APIs.
- **Token Time-To-Live (TTL)**: 60 minutes.
- **Signature Algorithm**: HS512.
- Only specific endpoints are permitted without authentication;
  signup and login.
- All others require a valid JWT.

### Password Hashing

- Passwords are hashed using the **BCryptPasswordEncoder** algorithm before being stored in the database, ensuring secure authentication and storage.

### Authentication API

### Endpoints

#### 1. POST `/api/auth/signup`
- Registers a new user.

#### 2. POST `/api/auth/login`
- Logs in a user and returns an access token.

#### 3. POST `/api/auth/logout`
- Logs out a user.
- Requires the access token in the Authorization header.

---

## Database and ORM

- **Database**: h2 is used to store all entities and data.
- **ORM Framework**: Hibernate is used for interacting with the database.
- Entities are mapped to database tables using **Java Persistence API (JPA)** annotations.
- **A script file** used to populate SKU data on app startup in the path 'src/main/resources/data.sql'

---

## Validation

- Input validation is implemented using the **Java Bean Validation API**.
- Ensures data integrity by validating required fields, data types, and constraints.
- Examples of validations:
  - Validating email format.
  - Ensuring password meets security requirements.
  - Checking for non-null fields like `name` or `card number`.

---

## API Documentation

- Swagger is used for API documentation.
- a compatible version of openapi 2.8.6 is used along with spring 3.4.5 to add swagger as
documentation tool.
- Access Swagger UI at: http://localhost:8080/swagger-ui.html.

## ExceptionHandling
- custom exceptions used along with the GlobalExceptionHandler to handle business
errors.

## Caching
- Redis is used as a solution for caching.
- An embedded Redis instance is used on the default port (6379) for local development.
- Redis acts as a caching layer to reduce database hits and improve performance for frequently accessed data.

We use RedisTemplate to:

- Store data in Redis (e.g., product lists).
- Fetch data from Redis before querying the database.
- This setup helps minimize load on the database and speeds up response times for read-heavy operations.
- Data can be optionally expired using TTL (not enforced by default).

## Logging with Log4J

- Log4J is used as the default logging framework.
- Used in some cases like authentication and registration.
- Logging guidelines:
    - **INFO level**: Logs function entry points.
    - **DEBUG level**: Logs function parameters and detailed execution steps.
    - **ERROR level**: Logs exceptions in catch blocks.
    - **WARN level**: Logs unexpected results that do not cause system errors.
    - **DEBUG level**: Used as needed for deep debugging.
---

## Spring Boot Application Setup Instructions

1. Clone the repository:  
   `git clone https://github.com/morabicit/timeLog_backend.git`  
   `cd <timeLog_backend>`

2. Ensure the following prerequisites are installed on your system:  
   - Java 17
   - MySQL database server  
   - Redis server  

3. Open the `src/main/resources/application.properties` file and update the properties if needed:

   ```properties
    spring.datasource.url=jdbc:h2:mem:shahid;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    spring.datasource.driver-class-name=org.h2.Driver
    spring.datasource.username=root
    spring.datasource.password=1234

   ```

4. Build the application using Maven and skip tests:
   ```sh
   mvn clean install
   ```

5. Run the application:
   ```sh
   mvn spring-boot:run
   ```

6. Verify the application is running on `http://localhost:8080` and ensure your h2 database is accessible through the http://localhost:8080/h2-console/. The application will automatically create or update tables in the database based on the JPA configuration.

---
