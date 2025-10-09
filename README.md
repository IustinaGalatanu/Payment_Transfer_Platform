# PayPlatform

**PayPlatform** is a Spring Boot backend application that simulates a digital payment system between users.  
It supports deposits, withdrawals, and transfers, while automatically detecting suspicious or invalid financial activity.  
The project was built with **Java 21**, focusing on transactional integrity, business rule validation, and clean service-based architecture.

---

## Overview

The system manages:
- **Users** – with name, iban and account balance  
- **Transactions** – deposit, withdraw, and transfer operations with validations  
- **Alerts** – automatically generated when unusual or risky patterns are detected  

The goal was to implement a realistic business workflow with transaction safety and an alert mechanism using `@Transactional` and `Propagation.REQUIRES_NEW`.

---

## Core Features

- Create and manage transactions (deposit, withdraw, transfer)  
- Validate and update user balances automatically  
- Generate alerts based on business rules:
  - **High Value Transaction** → amount > 10,000 RON  
  - **Suspicious Activity** → more than 5 transactions per minute  
  - **Negative Attempt** → transfer or withdrawal exceeds available balance  
- RESTful API with DTOs and clean JSON responses  
- Database support: H2 (development) and PostgreSQL (production)

---

## Technical Stack

| Layer | Technology |
|--------|-------------|
| Language | Java 21 |
| Framework | Spring Boot 3.5.6 |
| ORM | Spring Data JPA (Hibernate) |
| Database | H2 / PostgreSQL |
| Build Tool | Maven |
| Testing | JUnit 5, Mockito, MockMvc |
| Documentation | Swagger |
| Extras | Lombok |

---

## Architecture


com.example.payplatform
├── controller/ → REST endpoints
├── model/ → Entities (User, Transaction, Alert)
├── dto/ → Request/Response DTOs
├── repository/ → JPA repositories
├── service/ → Business logic interfaces
└── service/impl/ → Implementations with validation and alerts
---

## Example Business Flow

1. A user deposits 12,000 RON → balance increases → alert generated (HIGH_VALUE).  
2. A user performs 6 transactions in under one minute → alert generated (SUSPICIOUS_ACTIVITY).  
3. A user tries to withdraw more than their balance → transaction fails → alert generated (NEGATIVE_ATTEMPT).  

---

## License

  This project was developed for educational and portfolio purposes.
  
---

## How to Run

**Requirements:** Java 21, Maven  
```bash
git clone https://github.com/<username>/PayPlatform.git
cd PayPlatform
mvn spring-boot:run







