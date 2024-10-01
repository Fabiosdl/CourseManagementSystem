# Course Management System

A full-stack Course Management System that allows the admin to manage students, tutors, and course modules. Students can check their enrolled courses, and tutors can manage the courses they are assigned. The system integrates MySQL for data storage and uses Docker for containerization, simplifying deployment and scalability.

## Key technologies:

- **Spring Boot** for the backend API
- **JPA/Hibernate** for database interaction
- **MySQL** for persistent storage
- **Spring MVC and Thymeleaf** for the frontend
- **Spring Security** for login/registration
- **Docker** for containerization
- **Git** for version control

## Features:
   #### Admin Role:
   - Register new students and tutors
   - Manage students:
   - View a list of students
   - Enroll/withdraw students from courses
   - Delete students
   **Manage tutors:**
   - View a list of tutors
   - Assign/withdraw tutors from courses
   - Delete tutors
   **Create and manage courses:**
   - View a list of available courses
   - Create new courses
   - Delete courses

   #### Tutor Role:
   - Manage assigned courses
   - Access student performance data
   - Upload and manage course materials (planned feature)

   #### Student Role:
   - View enrolled courses
   - Check grades for each course
   - Access learning materials and communicate with tutors (planned feature)
   - Prerequisites
   - Docker: Ensure Docker is installed on your computer. If not, download and install it from here. 

## How to Run the App

1. **Install Docker**:
   - Ensure Docker is installed on your computer. If not, download and install Docker from [here](https://www.docker.com/get-started).

2. **Start Docker**:
   - Open Docker Desktop and ensure it is running.

3. **Navigate to the Project Directory**:
   - Open a terminal and navigate to the directory where the project is located.

4. **Verify Docker Files**:
   - Confirm that both `Dockerfile` and `docker-compose.yml` are present in the directory.

5. **Build the Docker Image**:
   ```bash
   docker build -t coursemanagementsys .
   ```
 
6. **Start the application**:
   - Run the following command to start the containers:
   ```bash
   docker-compose up
   ```
   - This will start 3 containers:
      course_app_service-1 on port 8080
      phpmyadmin on port 8090
      mysql on port 3306
    
## Accessing the Database

1. **Open phpMyAdmin**:
   - Open your browser and go to: `localhost:8090` to access **phpMyAdmin**.

2. **Login Credentials**:
   - On the login page, enter the following credentials (as specified in the `docker-compose.yml` file):
     - **Username**: `fabiolima`
     - **Password**: `fabiolima123`

3. **View the Database Schema**:
   - Once logged in, you will see the schema `course_management_v2`, which stores the application's data.

## Access the Course Management System:

1. **Open Course Management System App**:
    - Open your browser and go to: `localhost:8080` to access **Login Page**.

2. **Login Credentials**:
    - On the login page, enter the following credentials:
      - **Username**: `Admin`
      - **Password**: `admin123`

3. **Admin Dashboard**:
    - On this page you can:
      - Register a new Student or Tutor.
      - Manage Students:
        - View a list of students on the system.
        - Delete the student.
        - Enroll/Withdraw students to courses.
      - Manage Tutors:
        - View a list of tutors on the system.
        - Delete tutors.
        - Enroll/Withdraw tutors to courses.
      - Create New Courses:
        - View a list of available courses.
        - Create/Delete courses.

4. **Student / Tutor page** (Planned future)
    - Once the admin creates a student or tutor, they will be able to log in and access their personal pages to:
        - Students: Check their grades, access course materials, and communicate with tutors.
        - Tutors: Manage assigned courses, upload materials, and review student performance.
