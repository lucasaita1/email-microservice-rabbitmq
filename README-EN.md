# 📧 Microservices User & Email System

> A modern, scalable microservices architecture for user management with asynchronous email notifications using Spring Boot, RabbitMQ, PostgreSQL, and MongoDB.

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen?style=for-the-badge&logo=springboot)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Message%20Broker-orange?style=for-the-badge&logo=rabbitmq)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue?style=for-the-badge&logo=postgresql)
![MongoDB](https://img.shields.io/badge/MongoDB-7-green?style=for-the-badge&logo=mongodb)
![Docker](https://img.shields.io/badge/Docker-Compose-blue?style=for-the-badge&logo=docker)

---

## 🎯 Overview

This project demonstrates a microservices architecture with two independent services communicating asynchronously through RabbitMQ. When a user is created, a welcome email is automatically sent through a dedicated email service.

### Architecture Highlights

- **Loose Coupling**: Services communicate via message queues
- **Independent Scaling**: Each service can scale independently
- **Polyglot Persistence**: PostgreSQL for relational data, MongoDB for email logs
- **Event-Driven**: Asynchronous message processing with RabbitMQ
- **Containerized**: Docker Compose for easy deployment

---

## 🏗️ Architecture

```
┌─────────────────┐         ┌──────────────┐         ┌─────────────────┐
│                 │         │              │         │                 │
│  User Service   │────────▶│   RabbitMQ   │────────▶│  Email Service  │
│  (PostgreSQL)   │         │ (Email Queue)│         │   (MongoDB)     │
│                 │         │              │         │                 │
└─────────────────┘         └──────────────┘         └─────────────────┘
       │                                                      │
       │                                                      │
       ▼                                                      ▼
  REST API                                            SMTP Server
```

---

## 🚀 Services

### 1. User Service

Manages user operations with full CRUD functionality.

**Tech Stack:**
- Spring Boot 3.5.6
- Spring Data JPA
- PostgreSQL 14
- Spring AMQP (RabbitMQ)
- Bean Validation

**Features:**
- Create, read, update, and delete users
- Automatic email notification on user creation
- RESTful API with proper HTTP status codes
- Data validation with Jakarta Validation

**Endpoints:**
```
POST   /users/create      - Create new user
GET    /users             - Get all users
GET    /users/{id}        - Get user by ID
PUT    /users/{id}        - Update user
DELETE /users/{id}        - Delete user
```

### 2. Email Service

Handles email sending with persistent logging.

**Tech Stack:**
- Spring Boot 3.5.6
- Spring Data MongoDB
- MongoDB 7
- Spring Mail
- Spring AMQP (RabbitMQ)

**Features:**
- Asynchronous email processing via RabbitMQ
- Email status tracking (SENT, ERROR, PENDING, OPENED)
- Persistent email history in MongoDB
- Automatic retry mechanism on failure

---

## 📋 Prerequisites

Before running this application, ensure you have:

- **Java 17** or higher
- **Maven 3.8+**
- **Docker** and **Docker Compose**
- **RabbitMQ** instance (CloudAMQP or local)
- **SMTP Server** credentials (Gmail, SendGrid, etc.)

---

## ⚙️ Configuration

### Environment Variables

Create a `.env` file in both service directories with the following variables:

#### User Service (user-service/.env)
```env
# RabbitMQ Configuration
RABBITMQ_HOST=your-rabbitmq-host.mq.cloudamqp.com
RABBITMQ_PORT=5671
RABBITMQ_USERNAME=your-username
RABBITMQ_PASSWORD=your-password
RABBITMQ_VHOST=your-vhost
```

#### Email Service (email-service/.env)
```env
# RabbitMQ Configuration
RABBITMQ_HOST=your-rabbitmq-host.mq.cloudamqp.com
RABBITMQ_PORT=5671
RABBITMQ_USERNAME=your-username
RABBITMQ_PASSWORD=your-password
RABBITMQ_VHOST=your-vhost

# Email Configuration
EMAIL_USERNAME=your-email@gmail.com
EMAIL_PASSWORD=your-app-password
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
```

### Application Properties

The services use the following default configurations:

**User Service:**
- Port: `8081`
- Database: PostgreSQL on `localhost:5432`

**Email Service:**
- Port: `8080` (default)
- Database: MongoDB on `localhost:27017`

---

## 🔧 Installation & Setup

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd <project-directory>
```

### 2. Start Databases with Docker

#### For User Service:
```bash
cd user-service
docker-compose up -d
```

#### For Email Service:
```bash
cd email-service
docker-compose up -d
```

### 3. Configure Environment Variables

Create `.env` files in both service directories as described in the Configuration section.

### 4. Build the Services

#### User Service:
```bash
cd user-service
./mvnw clean install
```

#### Email Service:
```bash
cd email-service
./mvnw clean install
```

### 5. Run the Services

Start both services in separate terminals:

#### Terminal 1 - User Service:
```bash
cd user-service
./mvnw spring-boot:run
```

#### Terminal 2 - Email Service:
```bash
cd email-service
./mvnw spring-boot:run
```

---

## 📝 Usage Example

### Create a New User

```bash
curl -X POST http://localhost:8081/users/create \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "email": "john.doe@example.com",
    "password": "securePassword123"
  }'
```

**Response:**
```json
{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "username": "johndoe",
  "email": "john.doe@example.com"
}
```

### Get All Users

```bash
curl http://localhost:8081/users
```

### Get User by ID

```bash
curl http://localhost:8081/users/550e8400-e29b-41d4-a716-446655440000
```

### Update User

```bash
curl -X PUT http://localhost:8081/users/550e8400-e29b-41d4-a716-446655440000 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johnupdated",
    "email": "john.updated@example.com",
    "password": "newPassword123"
  }'
