# Arquitetura do Access Token, Refresh Token e Logout (estudo de caso)

### Diagrama de Sequência
```mermaid
sequenceDiagram
  participant C as Client
  participant A as Authorization Server
  participant R as Resource Server
  participant DB as Token Store

  Note over C,A: LOGIN

  C->>A: 1. Authorization Grant (login)
  A->>DB: 2. Persist Refresh Token
  A->>C: 3. Access Token (short-lived) + Refresh Token

  Note over C,R: ACCESS PROTECTED RESOURCE

  C->>R: 4. Request with Access Token
  R->>R: 5. Validate JWT signature & expiration
  R->>C: 6. Return Protected Resource

  Note over C,R: Access Token Expired

  C->>R: 7. Request with expired Access Token
  R-->>C: 8. 401 Unauthorized (token expired)

  Note over C,A: TOKEN REFRESH

  C->>A: 9. Send Refresh Token
  A->>DB: 10. Validate Refresh Token (not revoked / not expired)
  A->>DB: 11. Invalidate old Refresh Token (rotation)
  A->>DB: 12. Persist new Refresh Token
  A->>C: 13. New Access Token + New Refresh Token

  Note over C,R: RETRY ORIGINAL REQUEST

  C->>R: 14. Retry request with new Access Token
  R->>R: 15. Validate JWT
  R->>C: 16. Return Protected Resource

  Note over C,A: LOGOUT

  C->>A: 17. Logout (send Refresh Token)
  A->>DB: 18. Revoke Refresh Token
  A-->>C: 19. Logout Success
```

### Etapas do Diagrama

#### Login
...

#### Access Protected Resource
...

#### Access Token Expired
...

#### Token Refresh
...

#### Logout
...


### Anotações
#### Política de Sessões dos Usuários
Dependendo da política de sessão que será implementada, existem três caminhos possíveis nessa estratégia, sendo elas:
- 1. Permitir múltiplas sessões
- 2. Limitar quantidade de sessões por usuário
- 3. Single Session
