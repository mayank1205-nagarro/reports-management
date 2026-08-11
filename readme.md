# Report Management System

A Spring Boot REST API for creating, assigning, searching, auditing, and exporting reports.

The application uses different persistence technologies according to data characteristics:

- **SQLite**: Department and user relational data for local development
- **MongoDB**: Report and report-history documents
- **JWT**: Stateless authentication
- **Spring Security**: Authentication and role-based authorization
- **Swagger/OpenAPI**: Interactive API documentation
- **Apache POI**: Excel export

> **Submission note:** The original requirement specifies **MySQL** for relational data. SQLite is currently configured for local development because MySQL is not available.

---

## Features

- User login using username and password
- JWT generation after successful authentication
- Role-based authorization using `ADMIN` and `USER`
- Department creation restricted to administrators
- Department lookup
- Report creation without department assignment
- Report assignment to a department after creation
- Report assignment history and audit information
- Report filtering
- Multiple-report Excel export
- Centralized exception handling
- API request, response-status, and error logging
- Swagger UI documentation

---

## Technology Stack

- Java 17+
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Data MongoDB
- Spring Security
- JWT
- Hibernate
- SQLite for local development
- MongoDB
- Apache POI
- Maven

---

## High-Level Architecture

```text
Client
  |
  |  Username/password
  v
Auth API
  |
  |  JWT token
  v
Spring Security JWT Filter
  |
  v
Controller
  |
  v
Service Layer
  |
  +--------------------+
  |                    |
  v                    v
SQLite               MongoDB
Users                Reports
Departments          Report History
