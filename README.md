# API REST for Contacts

### Technologies
<section align="left">
    <img alt="Static Badge" src="https://img.shields.io/badge/Java 21.0.7-grey?style=flat&logo=openjdk">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Boot 3.5.3-grey?style=flat&logo=springboot">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Web-grey?style=flat&logo=Spring%20Boot">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Validation-grey?style=flat&logo=Spring%20Boot">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring Security-grey?style=flat&logo=Spring%20Boot"> 
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Data%20JPA-grey?style=flat&logo=Spring%20Boot"> 
    <img alt="Static Badge" src="https://img.shields.io/badge/JUnit5-grey?style=flat&logo=JUnit5"> 
    <img alt="Static Badge" src="https://img.shields.io/badge/Docker-grey?style=flat&logo=Docker">
    <img alt="Static Badge" src="https://img.shields.io/badge/Redis-grey?style=flat&logo=Redis">
    <img alt="Static Badge" src="https://img.shields.io/badge/PostgreSQL-grey?style=flat&logo=PostgreSQL">
    <img alt="Static Badge" src="https://img.shields.io/badge/pgAdmin-grey?style=flat&logo=PostgreSQL">
    <img alt="Static Badge" src="https://img.shields.io/badge/Postman-grey?style=flat&logo=Postman">
    <img alt="Static Badge" src="https://img.shields.io/badge/Swagger (OpenAPI)-grey?style=flat&logo=Swagger">
    <img alt="Static Badge" src="https://img.shields.io/badge/Yaml-grey?style=flat&logo=yaml">
    <img alt="Static Badge" src="https://img.shields.io/badge/Hibernate-grey?style=flat&logo=Hibernate">
    <img alt="Static Badge" src="https://img.shields.io/badge/Token JWT-grey?style=flat&logo=JSON">
</section>

---

### Pre-requisites
- Java 21
- Apache Maven (3.8.7+)
- Docker

---

### Getting Started

#### 1. Clone the repository
```bash
git clone git@github.com:TechAbraao/contacts.git
cd contacts
```

#### 2. Configure environment variables
Copy the example file and fill in your values:
```bash
cp .env.example .env
```
Minimal `.env` example:
```bash
## POSTGRESQL ##
POSTGRES_CONTAINER_NAME=contacts_postgres
POSTGRES_PORT=5432
POSTGRES_USER=postgres
POSTGRES_PASSWORD=secret
POSTGRES_DB=contacts_db

## PGADMIN ##
PGADMIN_CONTAINER_NAME=contacts_pgadmin
PGADMIN_PORT=8081
PGADMIN_EMAIL=admin@example.com
PGADMIN_PASSWORD=secret
```

---

### Flow 1 — Run locally (application on host, PostgreSQL on Docker)

Use this flow if you want to run the application directly with Maven, while the database runs in a container.

#### 1. Start the database container
```bash
docker compose --env-file .env -f docker/compose/docker-compose-dev.yml up -d
```
Or with Makefile (Linux/Unix):
```bash
make up
```
> Check out more commands by typing `make` in the terminal. These commands are solely for development assistance.

#### 2. Configure `application.yml`
Since the application runs on your host machine, it connects to PostgreSQL via `localhost`:
```yml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/contacts_db
    username: postgres
    password: secret
    driver-class-name: org.postgresql.Driver

  security:
    jwt:
      secret: secret
    user:
      email: admin@admin.com
      name: admin
      password: admin
```

#### 3. Run the application
```bash
mvn clean install -DskipTests
mvn spring-boot:run
```
To run with tests:
```bash
mvn test
```

#### 4. Availability
API:
```
http://localhost:8080/api/
```
Swagger UI:
```
http://localhost:8080/swagger-ui/index.html
```

---

### Flow 2 — Run everything with Docker (recommended for production)

Use this flow to run both the application and the database as containers.

#### 1. Build the application JAR
```bash
mvn package -DskipTests
```

#### 2. Build the Docker image
```bash
docker build -f docker/dockerfiles/Dockerfile -t contacts .
```

#### 3. Configure `application.yml`
Since both services run inside Docker, the application must connect to PostgreSQL using the **service name** defined in `docker-compose-prod.yml` (`postgres`), not `localhost`:
```yml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://postgres:5432/contacts_db
    username: postgres
    password: secret
    driver-class-name: org.postgresql.Driver

  security:
    jwt:
      secret: secret
    user:
      email: admin@admin.com
      name: admin
      password: admin
```

> Alternatively, you can keep `localhost` in `application.yml` and override the URL via environment variable in `docker-compose-prod.yml`:
> ```yaml
> environment:
>   SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/contacts_db
> ```

#### 4. Start all containers
```bash
docker compose --env-file .env -f docker/compose/docker-compose-prod.yml up -d
```

#### 5. Verify running containers
```bash
docker ps
```

#### 6. View application logs
```bash
docker logs contacts_app
```

#### 7. Stop all containers
```bash
docker compose down
```

#### 8. Availability
API:
```
http://localhost:8080/api/
```
Swagger UI:
```
http://localhost:8080/swagger-ui/index.html
```