# Hospital Appointment

Spring Boot application for managing doctors and patients in a hospital system.

## Features

- Manage **Doctors**: Create, Read, Update, Delete
- Manage **Patients**: Create, Read, Update, Delete
- View all patients of a specific doctor
- Soft delete functionality (`isDeleted` flag)
- DTO mapping with **MapStruct**
- Validation for input data
- Global exception handling for common errors
- API documentation with **SpringDoc OpenAPI**

## Technologies Used

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Web
- MySQL
- Lombok
- MapStruct
- Spring Boot Validation
- SpringDoc OpenAPI

## Authentication

Currently, the application **does not implement authentication or JWT**.  
All APIs are publicly accessible. Authentication and authorization will be added in a future update.

## Internationalization (i18n)

The application supports **internationalization (i18n)**.  
All messages and API responses are localized based on the user's locale.  
Currently, messages are stored in properties files and handled via `MessageResponseUtil`.

## Audit / Logging

The application includes **audit logging** for tracking important actions such as creating, updating, or deleting doctors and patients.  
All audit logs can be used for monitoring, debugging, and compliance purposes.



## API Endpoints

### Doctors
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/doctors` | Get all doctors |
| POST | `/api/doctors/createDoctor` | Create a new doctor |
| PUT | `/api/doctors/updateDoctor/{id}` | Update an existing doctor by ID |
| DELETE | `/api/doctors/deleteDoctor/{id}` | Delete a doctor by ID (soft delete) |
| GET | `/api/doctors/{doctorId}/patients` | Get all patients for a specific doctor |

### Patients
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/patients` | Get all patients |
| POST | `/api/patients/createPatient` | Create a new patient |
| PUT | `/api/patients/updatePatient/{id}` | Update an existing patient by ID |
| DELETE | `/api/patients/deletePatient/{id}` | Delete a patient by ID (soft delete) |

## Example JSON Requests

### Create Doctor
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "department": "Cardiology",
  "proficiency": "Senior",
  "phoneNumber": "555-1234",
  "email": "john.doe@example.com",
  "registrationNumber": "123456"
}
