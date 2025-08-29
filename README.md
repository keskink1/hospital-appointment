# Hospital Appointment

A secure and robust Spring Boot application for managing doctors and patients in a hospital system.

## Features

* **JWT Authentication**: Implements a secure authentication system using JSON Web Tokens (JWT) with both access and refresh tokens.

* **Role-Based Access Control**: Defines granular access rules. Doctors (ADMIN) can manage other doctors and patients, while patients (USER) have limited access to their own data.

* **Database Management**: Uses Spring Data JPA with MySQL for data persistence and Flyway for database migrations.

* **Soft Deletion**: Implements soft-delete functionality for doctors, ensuring data is retained for audit purposes.

* **API Documentation**: Provides comprehensive API documentation with **SpringDoc OpenAPI (Swagger UI)**.

* **Data Mapping**: Uses **MapStruct** for efficient and safe DTO-to-entity mapping.

* **Validation**: Validates all incoming request data to ensure data integrity.

* **Global Exception Handling**: Provides consistent and informative error responses for common exceptions across the entire application.

* **Auditing**: Automatically tracks the creation and modification dates and users for all entities.

## Technologies

* **Java 17**

* **Spring Boot 3**

* **Spring Security** (JWT)

* **Spring Data JPA**

* **MySQL**

* **Lombok**

* **MapStruct**

* **Flyway**

* **SpringDoc OpenAPI**

* **`jjwt`**

## Authentication and Authorization

This application uses a JWT-based authentication system.

* **Registration**: Doctors and patients can register via public endpoints.

* **Login**: After registration, users log in to receive an access token (for API calls) and a refresh token (for token renewal).

* **Access Control**: The application uses `hasRole()` and `authenticated()` rules to restrict access to certain endpoints. For example, only authenticated doctors with the `ADMIN` role can update or delete other users.

## Audit

The application uses **Spring Data Auditing** to automatically track who created and last modified each entity in the database.

## API Endpoints

### Authentication & User Management

| Method | Endpoint | Description | Access | 
 | --- | --- | --- | --- | 
| `POST` | `/auth/login` | Authenticates a user and returns JWTs. | `Public` | 
| `POST` | `/auth/refresh` | Refreshes a user's access token. | `Public` | 
| `POST` | `/users/register/doctor` | Registers a new doctor. | `Public` | 
| `POST` | `/users/register/patient` | Registers a new patient. | `Public` | 
| `GET` | `/auth/me` | Retrieves the current user's details. | `Auth` | 

### Doctor Endpoints

| Method | Endpoint | Description | Access | 
 | --- | --- | --- | --- | 
| `GET` | `/doctors` | Get all doctors. | `Public` | 
| `GET` | `/doctors/{id}` | Get a doctor by ID. | `Public` | 
| `PUT` | `/doctors/{id}` | Update a doctor's details. | `ADMIN` | 
| `DELETE` | `/doctors/{id}` | Soft-delete a doctor by ID. | `ADMIN` | 
| `GET` | `/doctors/admin/getAll` | Get all doctors for admin view. | `ADMIN` | 
| `GET` | `/doctors/{doctorId}/patients` | Get all patients for a specific doctor. | `ADMIN` | 

### Patient Endpoints

| Method | Endpoint | Description | Access | 
 | --- | --- | --- | --- | 
| `GET` | `/patients/{id}` | Get a patient by ID. | `Auth` | 
| `POST` | `/doctors/{doctorId}/patients/{patientId}` | Adds a patient to a doctor. | `ADMIN` | 
| `DELETE` | `/doctors/{doctorId}/patients/{patientId}` | Removes a patient from a doctor. | `ADMIN` | 

## Example JSON Requests

### Login

This request is made after a user has been registered.
```json
{
  "email": "john.doe@example.com",
  "password": "yourpassword123"
}
```
### Create Doctor
```json

{
  "name": "Jane",
  "surname": "Doe",
  "email": "jane.doe@hospital.com",
  "phoneNumber": "5551112222",
  "password": "StrongPassword123!",
  "registrationNumber": "28765432",
  "department": "Orthopedics",
  "proficiency": "MD"
}
```
### Create Patient
```json

{
  "name": "John",
  "surname": "Smith",
  "email": "john.smith@mail.com",
  "phoneNumber": "5553334444",
  "password": "MyPatientPassword!",
  "nationalId": "12345678910",
  "birthDate": "1995-10-25"
}

```
