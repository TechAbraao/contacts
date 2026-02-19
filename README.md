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

### API RESTful Definitions
#### Endpoints
Check out all the endpoints available in this project.
##### Authorizations

| Method | URL                 | Description                | Authentication          |
| ------ | ------------------- | -------------------------- |-------------------------|
| POST   | `/api/auth/signup`  | Register a new user        | public                  |
| POST   | `/api/auth/signin`  | Login and obtain JWT token | public                  |
| POST   | `/api/auth/signout` | Logout (invalidate token)  | basicAuth or bearerAuth |


##### Users

| Method | URL                   | Description                 | Authentication          |
|--------|-----------------------|-----------------------------|-------------------------|
| GET    | `/api/users/me`       | Get authenticated user data | basicAuth               |
| GET    | `/api/users`          | Get all Users               | basicAuth               |
| GET    | `/api/users/{userId}` | Get User by ID              | basicAuth               |
| POST   | `/api/users`          | Create a User               | basicAuth               |
| DELETE | `/api/users/{userId}` | Delete User by ID           | basicAuth or bearerAuth |

##### Contacts

| Method | URL                         | Description            | Authentication          |
|--------|-----------------------------|------------------------|-------------------------|
| GET    | `/api/contacts`             | List my contacts       | basicAuth or bearerAuth |
| GET    | `/api/contacts/{contactId}` | Get a specific contact | basicAuth or bearerAuth |
| POST   | `/api/contacts`             | Create a new contact   | basicAuth or bearerAuth |
| PUT    | `/api/contacts/{contactId}` | Update a contact       | basicAuth or bearerAuth |
| DELETE | `/api/contacts/{contactId}` | Delete a contact       | basicAuth or bearerAuth |

#### API Swagger
Check out the main returned and required payload formats and gain access to the API's Swagger.

```bash
http://localhost:<port>/swagger-ui/index.html
```
