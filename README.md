# MiniBank

API REST de gestion de comptes bancaires et de virements, développée en **Java 17 / Spring Boot 3** selon une **architecture hexagonale**.

MiniBank est un projet personnel dont l'objectif est de reproduire, à petite échelle, les exigences d'une application bancaire réelle : intégrité des données, cohérence des soldes, résistance aux doubles soumissions et aux accès concurrents, et un code métier testable indépendamment de l'infrastructure.

> **Statut : en cours de développement.** Voir la [feuille de route](#feuille-de-route).

---

## Fonctionnalités

- Inscription de clients
- Ouverture de comptes (solde initial à zéro, devise au choix)
- Blocage d'un compte
- Virements entre comptes, exécutés dans une transaction unique
- Historique paginé des écritures d'un compte

## Stack technique

| Domaine | Outils |
| --- | --- |
| Langage | Java 17 |
| Framework | Spring Boot 3, Spring Web, Spring Data JPA, Validation |
| Base de données | PostgreSQL, migrations Flyway |
| Build | Maven (wrapper `mvnw`) |
| Tests | JUnit 5, Mockito, Testcontainers, ArchUnit |
| Documentation API | OpenAPI / Swagger (springdoc) |
| Conteneurisation | Docker, Docker Compose |
| CI | GitHub Actions |

---

## Architecture

Le projet suit une **architecture hexagonale** (ports et adaptateurs). Le domaine métier est au centre et ne dépend d'aucune technologie : ni Spring, ni JPA, ni HTTP.

```
src/main/java/fr/techcrud/minibank/
├── domain/                  # Cœur métier, Java pur
│   ├── model/               # Customer, Account, Transfer, LedgerEntry, Money…
│   └── exception/           # InsufficientFundsException, AccountBlockedException…
├── application/             # Cas d'usage
│   ├── port/in/             # Ce que l'application offre (TransferMoneyUseCase…)
│   ├── port/out/            # Ce dont elle a besoin (AccountRepository…)
│   └── service/             # Orchestration des cas d'usage
└── adapter/
    ├── in/web/              # Contrôleurs REST, DTO, gestion des erreurs
    └── out/persistence/     # Entités JPA, repositories Spring Data, mappers
```

**Règle de dépendance :** le domaine ne dépend de rien, l'application dépend uniquement du domaine, et les adaptateurs dépendent de l'application et du domaine. Jamais l'inverse. Cette règle est vérifiée automatiquement par un test ArchUnit.

### Modèle du domaine

| Concept | Nature | Rôle |
| --- | --- | --- |
| `Customer` | Entité | Une personne cliente de la banque |
| `Account` | Entité | Un compte détenant un solde dans une devise, rattaché à un client |
| `Transfer` | Entité | Un virement d'un compte source vers un compte cible |
| `LedgerEntry` | Entité | Une écriture de débit ou de crédit dans l'historique d'un compte |
| `Money` | Value object | Un montant et sa devise, immuable |

L'argent circule toujours **entre des comptes**, jamais entre des clients.

---

## Règles métier

- Un compte s'ouvre avec un solde nul.
- Un montant de virement est strictement positif.
- Un virement est refusé si le solde de la source est insuffisant.
- Un virement est refusé si l'un des comptes est bloqué.
- La source et la cible d'un virement sont différentes et dans la même devise.
- Chaque virement produit deux écritures (un débit et un crédit), jamais modifiées ni supprimées.

## Choix techniques

**Montants en `BigDecimal`.** Les nombres à virgule flottante (`double`) produisent des erreurs d'arrondi inacceptables pour de l'argent. Les montants sont stockés en `NUMERIC(19,4)` côté base.

**Value object `Money`.** Le montant et la devise sont indissociables. Les opérations (`add`, `subtract`, `isLessThan`) refusent de combiner deux devises différentes.

**Transactions.** Le débit de la source et le crédit de la cible sont exécutés dans une seule transaction : soit les deux réussissent, soit aucun n'est appliqué.

**Idempotence.** Chaque demande de virement porte un en-tête `Idempotency-Key`, protégé par une contrainte d'unicité en base. Une requête rejouée (double clic, nouvelle tentative réseau) n'est exécutée qu'une seule fois.

**Concurrence.** Les comptes concernés par un virement sont verrouillés en base (`PESSIMISTIC_WRITE`), toujours dans le même ordre, pour éviter les soldes incohérents et les interblocages.

**Contraintes en base.** Des contraintes `CHECK` (solde positif, montant positif, statuts autorisés) servent de dernier filet de sécurité, en complément des règles du domaine.

**Identifiants générés par le domaine.** Les UUID sont créés par l'application, au moment où l'objet métier naît, et non par la base de données.

---

## Lancer le projet

### Prérequis

- JDK 17 ou plus récent
- Docker et Docker Compose

### Démarrage

```bash
# Démarrer PostgreSQL
docker compose up -d

# Lancer l'application (les migrations Flyway s'exécutent au démarrage)
./mvnw spring-boot:run
```

L'API est disponible sur `http://localhost:8080`, et sa documentation interactive sur `http://localhost:8080/swagger-ui.html`.

### Tests

```bash
./mvnw test
```

Les tests d'intégration démarrent automatiquement une base PostgreSQL éphémère avec Testcontainers : Docker doit être lancé.

---

## API

| Méthode | Route | Description |
| --- | --- | --- |
| `POST` | `/api/customers` | Inscrire un client |
| `GET` | `/api/customers/{id}` | Consulter un client |
| `POST` | `/api/accounts` | Ouvrir un compte |
| `GET` | `/api/accounts/{id}` | Consulter un compte et son solde |
| `POST` | `/api/accounts/{id}/block` | Bloquer un compte |
| `GET` | `/api/accounts/{id}/entries` | Historique paginé des écritures |
| `POST` | `/api/transfers` | Effectuer un virement (en-tête `Idempotency-Key` requis) |

---

## Feuille de route

- [x] Schéma de base de données et migration Flyway
- [x] Modèles `Customer`, `Account`, `Money`, `AccountStatus`
- [ ] Modèles `Transfer`, `LedgerEntry`, `TransferStatus`, `EntryType`
- [ ] Comportements métier de `Account` (`debit`, `credit`, `block`) et exceptions du domaine
- [ ] Ports et services applicatifs
- [ ] Adaptateur de persistance (JPA, Testcontainers)
- [ ] Adaptateur web (contrôleurs, gestion des erreurs, OpenAPI)
- [ ] Idempotence et test de virements concurrents
- [ ] Docker Compose et CI GitHub Actions
- [ ] Sécurité (Spring Security, JWT)
- [ ] Traitement batch d'intégration de transactions (Spring Batch)
- [ ] Service d'évaluation du risque (Python / FastAPI)
