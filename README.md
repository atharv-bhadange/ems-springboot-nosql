# Employee Management System

## How to run the application

### Prerequisites
- Java 11
- Maven
- Couchbase Server (Docker)
- Postman (Optional)

### Steps
1. Unzip the project folder.
2. Start the Couchbase Server using Docker by running the following command:
   ```bash
    docker run -d --name db -p 8091-8096:8091-8096 -p 11210-11211:11210-11211 couchbase
   ```
   - This command will start a Couchbase Server instance in a Docker container.
   - The Couchbase Web Console will be accessible at `http://localhost:8091/`.
3. Go to `http://localhost:8091/` to access Couchbase Web Console.
4. Give the following credentials to login:
  - Username: `Administrator`
  - Password: `password`
  - Finish with Default.
5. Add a new bucket named `employee`.
6. Open a terminal and navigate to the project folder.
7. Run the following command to build the application:
   ```bash
   mvn clean install
   ```
8. Run the following command to start the application:
   ```bash
   mvn spring-boot:run
   ```
9. The application should now be running on `http://localhost:8085`.
10. You can use Postman or any other API client to test the endpoints.

## API Endpoints
### Create Employee
- **Endpoint**: POST `/api/v1/employee`
- **Description**: Creates a new employee with the provided details.
- **Request Body**:
  ```json
  {
      "name": "Some One",
      "email": "ebasdqr@gmail.com",
      "phoneNo": "84248480254",
      "profileImg": "sample-url.com",
      "reportsTo": "fa95db0e-69e6-4332-8586-f1d6b283ffe7"
  }
  ```
  - `name` (string, required): The name of the employee.
  - `email` (string, required): The email address of the employee.
  - `phoneNo` (string, required): The phone number of the employee.
  - `profileImg` (string, optional): The URL of the employee's profile image.
  - `reportsTo` (string, optional): The ID of the employee to whom the new employee reports to. (Null for top-level employees)

- **Response**:
  - `201 Created`: Employee created successfully.
  - `409 Conflict`: If an employee with the same details already exists.

### Update Employee
- **Endpoint**: PUT `/api/v1/employee/{id}`
- **Description**: Updates an existing employee with the provided ID.
- **Request Body**: Same as the create employee request body.
- **Path Parameters**:
  - `id` (string, required): The ID of the employee to update.
- **Response**:
  - `200 OK`: Employee updated successfully.
  - `404 Not Found`: If no employee with the specified ID exists.

### Delete Employee
- **Endpoint**: DELETE `/api/v1/employee/{id}`
- **Description**: Deletes the employee with the provided ID.
- **Path Parameters**:
  - `id` (string, required): The ID of the employee to delete.
- **Response**:
  - `200 OK`: Employee deleted successfully.
  - `404 Not Found`: If no employee with the specified ID exists.

### Get Employee by ID
- **Endpoint**: GET `/api/v1/employee/{id}`
- **Description**: Retrieves the details of the employee with the provided ID.
- **Path Parameters**:
  - `id` (string, required): The ID of the employee to retrieve.
- **Response**:
  - `200 OK`: Employee details returned successfully.
  - `404 Not Found`: If no employee with the specified ID exists.

### Get All Employees with Pagination and Sorting
- **Endpoint**: GET `/api/v1/employee`
- **Description**: Retrieves a list of all employees with pagination and sorting options.
- **Query Parameters**:
  - `page` (integer, optional, default: 0): Page number (zero-based).
  - `size` (integer, optional, default: 10): Number of records per page.
  - `sortBy` (string, optional, default: "id"): Field to sort by.
- **Response**:
  - `200 OK`: List of employees returned successfully.
  - `404 Not Found`: If no employees are found.

### Get Nth Level Manager
- **Endpoint**: GET `/api/v1/employee/{id}/manager/{level}`
- **Description**: Retrieves the Nth level manager of the employee with the provided ID.
- **Path Parameters**:
  - `id` (string, required): The ID of the employee.
  - `level` (integer, required): The level of the manager to retrieve.
- **Response**:
  - `200 OK`: Manager details returned successfully.
  - `404 Not Found`: If no employee with the specified ID exists or the level is too high.