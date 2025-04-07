# 🧠 Bet Management API

A complete and scalable RESTful API for managing sports bets. Built with Java and Spring Boot, this API supports JWT-based authentication, real-time WebSocket communication, and generation of detailed reports in both PDF and CSV formats.

---

## 🚀 Features

- ✅ User authentication with JWT
- 📄 Export reports in PDF and CSV
- 🧾 Clean and organized DTO responses
- 🧠 WebSocket endpoint for real-time chat
- 📚 Swagger documentation for easy testing
- 📊 Filtering, pagination, and search
- 🛡️ Rate limiting with Resilience4j
- 🧠 Auditing with Hibernate Envers
- 🧪 Automated tests with validation logic

---

## 🛠️ Tech Stack

- Java 20
- Spring Boot 3.3.3
- PostgreSQL
- WebSocket
- Swagger (OpenAPI)
- Hibernate / Spring Data JPA
- Resilience4j
- Lombok

---

## 📂 Folder Structure

src ├── config ├── controller ├── dto ├── entity ├── enums ├── repository ├── service └── websocket

yaml
Copiar
Editar

---

## 📦 Installation

# Clone the repository
git clone 
https://github.com/fjuncal/bet-management-api.git
cd bet-management-api

# Run the application
./mvnw spring-boot:run
Make sure to configure your PostgreSQL database settings in application.yml.

🔐 Authentication
This API uses JWT. After login, use the token in the Authorization header as:
Authorization: Bearer <your-token>

📘 API Documentation
Once running, access the Swagger UI at:
http://localhost:8080/swagger-ui/index.html

📄 Report Generation
PDF and CSV export endpoints allow for dynamic report generation.

HTTP headers are configured to automatically prompt downloads with proper file extensions.

🧠 Real-Time Chat (WebSocket)
The API includes a WebSocket endpoint where authenticated users can send and receive real-time messages.

📈 Monitoring
Prometheus and Grafana are configured for metrics and visualization.

👨‍💻 Author
Fellipe Juncal — LinkedIn | GitHub

📄 License
This project is licensed under the MIT License.