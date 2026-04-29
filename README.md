📁 Folder Structure
backend/
├── pom.xml
└── src/main/
    ├── java/com/loms/
    │   ├── controller/
    │   ├── service/
    │   ├── repository/
    │   ├── entity/
    │   ├── security/
    │   └── config/
    └── resources/
        └── application.properties

  ⚙️ application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/loms_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080

▶️ Run Backend
cd backend
mvn spring-boot:run
