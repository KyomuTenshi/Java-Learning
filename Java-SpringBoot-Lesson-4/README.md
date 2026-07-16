# 🚀 Spring Boot & Dependency Injection: Building REST API (Lesson 4)

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Apache_Tomcat-F8DC75?style=for-the-badge&logo=apache-tomcat&logoColor=black" alt="Tomcat" />
  <img src="https://img.shields.io/badge/REST_API-Protocols-0052CC?style=for-the-badge" alt="REST API" />
</p>

---

# [RU] Знакомство со Spring Boot и Dependency Injection (Занятие 4)

## 📌 Краткое описание
Этот репозиторий посвящен изучению экосистемы **Spring Boot** и фундаментальных механизмов управления зависимостями (**IoC/DI**). Данная тема является ключевым водоразделом в Java Backend разработке: она знаменует переход от ручного создания объектов к автоматическому управлению жизненным циклом компонентов силами фреймворка.

В рамках проекта реализовано масштабируемое REST API для калькулятора с поддержкой безопасного конкурентного ведения истории операций. Данное решение наглядно демонстрирует принципы слабой связанности (Loose Coupling), декомпозиции бизнес-логики и разработки веб-интерфейсов для корпоративного ПО.

---

## 🎯 Цели изучения (Learning Goals)
* **Понимание IoC и DI:** Освоить инверсию управления и внедрение зависимостей как фундаментальные концепции разработки гибких систем.
* **Проектирование REST API:** Научиться проектировать веб-интерфейсы, распределять зоны ответственности между контроллерами и сервисной логикой.
* **Многопоточная безопасность:** Понять поведение синглтон-бинов в конкурентной веб-среде и научиться применять потокобезопасные коллекции для сохранения целостности данных.

---

## 📚 Теоретическая часть (Theory)

### Ключевые концепции и их роль в Enterprise-разработке:

1. **Inversion of Control (Инверсия управления) & IoC-контейнер**
   * *Что это:* Архитектурный принцип, при котором управление жизненным циклом объектов передается фреймворку.
   * *Зачем:* Избавляет разработчика от написания инфраструктурного кода (ручной сборки зависимостей через `new`).
   * *Роль в Backend:* Позволяет декларативно регистрировать компоненты с помощью стереотипных аннотаций (`@Component`, `@Service`, `@RestController`) и гибко конфигурировать систему.

2. **Dependency Injection (Внедрение зависимостей)**
   * *Что это:* Паттерн проектирования, реализующий принцип IoC, при котором объект получает свои зависимости извне, а не создает их самостоятельно.
   * *Зачем:* Обеспечивает слабую связанность модулей, облегчает тестирование (через подмену mock-объектами) и расширение системы.
   * *Роль в Backend:* Spring Boot автоматически связывает нужные компоненты графа приложения на этапе его запуска, минимизируя зацепление модулей (coupling).

3. **Синглтон-бины в многопоточной веб-среде**
   * *Что это:* Поведение бина по умолчанию (Scope = Singleton), при котором в контейнере существует только один экземпляр класса на всё приложение.
   * *Зачем:* Экономит память JVM и накладные расходы на постоянную инициализацию объектов.
   * *Роль в Backend:* Так как веб-сервер Tomcat обрабатывает входящие HTTP-запросы в пуле параллельных потоков, Singleton-компоненты должны быть либо иммутабельными (без состояния), либо использовать потокобезопасные структуры данных.

---

## 🛠 Практическая реализация (Practice)

В процессе практики была спроектирована трехслойная архитектура REST-приложения со следующими компонентами:

* **`CalculatorService`**: Слой чистой математической бизнес-логики (сложение, вычитание, умножение, деление).
* **`HistoryService`**: Слой хранения истории вычислений в оперативной памяти с использованием потокобезопасной коллекции `CopyOnWriteArrayList<String>` для предотвращения Race Conditions.
* **`CalculatorController`**: Слой обработки внешних HTTP-запросов (`@RestController`), валидации входных параметров (включая безопасную обработку деления на ноль) и возврата результатов клиенту.

### В процессе практики были реализованы:
* Архитектура **Constructor Injection** — наиболее надежный способ внедрения зависимостей, гарантирующий иммутабельность полей через ключевое слово `final`.
* Механизм обработки ошибок на уровне REST-контроллера для предупреждения аварийного завершения потоков выполнения (`ArithmeticException`).
* Сериализация результатов и истории операций в JSON-формат «из коробки» за счет автоконфигурации Spring Web.

---

## 💻 Использованные технологии (Tech Stack)

| Technology                    | Purpose                                                                          |
|-------------------------------|----------------------------------------------------------------------------------|
| **Java 17 (or higher)**       | Основной язык разработки, применение современных языковых конструкций            |
| **Spring Boot (Web-starter)** | Развертывание IoC-контейнера, автоконфигурация сервера Tomcat, управление бинами |
| **CopyOnWriteArrayList**      | Многопоточная потокобезопасная коллекция для ведения истории без блокировок      |

---

## 💻 Примеры использования API

После запуска приложения (через `./mvnw spring-boot:run`) доступны следующие REST-эндпоинты:

* **Сложение чисел:**
  * Запрос: `GET http://localhost:8080/calc/add?a=10&b=5`
  * Ответ: `15`
* **Деление чисел (успешный сценарий):**
  * Запрос: `GET http://localhost:8080/calc/div?a=20&b=4`
  * Ответ: `5`
* **Обработка ошибки деления на ноль:**
  * Запрос: `GET http://localhost:8080/calc/div?a=10&b=0`
  * Ответ: `Ошибка: деление на ноль`