```

### Delete User

```bash
curl -X DELETE http://localhost:8081/users/550e8400-e29b-41d4-a716-446655440000
```

---

## 🔄 Message Flow

1. **User Creation**: Client sends POST request to User Service
2. **Persistence**: User is saved to PostgreSQL database
3. **Message Publishing**: User Service publishes message to RabbitMQ queue
4. **Message Consumption**: Email Service consumes message from queue
5. **Email Sending**: Email Service sends welcome email via SMTP
6. **Status Logging**: Email status (SENT/ERROR) is saved to MongoDB

---

## 🗄️ Database Schema

### User Service (PostgreSQL)

**TB_USERS**
| Column   | Type   | Constraints    |
|----------|--------|----------------|
| user_id  | UUID   | PRIMARY KEY    |
| username | VARCHAR| NOT NULL       |
| email    | VARCHAR| UNIQUE, NOT NULL|
| password | VARCHAR| NOT NULL       |

### Email Service (MongoDB)

**Email Collection**
```json
{
  "_id": "ObjectId",
  "id": "String",
  "userId": "String",
  "from": "String",
  "to": "String",
  "subject": "String",
  "body": "String",
  "sentAt": "LocalDateTime",
  "status": "SENT | ERROR | PENDING | OPENED"
}
```

---

## 🛡️ Error Handling

Both services implement comprehensive error handling:

- **User Service**: Returns appropriate HTTP status codes (201, 200, 404, 204)
- **Email Service**: Catches exceptions during email sending and logs ERROR status
- **RabbitMQ**: Durable queues ensure message persistence

---

## 🧪 Testing

Run tests for each service:

```bash
# User Service
cd user-service
./mvnw test

# Email Service
cd email-service
./mvnw test
```

---

## 📦 Project Structure

```
.
├── user-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/dev/lucas/user/
│   │   │   │   ├── config/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── entity/
│   │   │   │   ├── mapper/
│   │   │   │   ├── producer/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   └── resources/
│   │   └── test/
│   ├── docker-compose.yml
│   ├── pom.xml
│   └── .env
│
└── email-service/
    ├── src/
    │   ├── main/
    │   │   ├── java/dev/lucas/email/
    │   │   │   ├── config/
    │   │   │   ├── consumer/
    │   │   │   ├── dto/
    │   │   │   ├── entity/
    │   │   │   ├── enums/
    │   │   │   ├── repository/
    │   │   │   └── service/
    │   │   └── resources/
    │   └── test/
    ├── docker-compose.yml
    ├── pom.xml
    └── .env
```

---

## 🚀 Deployment

### Docker Deployment

Both services can be containerized and deployed using Docker:

```bash
# Build Docker image for User Service
cd user-service
docker build -t user-service:latest .

# Build Docker image for Email Service
cd email-service
docker build -t email-service:latest .
```

### Cloud Deployment

This architecture is cloud-ready and can be deployed to:
- **AWS**: ECS, EKS, or Elastic Beanstalk
- **Azure**: Container Instances or AKS
- **GCP**: Cloud Run or GKE
- **Heroku**: With CloudAMQP add-on

---

## 🎯 Future Enhancements

- [ ] Add Spring Security with JWT authentication
- [ ] Implement password hashing with BCrypt
- [ ] Add email templates with Thymeleaf
- [ ] Implement email tracking (opened, clicked)
- [ ] Add API documentation with Swagger/OpenAPI


---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## 👤 Author

**Lucas Aita**

- GitHub: [@lucasaita1](https://github.com/lucasaita1)
- LinkedIn: [Lucas Aita](https://www.linkedin.com/in/lucas-aita/)

---

## 🙏 Acknowledgments

- Spring Boot Documentation
- RabbitMQ Tutorials
- MongoDB Documentation
- PostgreSQL Documentation

---

<div align="center">

**⭐ If you found this project helpful, please consider giving it a star!**

Made with ❤️ and ☕

</div>
