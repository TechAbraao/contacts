# REST API for Contacts

### Tecnologias
<section align="left">
    <img alt="Static Badge" src="https://img.shields.io/badge/Java 21.0.7-grey?style=flat&logo=openjdk">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Boot 3.5.3-grey?style=flat&logo=springboot">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Web-grey?style=flat&logo=Spring%20Boot">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Validation-grey?style=flat&logo=Spring%20Boot">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring Security-grey?style=flat&logo=Spring%20Boot"> 
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Data%20JPA-grey?style=flat&logo=Spring%20Boot"> 
    <br>
    <img alt="Static Badge" src="https://img.shields.io/badge/Docker-grey?style=flat&logo=Docker">
    <img alt="Static Badge" src="https://img.shields.io/badge/PostgreSQL-grey?style=flat&logo=PostgreSQL">
    <img alt="Static Badge" src="https://img.shields.io/badge/pgAdmin-grey?style=flat&logo=PostgreSQL">
    <img alt="Static Badge" src="https://img.shields.io/badge/Postman-grey?style=flat&logo=Postman">
    <img alt="Static Badge" src="https://img.shields.io/badge/Yaml-grey?style=flat&logo=yaml">
    <img alt="Static Badge" src="https://img.shields.io/badge/Hibernate-grey?style=flat&logo=Hibernate">
<img alt="Static Badge" src="https://img.shields.io/badge/Token JWT-grey?style=flat&logo=JSON">
</section>

#### 🔐 Authorization

| Method | URL                          | Description                |
| ------ | ---------------------------- | -------------------------- |
| POST   | `/api/authorization/signup`  | Register a new user        |
| POST   | `/api/authorization/signin`  | Login and obtain JWT token |
| POST   | `/api/authorization/signout` | Logout                     |


#### 👤 User

| Method | URL             | Description                 |
| ------ | --------------- | --------------------------- |
| GET    | `/api/users/me` | Get authenticated user data |


#### 📇 Contacts (Authenticated Users Only)

| Method | URL                  | Description            |
| ------ | -------------------- | ---------------------- |
| GET    | `/api/contacts`      | List user’s contacts   |
| GET    | `/api/contacts/{id}` | Get a specific contact |
| POST   | `/api/contacts`      | Create a new contact   |
| PUT    | `/api/contacts/{id}` | Update a contact       |
| DELETE | `/api/contacts/{id}` | Delete a contact       |

