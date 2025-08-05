# API REST de Contatos

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

#### 🔐 Autorização

| Método | URL                                         | Descrição                                         |
| ------ | ------------------------------------------- | ------------------------------------------------- |
| POST   | `/api/auth/register`                        | Registrar um novo usuário                         |
| POST   | `/api/auth/login`                           | Login e obter Token JWT                           |

#### 👤 Usuário

| Método | URL                                         | Descrição                                         |
| ------ | ------------------------------------------- | ------------------------------------------------- |
| GET   | `/api/users/me`                              | Buscar dados do usuário autenticado               |

#### 📇 Contatos (Apenas para Usuários Autenticados)

| Método | URL                  | Descrição                    |
| ------ | -------------------- | ---------------------------- |
| GET    | `/api/contacts`      | Listar contatos do usuário   |
| GET    | `/api/contacts/{id}` | Buscar um contato específico |
| POST   | `/api/contacts`      | Criar novo contato           |
| PUT    | `/api/contacts/{id}` | Atualizar contato            |
| DELETE | `/api/contacts/{id}` | Deletar contato              |
