# 📧 Sistema de Microserviços - Usuários e E-mail

> Uma arquitetura moderna e escalável de microserviços para gerenciamento de usuários com notificações por e-mail assíncronas usando Spring Boot, RabbitMQ, PostgreSQL e MongoDB.

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen?style=for-the-badge&logo=springboot)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Message%20Broker-orange?style=for-the-badge&logo=rabbitmq)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue?style=for-the-badge&logo=postgresql)
![MongoDB](https://img.shields.io/badge/MongoDB-7-green?style=for-the-badge&logo=mongodb)
![Docker](https://img.shields.io/badge/Docker-Compose-blue?style=for-the-badge&logo=docker)

---

## 🎯 Visão Geral

Este projeto demonstra uma arquitetura de microserviços com dois serviços independentes que se comunicam de forma assíncrona através do RabbitMQ. Quando um usuário é criado, um e-mail de boas-vindas é automaticamente enviado através de um serviço dedicado de e-mail.

### Destaques da Arquitetura

- **Baixo Acoplamento**: Serviços se comunicam via filas de mensagens
- **Escalabilidade Independente**: Cada serviço pode escalar de forma independente
- **Persistência Poliglota**: PostgreSQL para dados relacionais, MongoDB para logs de e-mail
- **Orientado a Eventos**: Processamento assíncrono de mensagens com RabbitMQ
- **Containerizado**: Docker Compose para fácil implantação

---

## 🏗️ Arquitetura

```
┌─────────────────┐         ┌──────────────┐         ┌─────────────────┐
│                 │         │              │         │                 │
│  User Service   │────────▶│   RabbitMQ   │────────▶│  Email Service  │
│  (PostgreSQL)   │         │ (Fila Email) │         │   (MongoDB)     │
│                 │         │              │         │                 │
└─────────────────┘         └──────────────┘         └─────────────────┘
       │                                                      │
       │                                                      │
       ▼                                                      ▼
  REST API                                            Servidor SMTP
```

---

## 🚀 Serviços

### 1. Serviço de Usuários

Gerencia operações de usuários com funcionalidade CRUD completa.

**Stack Tecnológica:**
- Spring Boot 3.5.6
- Spring Data JPA
- PostgreSQL 14
- Spring AMQP (RabbitMQ)
- Bean Validation

**Funcionalidades:**
- Criar, ler, atualizar e deletar usuários
- Notificação automática por e-mail na criação de usuário
- API RESTful com códigos de status HTTP apropriados
- Validação de dados com Jakarta Validation

**Endpoints:**
```
POST   /users/create      - Criar novo usuário
GET    /users             - Listar todos os usuários
GET    /users/{id}        - Buscar usuário por ID
PUT    /users/{id}        - Atualizar usuário
DELETE /users/{id}        - Deletar usuário
```

### 2. Serviço de E-mail

Gerencia o envio de e-mails com registro persistente.

**Stack Tecnológica:**
- Spring Boot 3.5.6
- Spring Data MongoDB
- MongoDB 7
- Spring Mail
- Spring AMQP (RabbitMQ)

**Funcionalidades:**
- Processamento assíncrono de e-mails via RabbitMQ
- Rastreamento de status de e-mail (SENT, ERROR, PENDING, OPENED)
- Histórico persistente de e-mails no MongoDB
- Mecanismo automático de retry em caso de falha

---

## 📋 Pré-requisitos

Antes de executar esta aplicação, certifique-se de ter:

- **Java 17** ou superior
- **Maven 3.8+**
- **Docker** e **Docker Compose**
- Instância do **RabbitMQ** (CloudAMQP ou local)
- Credenciais de **Servidor SMTP** (Gmail, SendGrid, etc.)

---

## ⚙️ Configuração

### Variáveis de Ambiente

Crie um arquivo `.env` nos diretórios de ambos os serviços com as seguintes variáveis:

#### Serviço de Usuários (user-service/.env)
```env
# Configuração do RabbitMQ
RABBITMQ_HOST=seu-host-rabbitmq.mq.cloudamqp.com
RABBITMQ_PORT=5671
RABBITMQ_USERNAME=seu-usuario
RABBITMQ_PASSWORD=sua-senha
RABBITMQ_VHOST=seu-vhost
```

#### Serviço de E-mail (email-service/.env)
```env
# Configuração do RabbitMQ
RABBITMQ_HOST=seu-host-rabbitmq.mq.cloudamqp.com
RABBITMQ_PORT=5671
RABBITMQ_USERNAME=seu-usuario
RABBITMQ_PASSWORD=sua-senha
RABBITMQ_VHOST=seu-vhost

# Configuração de E-mail
EMAIL_USERNAME=seu-email@gmail.com
EMAIL_PASSWORD=sua-senha-de-app
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
```

### Propriedades da Aplicação

Os serviços utilizam as seguintes configurações padrão:

**Serviço de Usuários:**
- Porta: `8081`
- Banco de Dados: PostgreSQL em `localhost:5432`

**Serviço de E-mail:**
- Porta: `8080` (padrão)
- Banco de Dados: MongoDB em `localhost:27017`

---

## 🔧 Instalação e Configuração

### 1. Clone o Repositório

```bash
git clone <url-do-seu-repositorio>
cd <diretorio-do-projeto>
```

### 2. Inicie os Bancos de Dados com Docker

#### Para o Serviço de Usuários:
```bash
cd user-service
docker-compose up -d
```

#### Para o Serviço de E-mail:
```bash
cd email-service
docker-compose up -d
```

### 3. Configure as Variáveis de Ambiente

Crie arquivos `.env` em ambos os diretórios dos serviços conforme descrito na seção de Configuração.

### 4. Compile os Serviços

#### Serviço de Usuários:
```bash
cd user-service
./mvnw clean install
```

#### Serviço de E-mail:
```bash
cd email-service
./mvnw clean install
```

### 5. Execute os Serviços

Inicie ambos os serviços em terminais separados:

#### Terminal 1 - Serviço de Usuários:
```bash
cd user-service
./mvnw spring-boot:run
```

#### Terminal 2 - Serviço de E-mail:
```bash
cd email-service
./mvnw spring-boot:run
```

---

## 📝 Exemplo de Uso

### Criar um Novo Usuário

```bash
curl -X POST http://localhost:8081/users/create \
  -H "Content-Type: application/json" \
  -d '{
    "username": "joaosilva",
    "email": "joao.silva@exemplo.com",
    "password": "senhaSegura123"
  }'
```

**Resposta:**
```json
{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "username": "joaosilva",
  "email": "joao.silva@exemplo.com"
}
```

### Listar Todos os Usuários

```bash
curl http://localhost:8081/users
```

### Buscar Usuário por ID

```bash
curl http://localhost:8081/users/550e8400-e29b-41d4-a716-446655440000
```

### Atualizar Usuário

```bash
curl -X PUT http://localhost:8081/users/550e8400-e29b-41d4-a716-446655440000 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "joaoatualizado",
    "email": "joao.atualizado@exemplo.com",
    "password": "novaSenha123"
  }'
```

### Deletar Usuário

```bash
curl -X DELETE http://localhost:8081/users/550e8400-e29b-41d4-a716-446655440000
```

---

## 🔄 Fluxo de Mensagens

1. **Criação do Usuário**: Cliente envia requisição POST para o Serviço de Usuários
2. **Persistência**: Usuário é salvo no banco de dados PostgreSQL
3. **Publicação da Mensagem**: Serviço de Usuários publica mensagem na fila do RabbitMQ
4. **Consumo da Mensagem**: Serviço de E-mail consome a mensagem da fila
5. **Envio de E-mail**: Serviço de E-mail envia e-mail de boas-vindas via SMTP
6. **Registro de Status**: Status do e-mail (SENT/ERROR) é salvo no MongoDB

---

## 🗄️ Esquema do Banco de Dados

### Serviço de Usuários (PostgreSQL)

**TB_USERS**
| Coluna   | Tipo   | Restrições     |
|----------|--------|----------------|
| user_id  | UUID   | PRIMARY KEY    |
| username | VARCHAR| NOT NULL       |
| email    | VARCHAR| UNIQUE, NOT NULL|
| password | VARCHAR| NOT NULL       |

### Serviço de E-mail (MongoDB)

**Coleção Email**
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

## 🛡️ Tratamento de Erros

Ambos os serviços implementam tratamento abrangente de erros:

- **Serviço de Usuários**: Retorna códigos de status HTTP apropriados (201, 200, 404, 204)
- **Serviço de E-mail**: Captura exceções durante o envio de e-mail e registra status ERROR
- **RabbitMQ**: Filas duráveis garantem persistência de mensagens

---

## 🧪 Testes

Execute os testes para cada serviço:

```bash
# Serviço de Usuários
cd user-service
./mvnw test

# Serviço de E-mail
cd email-service
./mvnw test
```

---

## 📦 Estrutura do Projeto

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

## 🚀 Implantação

### Implantação com Docker

Ambos os serviços podem ser containerizados e implantados usando Docker:

```bash
# Construir imagem Docker para o Serviço de Usuários
cd user-service
docker build -t user-service:latest .

# Construir imagem Docker para o Serviço de E-mail
cd email-service
docker build -t email-service:latest .
```

### Implantação na Nuvem

Esta arquitetura está pronta para a nuvem e pode ser implantada em:
- **AWS**: ECS, EKS ou Elastic Beanstalk
- **Azure**: Container Instances ou AKS
- **GCP**: Cloud Run ou GKE
- **Heroku**: Com add-on CloudAMQP

---

## 🔐 Considerações de Segurança

- Senhas devem ser criptografadas (considere adicionar Spring Security com BCrypt)
- Use HTTPS em produção
- Proteja as credenciais do RabbitMQ
- Implemente limitação de taxa nos endpoints da API
- Adicione autenticação e autorização
- Use variáveis de ambiente para todos os dados sensíveis

---

## 🎯 Melhorias Futuras

- [ ] Adicionar Spring Security com autenticação JWT
- [ ] Implementar hash de senhas com BCrypt
- [ ] Adicionar templates de e-mail com Thymeleaf
- [ ] Implementar rastreamento de e-mail (aberto, clicado)
- [ ] Adicionar documentação da API com Swagger/OpenAPI
- [ ] Implementar políticas de retry para e-mails falhados
- [ ] Adicionar monitoramento com Spring Actuator e Prometheus
- [ ] Implementar padrão circuit breaker com Resilience4j
- [ ] Adicionar rastreamento distribuído com Zipkin
- [ ] Criar dashboard administrativo para análise de e-mails

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para enviar um Pull Request.

1. Faça um Fork do projeto
2. Crie sua branch de feature (`git checkout -b feature/RecursoIncrivel`)
3. Faça commit das suas mudanças (`git commit -m 'Adiciona RecursoIncrivel'`)
4. Faça push para a branch (`git push origin feature/RecursoIncrivel`)
5. Abra um Pull Request

---

## 📄 Licença

Este projeto é open source e está disponível sob a [Licença MIT](LICENSE).

---

## 👤 Autor

**Lucas Aita**

- GitHub: [@lucasaita1](https://github.com/lucasaita1)
- LinkedIn: [Lucas Aita](https://www.linkedin.com/in/lucas-aita/)

---

## 🙏 Agradecimentos

- Documentação do Spring Boot
- Tutoriais do RabbitMQ
- Documentação do MongoDB
- Documentação do PostgreSQL

---

<div align="center">

**⭐ Se você achou este projeto útil, considere dar uma estrela!**

Feito com ❤️ e ☕

</div>