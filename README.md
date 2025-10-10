# 🧩 Microservices Application — user-project

### 🟢 Overview  
Этот проект демонстрирует **микросервисную архитектуру**, построенную с использованием **Spring Boot**, **Spring Cloud** и **Apache Kafka**.  
Система состоит из независимых сервисов, которые взаимодействуют асинхронно через Kafka и управляются с помощью **Eureka**, **API Gateway** и **Config Server**.
---

## ⚙️ Architecture
                     ┌───────────────┐
                     │ config-server │
                     └──────┬────────┘
                            │
            ┌───────────────┴────────────────┐
            │                                │
  ┌─────────▼────────┐            ┌──────────▼──────────┐
  │   eureka-server  │            │     api-gateway     │
  └─────────┬────────┘            └──────────┬──────────┘
            │                                │
 ┌──────────▼──────────┐          ┌──────────▼──────────┐
 │     user-service    │          │ notification-service│
 └──────────┬──────────┘          └──────────┬──────────┘
            │                                │
            └──────────► Kafka ◄─────────────┘

---

## 🧱 Services

### 🧍‍♂️ `user-service`
- Предоставляет CRUD-операции для пользователей  
- Публикует события (`CREATE`, `DELETE`) в Kafka  
- Использует PostgreSQL для хранения данных  

### ✉️ `notification-service`
- Подписан на Kafka-топики с пользовательскими событиями  
- Отправляет email-уведомления о создании или удалении пользователя  

### 🌐 `api-gateway`
- Направляет внешние запросы во внутренние микросервисы  
- Обрабатывает маршрутизацию и аутентификацию  

### 🧭 `eureka-server`
- Реализует регистрацию и обнаружение всех микросервисов 

### ⚙️ `config-server`
- Управляет централизованной конфигурацией всех сервисов  

---

## 🧰 Tech Stack

| Category | Technologies |
|-----------|---------------|
| **Language** | Java 17 |
| **Framework** | Spring Boot, Spring Cloud |
| **Messaging** | Apache Kafka |
| **Database** | PostgreSQL |
| **Service Discovery** | Eureka |
| **Gateway** | Spring Cloud Gateway |
| **Config Management** | Spring Cloud Config |
| **Build Tool** | Maven |
| **Containerization** | Docker, Docker Compose |

---
