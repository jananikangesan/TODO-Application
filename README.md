# Task Management API

A Spring Boot application that provides CRUD operations for task management. The application integrates with an external API, supports searching,filtering
and task grouping by completion status.

## **Technologies Used**
- **Spring Boot** (Spring Web, Spring Data JPA)
- **MySQL** (Database)
- **Springdoc OpenAPI** (Swagger Documentation)
- **Lombok** (Simplify Java code)
- **Maven** (Dependency Management)

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
   * Update src/main/resources/application.properties with your database credentials.
3. **Build the project** :  mvn clean install
4. **Run the application**  : mvn spring-boot:run
5. **Access the API** :
   * API runs on http://localhost:8085
   * Swagger UI: http://localhost:8085/swagger-ui/index.html

### Access Swagger UI for API documentation:

* http://localhost:8085/swagger-ui/index.html

## Database Schema

The application uses a relational database (**MySQL**) to store task-related information.

### **1. Task Table**
The task table stores all tasks with their details.

| Column     | Type         | Constraints         | Description                        |
|------------|-------------|---------------------|------------------------------------|
| id       | BIGINT (PK) | AUTO_INCREMENT      | Unique identifier for each task   |
| userId   | BIGINT      | NOT NULL            | The ID of the user who owns the task |
| title    | VARCHAR(255)| NOT NULL            | Title or name of the task         |
| completed| BOOLEAN     | DEFAULT FALSE       | Status of task completion         |
| createdAt| TIMESTAMP   | DEFAULT CURRENT_TIMESTAMP | Task creation time |
| updatedAt| TIMESTAMP   | ON UPDATE CURRENT_TIMESTAMP | Last update time |


##  Sample API Requests and Responses

### 1️. Fetch All Stored Tasks (Paginated)
**Request:**
```http
GET /tasks?page=0&size=5
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
```
**Response:**
```json
{
   "Task deleted successfully"
}



