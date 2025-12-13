# API REST for Contacts

### Tecnologias
<section align="left">
    <img alt="Static Badge" src="https://img.shields.io/badge/Java 21.0.7-grey?style=flat&logo=openjdk">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Boot 3.5.3-grey?style=flat&logo=springboot">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Web-grey?style=flat&logo=Spring%20Boot">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Validation-grey?style=flat&logo=Spring%20Boot">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring Security-grey?style=flat&logo=Spring%20Boot"> 
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Data%20JPA-grey?style=flat&logo=Spring%20Boot"> 
    <img alt="Static Badge" src="https://img.shields.io/badge/Docker-grey?style=flat&logo=Docker">
    <img alt="Static Badge" src="https://img.shields.io/badge/Redis-grey?style=flat&logo=Redis">
    <img alt="Static Badge" src="https://img.shields.io/badge/PostgreSQL-grey?style=flat&logo=PostgreSQL">
    <img alt="Static Badge" src="https://img.shields.io/badge/pgAdmin-grey?style=flat&logo=PostgreSQL">
    <img alt="Static Badge" src="https://img.shields.io/badge/Postman-grey?style=flat&logo=Postman">
    <img alt="Static Badge" src="https://img.shields.io/badge/Swagger/OpenAPI-grey?style=flat&logo=Swagger">
    <img alt="Static Badge" src="https://img.shields.io/badge/Yaml-grey?style=flat&logo=yaml">
    <img alt="Static Badge" src="https://img.shields.io/badge/Hibernate-grey?style=flat&logo=Hibernate">
    <img alt="Static Badge" src="https://img.shields.io/badge/Token JWT-grey?style=flat&logo=JSON">
</section>

### API REST Contract and Definitions
#### Endpoints
##### Auth

| Method | URL                 | Description                | Authentication |
| ------ | ------------------- | -------------------------- | -------------- |
| POST   | `/api/auth/signup`  | Register a new user        | Public         |
| POST   | `/api/auth/signin`  | Login and obtain JWT token | Public         |
| POST   | `/api/auth/signout` | Logout (invalidate token)  | JWT Required  |


##### Users

| Method | URL             | Description                 | Authentication |
| ------ | --------------- | --------------------------- | -------------- |
| GET    | `/api/users/me` | Get authenticated user data | JWT Required   |
| GET    | `/api/users`    | Get all users               | Public         |

##### Contacts

| Method | URL                  | Description            | Authentication |
| ------ |----------------------| ---------------------- | -------------- |
| GET    | `/api/contacts`      | List my contacts       | JWT Required   |
| GET    | `/api/contacts/{id}` | Get a specific contact | JWT Required   |
| POST   | `/api/contacts`      | Create a new contact   | JWT Required   |
| PUT    | `/api/contacts/{id}` | Update a contact       | JWT Required   |
| DELETE | `/api/contacts/{id}` | Delete a contact       | JWT Required   |


