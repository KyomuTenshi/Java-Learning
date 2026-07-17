# Подключение PostgreSQL к Spring Boot проекту (Занятие 5)

---

# [RU] Подключение PostgreSQL к Spring Boot проекту (Занятие 5)

## 📌 Краткое описание

Этот репозиторий посвящен изучению интеграции полноценной реляционной СУБД **PostgreSQL** в веб-приложение на Spring Boot. Данная тема является ключевым инфраструктурным фундаментом Java Backend разработки. В реальных enterprise-проектах сохраняемость данных (persistence) — обязательное требование, и этот этап знаменует полный переход от хранения данных в оперативной памяти (ин-мемори) к долгосрочному, надежному и персистентному хранению.

В рамках проекта выстроена цепочка взаимодействия от REST-эндпоинтов до физической СУБД, настроен пул соединений и продемонстрирована работа с «сырыми» SQL-запросами на низком уровне абстракции данных.

---

## 🎯 Цели изучения (Learning Goals)

* **Понимание архитектуры доступа к данным:** Изучить все слои прохождения запроса: от Java-кода через DataSource и пул соединений до JDBC-драйвера и СУБД.
* **Конфигурирование Spring Boot приложений:** Освоить работу со сложными иерархическими структурами настроек в формате YAML.
* **Низкоуровневое взаимодействие с SQL:** Научиться выполнять скалярные и табличные SQL-запросы без использования ORM-систем, используя возможности `JdbcTemplate`.
* **Тестирование интеграции:** Разработать механизмы проверки жизнеспособности (Health Check) инфраструктурных связей приложения.

---

## 📚 Теоретическая часть (Theory)

### Ключевые концепции и их роль в Java Backend Development:

1. **Драйвер JDBC (Java Database Connectivity)**
* *Что это:* Низкоуровневая библиотека, реализующая стандартные интерфейсы Java для взаимодействия с базами данных.
* *Зачем используется:* Переводит стандартные вызовы Java API (SQL-команды) в бинарный сетевой протокол конкретной СУБД (в данном случае — `postgresql-driver`).
* *Роль в Backend:* Без драйвера невозможно установить физическое сетевое соединение между JVM и СУБД.


2. **Пул соединений (Connection Pool / HikariCP)**
* *Что это:* Высокопроизводительный менеджер, который кэширует и повторно использует пул открытых TCP-соединений с базой данных.
* *Зачем используется:* Открытие нового соединения на каждый HTTP-запрос — это ресурсоемкая операция, замедляющая backend. Пул выдает готовые соединения мгновенно.
* *Роль в Backend:* Обеспечивает масштабируемость и высокую скорость ответа веб-приложения при высокой конкурентной нагрузке.


3. **DataSource**
* *Что это:* Объект-фабрика в Java, выступающий центральной точкой конфигурации подключения.
* *Зачем используется:* Абстрагирует код приложения от конкретных драйверов, логинов, паролей и сетевых адресов СУБД.
* *Роль в Backend:* Централизованно управляет пулом соединений, предоставляя Spring Boot унифицированный интерфейс для выполнения транзакций и запросов.



---

## 🛠 Практическая реализация (Practice)

В процессе практики был осуществлен полный цикл интеграции базы данных: от локального развертывания СУБД до реализации сквозных диагностических REST-эндпоинтов.

### В процессе практики были реализованы:

* **Локальное развертывание СУБД:** Установлен сервер PostgreSQL, с помощью утилиты `psql` вручную создан выделенный пользователь `taskapi` и изолированная база данных `taskdb`.
* **Иерархическое конфигурирование в `application.yml`:** Настроена строка JDBC URL, аутентификационные данные, а также безопасный режим `spring.jpa.hibernate.ddl-auto: none`, предотвращающий автоматическую модификацию таблиц со стороны Hibernate до создания JPA-сущностей. Включено многострочное логирование SQL (`format_sql`).
* **Разработка Health-контроллера:** Создан класс `HealthController` с внедрением `JdbcTemplate` через конструктор. Он выполняет прямые скалярные запросы к метаданным системы (`queryForObject`) для верификации статуса подключения.

---

## 💻 Использованные технологии (Tech Stack)

| Technology                         | Purpose                                                                                |
|------------------------------------|----------------------------------------------------------------------------------------|
| **Java 17 (or higher)**            | Основной язык разработки, применение DI через конструкторы                             |
| **Spring Boot (Data JPA starter)** | Автоконфигурация DataSource, транзитивное подключение HikariCP и инфраструктуры данных |
| **PostgreSQL 16.x**                | Реляционная система управления базами данных (СУБД) для персистентного хранения        |
| **JdbcTemplate**                   | Легковесный инструмент Spring для выполнения прямых SQL-запросов и проверки связи      |

---

## 💻 Примеры использования API (Тестирование подключения)

После запуска приложения (через `./mvnw spring-boot:run`) доступны следующие эндпоинты для проверки интеграции:

* **1. Проверка общей доступности БД и версии:**
* Запрос: `GET http://localhost:8080/health/db`
* Успешный ответ (200 OK): `DB connection OK: PostgreSQL 16.x on x86_64...`
* Ответ при ошибке (500 Internal Server Error): возвращает структурированный JSON со статусом `500`, если процесс СУБД остановлен или сетевой доступ заблокирован.


* **2. Получение имени текущей активной базы данных:**
* Запрос: `GET http://localhost:8080/health/db/name`
* Ответ: `taskdb`


* **3. Проверка чтения данных из реальной таблицы:**
* Запрос: `GET http://localhost:8080/health/ping`
* Ответ: `hello from psql` *(значение, предварительно добавленное вручную через INSERT-скрипт)*



---

## 🧠 Полученные знания и навыки (Learning Outcomes)

