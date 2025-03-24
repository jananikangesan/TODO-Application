
# Task Management API

A Spring Boot application that provides CRUD operations for task management. The application integrates with an external API, supports searching, filtering, and task grouping by completion status. It also implements **JWT-based authentication** for securing API endpoints.

## **Technologies Used**
- **Spring Boot** (Spring Web, Spring Data JPA)
- **MySQL** (Database)
- **Springdoc OpenAPI** (Swagger Documentation)
- **Lombok** (Simplify Java code)
- **Maven** (Dependency Management)
- **JWT (JSON Web Tokens)** for Authentication and Authorization

## **Project Setup**

### **Prerequisites**
Ensure you have the following installed:
- Java 17
- Apache Maven 3.9.9
- MySQL
  
### **Steps to Run the Application**
1. **Clone the repository**
   * git clone https://github.com/jananikangesan/TODO-Application.git
   * cd TODO-Application
2. **Configure Database**
   * Update `src/main/resources/application.properties` with your database credentials.
3. **Build the project** :  mvn clean install
4. **Run the application**  : mvn spring-boot:run
5. **Access the API** :
   * API runs on http://localhost:8085
   * Swagger UI: http://localhost:8085/swagger-ui/index.html

### Access Swagger UI for API documentation:
* http://localhost:8085/swagger-ui/index.html

## JWT Authentication

This application uses **JWT (JSON Web Token)** for securing the API endpoints. The authentication process involves the following steps:

### **1. User Login**
- **POST /login**: A user can login by sending a POST request to the `/login` endpoint with their **email** and **password** in the request body.
- If the credentials are correct, a **JWT token** will be generated and returned.

### **2. Accessing Secured API Endpoints**
- All tasks-related API endpoints (e.g., `/tasks/**`) require the user to be authenticated.
- Include the JWT token in the `Authorization` header as `Bearer <token>` when making API requests.

Example of Authorization header:
```http
Authorization: Bearer <JWT_TOKEN>
```

### **Sample Authentication Request and Response**

**Login Request:**
```http
POST /login
Content-Type: application/json
```
**Body:**
```json
{
  "username": "user1",
  "password": "password123"
}
```

**Login Response (Success):**
```json
{
  "token": "<JWT_TOKEN>"
}
```

### **Secured API Requests**

**Fetch All Tasks (Secured)**
```http
GET /tasks?page=0&size=5
Authorization: Bearer <JWT_TOKEN>
```

**Update a Task (Secured)**
```http
PUT /tasks/3
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

**Body:**
```json
{
  "title": "Write API Documentation (Updated)",
  "completed": true
}
```

---

## Database Schema

The application uses a relational database (**MySQL**) to store task-related information.

### **1. Task Table**
The task table stores all tasks with their details.

| Column      | Type          | Constraints                   | Description                         |
|------------|--------------|------------------------------|-------------------------------------|
| `id`       | BIGINT (PK)  | NOT NULL                     | Unique identifier for each task    |
| `user_id`  | BIGINT       | DEFAULT NULL                 | The ID of the user who owns the task |
| `title`    | VARCHAR(255) | DEFAULT NULL                 | Title or name of the task          |
| `completed`| BOOLEAN      | DEFAULT NULL                 | Status of task completion          |
| `created_at`| DATETIME(6) | DEFAULT NULL                 | Task creation time                 |
| `updated_at`| DATETIME(6) | DEFAULT NULL                 | Last update time                   |

---

## Sample API Requests and Responses

### 1️. Fetch All Stored Tasks (Paginated)
**Request:**
```http
GET /tasks?page=0&size=5
Authorization: Bearer <JWT_TOKEN>
```
 **Response:**
```json
{
  "tasks": [
    {
      "id": 1,
      "userId": 101,
      "title": "Complete project",
      "completed": false,
      "createdAt": "2025-03-20T10:00:00",
      "updatedAt": "2025-03-21T12:00:00"
    },
    {
      "id": 2,
      "userId": 102,
      "title": "Prepare for meeting",
      "completed": true,
      "createdAt": "2025-03-21T11:30:00",
      "updatedAt": "2025-03-22T08:00:00"
    }
  ],
  "currentPage": 0,
  "totalPages": 5
}
```

### 2️. Fetch a Task by ID
 **Request:**
```http
GET /tasks/1
Authorization: Bearer <JWT_TOKEN>
```
 **Response:**
```json
{
  "id": 1,
  "userId": 101,
  "title": "Complete project",
  "completed": false,
  "createdAt": "2025-03-20T10:00:00",
  "updatedAt": "2025-03-21T12:00:00"
}
```

### 3. Update a Task
 **Request:**
```http
PUT /tasks/3
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```
 **Body:**
```json
{
  "title": "Write API Documentation (Updated)",
  "completed": true
}
```
 **Response:**
```json
{
  "id": 3,
  "userId": 103,
  "title": "Write API Documentation (Updated)",
  "completed": true,
  "createdAt": "2025-03-23T08:30:00",
  "updatedAt": "2025-03-23T09:00:00"
}
```

### 4. Delete a Task
 **Request:**
```http
DELETE /tasks/3
Authorization: Bearer <JWT_TOKEN>
```
**Response:**
```json
{
   "Task deleted successfully"
}
```

---

### **Security Configuration**

JWT authentication is implemented using a custom filter (`JwtAuthenticationFilter`). All endpoints except for `/login` and `/register` are secured and require a valid JWT token to access.

---
