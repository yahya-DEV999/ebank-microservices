# Projet – Architecture micro-services (Partie 1)

Cours : Architecture JEE et Java avancé – Mundiapolis 2026

**Stack :** Java 17+ · Spring Boot 3.4 · Spring Cloud 2024.0 · Spring Data JPA · H2 ·
OpenFeign · Resilience4J · Spring Cloud Gateway · Eureka · Swagger (springdoc) · Lombok

## Architecture cible

```
                 ┌──────────────────────────┐
  Client REST ──►│ gateway-service  :8888   │
 (Postman, …)    └────────────┬─────────────┘
                              │  routes lb://
              ┌───────────────┴───────────────┐
              ▼                               ▼
   ┌──────────────────────┐   OpenFeign   ┌──────────────────────┐
   │ customer-service     │◄──────────────│ ebank-service        │
   │ :8081  (H2)          │  + Resilience4J│ :8082  (H2)          │
   └──────────┬───────────┘               └──────────┬───────────┘
              └───────────────┬───────────────────────┘
                              ▼  enregistrement
                 ┌──────────────────────────┐
                 │ discovery-service :8761  │  (Eureka)
                 └──────────────────────────┘
```

| Service | Port | Rôle |
|---|---|---|
| discovery-service | 8761 | Annuaire Eureka |
| gateway-service | 8888 | Point d'entrée unique |
| customer-service | 8081 | Gestion des clients |
| ebank-service | 8082 | Gestion des comptes bancaires |

## Étape 1 – Micro-service `customer-service`
- Dépendances : Spring Web, Spring Data JPA, H2, Lombok, springdoc-openapi (Swagger UI).
- Entité `Customer` (`id`, `firstName`, `lastName`, `email`) et `CustomerRepository` (Spring Data).
- `CustomerRestController` : `GET /api/customers`, `GET /api/customers/{id}`, `POST /api/customers`.
- Trois clients insérés au démarrage par un `CommandLineRunner`.

Test : http://localhost:8081/api/customers — Swagger : http://localhost:8081/swagger-ui.html

## Étape 2 – Micro-service `ebank-service`
- Entité `BankAccount` : `accountId` (UUID), `balance`, `createdAt`, `currency`, `type`
  (enum `AccountType` : `CURRENT_ACCOUNT` / `SAVING_ACCOUNT`) et `customerId`.
- Chaque micro-service a **sa propre base** : ebank-service ne stocke que l'identifiant du client.
- `BankAccountRestController` : `GET /api/accounts`, `GET /api/accounts/{id}`,
  `GET /api/customers/{customerId}/accounts`.
- Deux comptes (courant + épargne) créés pour chacun des clients 1 à 3.

Test : http://localhost:8082/api/accounts — Swagger : http://localhost:8082/swagger-ui.html

## Étape 3 – Communication entre les services avec OpenFeign
- Dépendance `spring-cloud-starter-openfeign` + `@EnableFeignClients`.
- `model/Customer` : simple classe (pas une entité) qui reçoit les données du client.
- `clients/CustomerRestClient` : interface `@FeignClient` ; Spring génère le client HTTP qui
  appelle `GET /api/customers/{id}` sur customer-service.
- `BankAccount.customer` est `@Transient` : il n'est pas stocké, il est rempli à chaque requête
  par le contrôleur.

Test (les deux services lancés) : http://localhost:8082/api/accounts → chaque compte contient
maintenant l'objet `customer`.