* **Просмотр истории вычислений:**
  * Запрос: `GET http://localhost:8080/calc/history`
  * Ответ: 
    ```json
    [
      "10 + 5 = 15",
      "20 / 4 = 5"
    ]
    ```

---

## 🧠 Полученные знания и навыки (Learning Outcomes)
* **Получил практический опыт** проектирования слоистой архитектуры (Controller -> Service) на базе Spring Boot.
* **Изучил принципы** работы IoC-контейнера и внедрения зависимостей через конструктор (Constructor Injection) как индустриального стандарта.
* **Научился применять** потокобезопасные структуры данных для обеспечения целостности стейта в многопоточной веб-среде.
* **Улучшил понимание** архитектуры backend-приложений, осознав, как Spring Boot абстрагирует работу с HTTP-протоколом и сервером приложений.
* **Освоил лучшие практики** валидации запросов на границе приложения (слой контроллеров) для предотвращения системных ошибок бизнес-логики.

---
---

# [EN] Introduction to Spring Boot & Dependency Injection (Lesson 4)

## 📌 Brief Description
This repository focuses on exploring the core capabilities of **Spring Boot** and its foundational dependency management mechanisms (**IoC/DI**). This topic represents a crucial milestone in a Java Backend developer's journey, shifting away from manual object construction to automated lifecycle management via the application context.

The project demonstrates a REST API calculator equipped with thread-safe audit logging of arithmetic operations. This architecture highlights key software design patterns: Loose Coupling, modular service separation, and web resource handling in enterprise software.

---

## 🎯 Learning Goals
* **Inversion of Control & DI Mastery:** Comprehend the architecture of IoC containers and how declarative dependency injection simplifies software maintenance.
* **REST API Design Principles:** Learn to structure HTTP endpoints and delegate responsibilities across Web, Service, and Storage layers.
* **Concurrency Awareness:** Evaluate the behavior of singleton-scoped beans under parallel traffic and implement thread-safe constructs to safeguard state.

---

## 📚 Theoretical Insights

### Core Concepts & Enterprise Application:

1. **Inversion of Control (IoC) & IoC Container**
   * *Concept:* A software engineering pattern where control over object creation and lifecycle is delegated to the framework.
   * *Why it matters:* It frees developers from writing repetitive factory and bootstrapping code.
   * *Backend Role:* Spring scans stereotype annotations (`@Component`, `@Service`, `@RestController`) to register them as managed beans and map runtime instances dynamically.

2. **Dependency Injection (DI)**
   * *Concept:* A specific implementation of IoC where an object receives its dependencies from external providers instead of creating them internally.
   * *Why it matters:* Increases modularity, decoupling components for cleaner Unit testing using mocks.
   * *Backend Role:* Spring resolves, builds, and injects the dependency graph at startup, reducing inter-class coupling to a minimum.

3. **Singleton Scope in Multi-Threaded Web Applications**
   * *Concept:* Spring's default scope where only one instance of a component is created for the entire application context.
   * *Why it matters:* Conserves heap memory and minimizes JVM overhead on garbage collection.
   * *Backend Role:* Since Spring's embedded Tomcat engine handles requests concurrently, any stateful singleton components must protect their state with thread-safe data structures.

---

## 🛠 Practical Implementation

This module implements a classic three-tier architecture structured as follows:

* **`CalculatorService`**: Contains pure, database-independent business logic (basic arithmetic operations).
* **`HistoryService`**: An in-memory data store using a lock-free thread-safe `CopyOnWriteArrayList<String>` to record computation history.
* **`CalculatorController`**: Represents the HTTP API boundary. It validates user query parameters, handles error states, and triggers service behaviors.

### Practical Milestones Completed:
* Adopted **Constructor-based Injection** to strictly guarantee dependency immutability via `final` fields.
* Designed proactive input validation at the Controller level to gracefully handle and intercept division-by-zero operations (`ArithmeticException`).
* Handled effortless object-to-JSON serialization out-of-the-box leveraging Spring Web's built-in Jackson serializers.

---

## 💻 Tech Stack

| Technology                    | Purpose                                                                             |
|-------------------------------|-------------------------------------------------------------------------------------|
| **Java 17+**                  | Core programming language with modern syntax                                        |
| **Spring Boot (Web Starter)** | IoC Container management, embedded Tomcat, REST endpoint routing                    |
| **CopyOnWriteArrayList**      | Thread-safe collection preventing Race Conditions under parallel request executions |

---

## 💻 API Usage Examples

Once the application is up and running (via `./mvnw spring-boot:run`), the following HTTP endpoints are exposed:

* **Addition:**
  * Request: `GET http://localhost:8080/calc/add?a=10&b=5`
  * Response: `15`
* **Division (Successful scenario):**
  * Request: `GET http://localhost:8080/calc/div?a=20&b=4`
  * Response: `5`
* **Division by Zero handling:**
  * Request: `GET http://localhost:8080/calc/div?a=10&b=0`
  * Response: `Ошибка: деление на ноль`
* **Retrieving Calculation History:**
  * Request: `GET http://localhost:8080/calc/history`
  * Response: 
    ```json
    [
      "10 + 5 = 15",
      "20 / 4 = 5"
    ]
    ```

---

## 🧠 Learning Outcomes
* **Gained hands-on experience** structuring and executing clean multi-layered architectures within the Spring Boot ecosystem.
* **Mastered the mechanics** of constructor-based dependency injection, achieving robust object immutability and test isolation.
* **Successfully integrated** thread-safe collections to prevent write conflicts in parallel HTTP environments.
* **Bridged conceptual gaps** in web architecture by discovering how Spring Boot simplifies networking, request parsing, and server configurations.
* **Applied defensive validation patterns** to protect internal system logic from boundary errors.