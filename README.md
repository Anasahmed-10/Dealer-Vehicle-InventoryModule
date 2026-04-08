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

## API Endpoints

### Dealers

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/dealer` | Create a dealer |
| `GET` | `/dealers/{id}` | Get dealer by ID |
| `GET` | `/dealers` | List dealers (pagination/sort) |
| `PATCH` | `/dealers/{id}` | Update dealer |
| `DELETE` | `/dealers/{id}` | Delete dealer |

### Vehicles

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/vehicles` | Create a vehicle |
| `GET` | `/vehicles/{id}` | Get vehicle by ID |
| `GET` | `/vehicles` | List vehicles with filters and pagination |
| `PATCH` | `/vehicles/{id}` | Update vehicle |
| `DELETE` | `/vehicles/{id}` | Delete vehicle |

#### Supported Filters for `GET /vehicles`

- `model` (String) - Filter by vehicle model
- `status` - Filter by status: `AVAILABLE` or `SOLD`
- `priceMin` / `priceMax` (Decimal) - Filter by price range
- `subscription` - Filter by `PREMIUM` subscription (tenant-scoped filtering)

**Example Request:**
GET /vehicles?model=Civic&status=AVAILABLE&priceMin=20000&priceMax=50000&subscription=PREMIUM

### Admin

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/admin/dealers/countBySubscription` | Count dealers by subscription type |

---

## Request Headers 

### Tenant Operations

All tenant-scoped operations require the following headers:
```http
X-Tenant-Id: <tenant-id>
X-User-Role: TENANT_USER
```

**Example:**
```http
X-Tenant-Id: tenant-123
X-User-Role: TENANT_USER
```

### Admin Operations

Admin operation requires:
```http
X-User-Role: GLOBAL_ADMIN
```

**Note:** Admin operations do not require `X-Tenant-Id` as they have global access across all tenants.

---

## Validation & Error Handling

The API enforces strict validation rules to ensure data integrity and security:

| Scenario | HTTP Status | Description |
|----------|-------------|-------------|
| Missing `X-Tenant-Id` | `400 Bad Request` | Tenant ID is required for tenant-scoped operations |
| Cross-tenant access | `403 Forbidden` | Users cannot access resources from other tenants |
| Invalid enum values | `400 Bad Request` | Invalid subscription type or vehicle status |
| `priceMin > priceMax` | `400 Bad Request` | Price range is invalid |
| Invalid request body | `400 Bad Request` | Request validation failed |
| Resource not found | `404 Not Found` | Requested resource does not exist |
| Unauthorized access | `401 Unauthorized` | Missing or invalid authentication |

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