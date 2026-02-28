## Endpoints da Aplicação

#### Endpoints
Veja todos os endpoints disponíveis, seus métodos HTTP, URL, descrição e método de autenticação.
##### Authorizations

| Method | URL                 | Description   | Authentication |
|--------|---------------------|---------------|----------------|
| POST   | `/api/auth/signup`  | Sign Up       | public         |
| POST   | `/api/auth/signin`  | Sign In       | public         |
| POST   | `/api/auth/logout`  | Logout        | public         |
| POST   | `/api/auth/refresh` | Refresh Token | public         |

##### Users

| Method | URL                           | Description       | Authentication          |
|--------|-------------------------------|-------------------|-------------------------|
| GET    | `/api/users?username=&email=` | Get All Users     | basicAuth               |
| POST   | `/api/users`                  | Create User       | basicAuth               |
| GET    | `/api/users/me`               | Get User Infos    | basicAuth or bearerAuth |
| GET    | `/api/users/{userId}`         | Get User By ID    | basicAuth               |
| DELETE | `/api/users/{userId}`         | Delete User By ID | basicAuth               |
| PUT    | `/api/users/{userId}`         | Change User By ID | basicAuth               |

#### Query Parameters (Users)

| Parameter | Type     | Required | Description                                 |
|-----------|----------|----------|---------------------------------------------|
| username  | `STRING` | no       | Filter contacts by username (partial match) |
| email     | `STRING` | no       | Filter contacts by e-mail (partial match)   |


##### Contacts

| Method | URL                                  | Description             | Authentication          |
|--------|--------------------------------------|-------------------------|-------------------------|
| GET    | `/api/contacts?name=&email=`         | Get All Contacts        | basicAuth or bearerAuth |
| POST   | `/api/contacts`                      | Add Contact             | basicAuth or bearerAuth |
| GET    | `/api/contacts/{contactId}`          | Contact By ID           | basicAuth or bearerAuth |
| PUT    | `/api/contacts/{contactId}`          | Change Contact by ID    | basicAuth or bearerAuth |
| DELETE | `/api/contacts/{contactId}`          | Delete Contact By ID    | basicAuth or bearerAuth |
| GET    | `/api/contacts/favorites`            | All Favorites Contacts. | basicAuth or bearerAuth |
| PATCH  | `/api/contacts/{contactId}/favorite` | Favorite Contact.       | basicAuth or bearerAuth |

#### Query Parameters (Contacts)
| Parameter | Type     | Required | Description                               |
|-----------|----------|----------|-------------------------------------------|
| name      | `STRING` | no       | Filter contacts by name (partial match)   |
| email     | `STRING` | no       | Filter contacts by e-mail (partial match) |