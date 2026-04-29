# LOMS Backend v4 — Spring Boot

## Tech Stack
- Java 17, Spring Boot 3.2.2
- Spring Security + JWT
- Spring Data JPA + MySQL
- Lombok, ModelMapper

## Quick Start
1. Create DB: `mysql -u root -p < ../loms-database/schema.sql`
2. Seed data:  `mysql -u root -p < ../loms-database/seed.sql`
3. Edit `src/main/resources/application.properties` — set your DB password
4. Run: `mvn spring-boot:run`
5. API at: `http://localhost:8080`

## New Features (v4)
- **Signup endpoint** — `/api/auth/signup` (STUDENT/TEACHER only)
- **Approval system** — `is_approved` field; pending users cannot login
- **Login logging** — every login saved to `login_logs` table
- **Pending users API** — `/api/admin/users/pending`
- **Approve user API** — `PATCH /api/admin/users/{id}/approve`
- **Login logs API** — `GET /api/admin/login-logs`

## API Summary
```
POST /api/auth/signup          Register (STUDENT or TEACHER)
POST /api/auth/login           Login → JWT token

GET  /api/admin/dashboard
GET  /api/admin/users/pending  Pending approvals
PATCH /api/admin/users/{id}/approve
GET  /api/admin/login-logs     All visitor logins

GET/POST   /api/admin/departments
GET/POST   /api/admin/subjects
GET/POST   /api/admin/learning-outcomes
GET/POST   /api/admin/users

GET/POST   /api/teacher/tests
POST       /api/teacher/marks
GET        /api/teacher/marks/class-summary/{subjectId}
GET        /api/teacher/dashboard/{teacherId}

GET        /api/student/marks/results/{studentId}
GET        /api/student/dashboard/{studentId}
```
