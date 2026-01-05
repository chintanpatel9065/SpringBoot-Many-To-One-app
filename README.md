# Employee Management System (SpringBootManyToOneApp)

A Spring Boot-based Employee Management System that demonstrates a Many-to-One relationship between Employees and Departments. This application provides full CRUD (Create, Read, Update, Delete) functionality for both entities, with a responsive UI built using Thymeleaf and Bootstrap.

## Features

- **Employee Management:**
    - Add, Edit, Update, and Delete employees.
    - View a list of all employees.
    - Search employees by name or department.
    - Form validation for employee details.
- **Department Management:**
    - Add, Edit, Update, and Delete departments.
    - View a list of all departments.
- **Relationship:**
    - Each Employee belongs to one Department (Many-to-One).
    - Selecting a department is mandatory when adding or editing an employee.
- **Responsive Design:**
    - Built with Bootstrap for a mobile-friendly interface.

## Technologies Used

- **Backend:**
    - Java 17
    - Spring Boot 4.x
    - Spring Data JPA
    - Hibernate
- **Frontend:**
    - Thymeleaf
    - Bootstrap 5.x
- **Database:**
    - PostgreSQL (Production/Development)
    - H2 Database (Testing)
- **Other Tools:**
    - Lombok
    - Maven

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java Development Kit (JDK) 17**
- **Apache Maven**
- **PostgreSQL**

## Setup and Installation

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd SpringBootManyToOneApp
    ```

2.  **Database Configuration:**
    - Create a database named `appdb` in PostgreSQL.
    - Update the database credentials in `src/main/resources/application.properties` if they differ from the defaults:
      ```properties
      spring.datasource.username=your_username
      spring.datasource.password=your_password
      ```

3.  **Build the application:**
    ```bash
    ./mvnw clean install
    ```

## Running the Application

1.  **Start the Spring Boot application:**
    ```bash
    ./mvnw spring-boot:run
    ```

2.  **Access the application:**
    Open your web browser and navigate to `http://localhost:8080`.

## Testing

The project includes unit and integration tests for Repositories, Services, and Controllers.

To run the tests, use:
```bash
./mvnw test
```

## Project Structure

- `src/main/java`: Contains the Java source code (Controllers, Services, Repositories, and Entities).
- `src/main/resources/templates`: Contains Thymeleaf HTML templates.
- `src/main/resources/static`: Contains static assets like CSS and JS (Bootstrap).
- `src/test/java`: Contains the test suites.
