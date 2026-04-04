# Dealer & Vehicle Inventory Module

## Overview

This is a **multi-tenant Inventory Module** built using **Spring Boot** for managing dealers and their vehicles.  
The module follows **clean architecture principles** and is part of a **modular monolith application**.

---

## Features

- **Dealers Management**
  - Create, read, update, delete dealers
  - Pagination and sorting support
  - Tenant-scoped access
  - Subscription types: `BASIC` and `PREMIUM`

- **Vehicles Management**
  - Create, read, update, delete vehicles
  - Filter vehicles by model, status, price range, and dealer subscription type
  - Pagination and sorting support
  - Tenant-scoped access
  - Admin can query vehicles across tenants

- **Admin Endpoints**
  - Count dealers by subscription type
  - Global admin access only

- **Security**
  - Role-based access control:
    - `TENANT_USER` → tenant-specific operations
    - `GLOBAL_ADMIN` → global operations
  - X-Tenant-Id header enforces tenant scoping

---

## Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Hibernate / JPA Specifications
- For local testing, PostgreSQL
- Maven for build management
- Spring Security for role-based access
- Validation with Hibernate Validator

---
## Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Hibernate / JPA Specifications
- H2 Database (for local testing, can be replaced with PostgreSQL/MySQL)
- Maven for build management
- Spring Security for role-based access
- Validation with Hibernate Validator

---

## Setup

1. Clone the repository:

```bash
git clone https://github.com/Anasahmed_01/Dealer-Vehicle-InventoryModule.git
cd inventoryModule
```
1. Build the project:
```bash
./mvnw clean install
```
3. Run the application
```bash
./mvnw spring-boot:run
```
The application will start on http://localhost:9090

---

## Testing

Use Postman to test the API:
1. Add required headers (X-Tenant-Id and X-User-Role)
2. Test dealer and vehicle CRUD operations
3. Test filters and admin endpoints
---
## Author 
Anas Ahmed

Github: https://github.com/Anasahmed-01

---