* **Получил практический опыт** связывания независимого внешнего процесса СУБД PostgreSQL с экосистемой Spring Boot.
* **Изучил принципы** работы низкоуровневых драйверов и пулов соединений (`HikariCP`), управляющих эффективным распределением ресурсов.
* **Научился применять** декларативные настройки в формате YAML для гибкого разделения параметров среды.
* **Улучшил понимание** архитектуры backend-приложений, осознав важность изоляции слоев и безопасности структуры данных (отказ от деструктивных стратегий `ddl-auto`).
* **Освоил лучшие практики** ведения инфраструктурного логирования (форматированный вывод SQL в консоль для отладки).

---

---

# [EN] PostgreSQL Integration with Spring Boot (Lesson 5)

## 📌 Brief Description

This repository focuses on integrating **PostgreSQL**, a robust relational database management system, into a Spring Boot web application. This phase represents a major infrastructure milestone in professional Java Backend development, moving away from temporary in-memory storage towards persistent, reliable, and enterprise-grade data management.

The project demonstrates the complete communication cycle from REST endpoints down to the physical database. It configures high-performance connection pooling and showcases direct interactions with the database via raw SQL statements on a lower data access abstraction level.

---

## 🎯 Learning Goals

* **Understand Data Access Architecture:** Trace database requests through the full pipeline: Java code ➔ DataSource ➔ Connection Pool ➔ JDBC Driver ➔ PostgreSQL Server.
* **Master Application Configuration:** Transition to hierarchical YAML formatting to handle nested properties cleanly.
* **Low-Level SQL Proficiency:** Execute scalar and table queries natively using Spring's lightweight `JdbcTemplate` without abstract ORM layers.
* **Infrastructure Health Checking:** Build programmatic diagnostic tools to ensure external system connectivity at runtime.

---

## 📚 Theoretical Insights

### Core Concepts & Enterprise Application:

1. **JDBC Driver (Java Database Connectivity)**
* *Concept:* A low-level driver library implementing common Java API database abstractions.
* *Why it matters:* It acts as a translator, mapping abstract Java SQL commands into the binary network protocol used by PostgreSQL (`postgresql-driver`).
* *Backend Role:* Crucial for opening physical network sockets between the JVM application and the database instance.


2. **Connection Pool (HikariCP)**
* *Concept:* A high-speed cache managing a collection of open database TCP connections.
* *Why it matters:* Instantiating a new network handshake for every individual HTTP request causes massive performance overhead. A pool recycles connections instantly.
* *Backend Role:* Standard for modern backend applications to sustain large concurrent user bases without connection bottlenecks.


3. **DataSource**
* *Concept:* A centralized Java factory object defining the access parameters of a physical data store.
* *Why it matters:* Decouples business code from environment configurations like connection URLs, hostnames, and credentials.
* *Backend Role:* Serves as the primary provider of connection pooling for the Spring Framework, making resource routing fully transparent.



---

## 🛠 Practical Implementation

The practical application encompasses the entire setup loop, from bare-metal database initialization to end-to-end integration mapping.

### Key Implementation Milestones:

* **Database Provisioning:** Deployed a local PostgreSQL server, utilizing the `psql` CLI tool to provision a dedicated `taskapi` user and an isolated `taskdb` schema.
* **Hierarchical YAML Overhaul:** Refactored configuration into `application.yml`, mapping the JDBC connection string, credentials, and explicitly declaring `spring.jpa.hibernate.ddl-auto: none` to safeguard tables before Entity models are introduced. Enabled pretty-printed SQL logging (`format_sql`).
* **Diagnostic Health Rest Controller:** Designed a `HealthController` that utilizes Constructor-based Dependency Injection for `JdbcTemplate`. It triggers raw metadata SQL queries (`queryForObject`) to monitor connectivity dynamically.

---

## 💻 Tech Stack

| Technology                 | Purpose                                                                            |
|----------------------------|------------------------------------------------------------------------------------|
| **Java 17+**               | Core programming language, showcasing constructor-based injection patterns.        |
| **Spring Boot (Data JPA)** | Managed auto-configuration of the DataSource, implicitly introducing HikariCP      |
| **PostgreSQL 16.x**        | Persistent production-ready relational storage backend                             |
| **JdbcTemplate**           | Lightweight abstraction used for executing raw check queries and data verification |

---

## 💻 API Usage Examples (Connectivity Testing)

After launching the application (using `./mvnw spring-boot:run`), the following diagnostic REST endpoints become accessible:

* **1. Verify Database Availability & Version Info:**
* Request: `GET http://localhost:8080/health/db`
* Success Response (200 OK): `DB connection OK: PostgreSQL 16.x on x86_64...`
* Error Response (500 Internal Server Error): Returns a structural JSON block if the PostgreSQL daemon is stopped or unreachable.


* **2. Retrieve Active Database Name:**
* Request: `GET http://localhost:8080/health/db/name`
* Response: `taskdb`


* **3. Verify Data Read Operations From Live Table:**
* Request: `GET http://localhost:8080/health/ping`
* Response: `hello from psql` *(value previously populated using an explicit manual SQL INSERT script)*



---

## 🧠 Learning Outcomes

* **Gained practical experience** coupling isolated external server instances (PostgreSQL) with automated Spring Boot configuration cycles.
* **Studied core mechanics** of driver handshakes and database connection pools (`HikariCP`) that drive performance-critical backends.
* **Applied declarative configuration** standards using nested YAML to maintain multi-environment application profiles effortlessly.
* **Enhanced systemic comprehension** of backend architectures, prioritizing data safety over rapid testing by utilizing non-destructive `ddl-auto` parameters.
* **Adopted industry debugging standards** by configuring structured, multi-line SQL logging directly inside the developer console.

```

```