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

