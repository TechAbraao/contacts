# API REST for Contacts

### Tecnologias
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

### How to Start
#### Pre-requisites
- Java (21)
- Apache Maven (3.8.7+)
- Docker
- PostgreSQL
- Makefile (optional)

#### Running with Docker (recommended)
#### 1. Clone the repository
Clone and access the directory
```bash
git@github.com:TechAbraao/contacts.git
cd ./contacts
```

#### 2. Configure the environment variables.
Change the `.env.example` file to `.env`. For example:
````bash
cp .env.example .env
````
Now configure the necessary variables for the Docker container (minimal example):
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

#### 3. Configure the application.yml file.
The application is pre-configured to use environment variables. Below is the recommended configuration for your src/main/resources/application.yml (minimal example):
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
    user:
      email: admin@example.com
      name: admin
      password: secret
```

#### 4. Initialize the containers
If you have the Makefile (Linux/Unix system):
```bash
make start
```
If not, do it manually:
```bash
docker compose \
  --env-file .env \
  -f docker/compose/docker-compose.yml \
  up -d
```

#### 5. Run the application
```bash
mvn clean install
mvn spring-boot:run
```
If you want to run the tests:
```bash
mvn test
```

#### 6. Availability
The API will be available at
```bash
http://localhost:8000/api/
```
API Swagger
Check out the main returned and required payload formats and gain access to the API's Swagger.
```bash
http://localhost:8000/swagger-ui/index.html
```

### API RESTful Definitions
#### Endpoints
Check out all the endpoints available in this project.
##### Authorizations

| Method | URL                 | Description   | Authentication |
|--------|---------------------|---------------|----------------|
| POST   | `/api/auth/signup`  | Sign Up       | public         |
| POST   | `/api/auth/signin`  | Sign In       | public         |
| POST   | `/api/auth/signout` | Sign Out      | bearerAuth     |
| POST   | `/api/auth/refresh` | Refresh Token | bearerAuth     |

##### Users

| Method | URL                   | Description      | Authentication          |
|--------|-----------------------|------------------|-------------------------|
| GET    | `/api/users`          | Get All Users    | basicAuth               |
| POST   | `/api/users`          | Create User      | basicAuth               |
| GET    | `/api/users/me`       | Get User Infos   | basicAuth or bearerAuth |
| GET    | `/api/users/{userId}` | Get User By ID   | basicAuth               |
| DELETE | `/api/users/{userId}` | Delete User By ID | basicAuth               |
| PUT    | `/api/users/{userId}` | Change User By ID | basicAuth               |

##### Contacts

| Method | URL                         | Description          | Authentication          |
|--------|-----------------------------|----------------------|-------------------------|
| GET    | `/api/contacts`             | Get All Contacts     | basicAuth or bearerAuth |
| POST   | `/api/contacts`             | Add Contact          | basicAuth or bearerAuth |
| GET    | `/api/contacts/{contactId}` | Contact By ID        | basicAuth or bearerAuth |
| PUT    | `/api/contacts/{contactId}` | Change Contact by ID | basicAuth or bearerAuth |
| DELETE | `/api/contacts/{contactId}` | Delete Contact By ID | basicAuth or bearerAuth |
