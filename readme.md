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
- Report creation
- Report assignment to a predefined department
- Report assignment history and audit information
- Report filtering using report ID and creation date range
- Multiple-report Excel export
- Centralized exception handling
- API request, response status, and error logging
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
- SQLite
- MongoDB
- Apache POI
- Maven

---

## High-Level Architecture

```text
Client
  |
  | Username/password
  v
Authentication API
  |
  | JWT token
  v
Spring Security JWT Filter
  |
  v
Controller Layer
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
```

---

## Steps to Run the Application

### 1. Clone the repository

```bash
git clone https://github.com/mayank1205-nagarro/reports-management.git
```

### 2. Navigate to the project folder

```bash
cd reports-management
```

### 3. Start MongoDB

Ensure MongoDB is running on the default port `27017`.

### 4. Build the application

```bash
mvn clean install
```

### 5. Run the application

```bash
mvn spring-boot:run
```

The application will start at:

```text
http://localhost:8080
```

### 6. Open Swagger UI

```text
http://localhost:8080/swagger-ui/index.html
```

### 7. Login and authorize

Use the login API with either:

```text
ADMIN user
Username: admin
Password: admin123
```

or:

```text
USER user
Username: user
Password: admin123
```

Copy the JWT returned by the login API, click **Authorize** in Swagger UI, enter the token, and test the available APIs.

The `ADMIN` user can create departments and export reports. Authenticated users can access permitted report APIs according to authorization rules.
