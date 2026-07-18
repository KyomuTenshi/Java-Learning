# 🗄️ JPA & Hibernate ORM: Resolving Impedance Mismatch (Lesson 6)

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white" alt="Hibernate" />
  <img src="https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Data JPA" />
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
</p>

---

# [RU] Работа с JPA и Hibernate (Занятие 6)

## 📌 Краткое описание
Этот репозиторий посвящен изучению и интеграции объектно-реляционного отображения (**ORM**) в Java-приложение с использованием спецификации **JPA** и её реализации **Hibernate**. Данный этап является логическим развитием инфраструктуры доступа к данным (после подключения СУБД PostgreSQL на Занятии 5). Проект решает фундаментальную проблему объектно-реляционного рассогласования (*Impedance Mismatch*).

В рамках модуля реализован автоматический маппинг доменных Java-классов на таблицы СУБД PostgreSQL, настроено каскадное управление связанными сущностями и внедрены высокоуровневые декларативные репозитории Spring Data JPA.

---

## 🎯 Цели изучения (Learning Goals)
* **Преодоление Impedance Mismatch:** Освоить концепцию ORM для бесшовного проецирования объектных моделей Java на реляционные структуры СУБД.
* **Управление связями между сущностями:** Научиться проектировать двунаправленные связи (Bidirectional Relationships) и оптимизировать запросы к связанным данным.
* **Каскадное управление (Cascading):** Реализовать автоматическую синхронизацию состояний родительских и дочерних объектов на уровне базы данных.
* **Декларативный доступ к данным:** Использовать Spring Data JPA для исключения шаблонного SQL-кода и генерации запросов на основе сигнатур методов.

---

## 📚 Теоретическая часть (Theory)

### Ключевые концепции и их роль в Java Backend Development:

1. **JPA vs Hibernate vs Spring Data JPA**
   * *JPA (Java Persistence API):* Декларативный стандарт (спецификация/набор аннотаций), описывающий правила управления персистентными объектами.
   * *Hibernate:* Полноценный ORM-движок, который реализует стандарт JPA и берет на себя автоматическую генерацию и оптимизацию SQL-кода под конкретный диалект СУБД.
   * *Spring Data JPA:* Высокоуровневая надстройка, которая автоматически строит готовые прокси-реализации репозиториев на этапе старта приложения, убирая необходимость писать CRUD-код вручную.

2. **Жизненный цикл сущностей (Entity Lifecycle)**
   * *Что это:* Четыре состояния, в которых может находиться объект `@Entity`: *Transient* (новый объект в памяти), *Managed* (отслеживается EntityManager), *Detached* (отвязан от сессии) и *Removed* (помечен на удаление).
   * *Зачем используется:* Для эффективного кэширования, отслеживания изменений в полях объектов (Dirty Checking) и их автоматической синхронизации с СУБД в рамках транзакции.
   * *Роль в Backend:* Позволяет разработчику манипулировать данными как обычными Java-объектами, делегируя вызовы `INSERT`/`UPDATE` контексту постоянства (Persistence Context).

3. **Ленивая загрузка (Lazy Loading)**
   * *Что это:* Стратегия загрузки данных (`FetchType.LAZY`), при которой связанные сущности не извлекаются из базы данных до тех пор, пока к ним не произойдет явное обращение в коде.
   * *Зачем используется:* Предотвращает критическое падение производительности и проблему избыточных запросов (включая проблему N+1).
   * *Роль в Backend:* Оптимизирует потребление памяти JVM и сетевой трафик, подгружая дочерние списки (например, логи задачи) только по реальному требованию бизнес-логики.

---

## 🛠 Практическая реализация (Practice)

В процессе практики была спроектирована и реализована реляционная схема данных «один-ко-многим» (One-to-Many) между задачами и логами их аудита.

### В процессе практики были реализованы:
* **Декларативное описание сущностей (`TaskEntity` и `LogEntity`):** Поля классов размечены аннотациями JPA с жесткими ограничениями размеров (`length = 255`) и запретом пустых значений (`nullable = false`). Настроен автоинкремент первичных ключей (`GenerationType.IDENTITY`) под тип `bigserial` в PostgreSQL.
* **Защищенные No-Args конструкторы:** Реализованы `protected` пустые конструкторы для создания экземпляров объектов через рефлексию силами Hibernate, что изолирует их от вызова в бизнес-коде.
* **Двунаправленная связь с каскадным удалением:** Сторона `LogEntity` определена как владеющая через `@ManyToOne` и `@JoinColumn`. На стороне `TaskEntity` настроено свойство `cascade = CascadeType.ALL` для транзитивного сохранения, а также параметр `orphanRemoval = true` для автоматической очистки «сирот» из базы при удалении элементов из коллекции Java.
* **Синхронизация состояния (Defensive Method):** Внедрен метод `addLog(LogEntity log)`, который гарантирует атомарное проставление ссылок между объектами в оперативной памяти в обе стороны.
* **Вывод запросов из имени (Query Derivation):** В интерфейс `TaskRepository` добавлен метод `findByCompleted(boolean completed)`, автоматически преобразуемый Spring Data в SQL-запрос `SELECT * FROM tasks WHERE completed = ?`.

---

## 💻 Использованные технологии (Tech Stack)

| Technology              | Purpose                                                                      |
|-------------------------|------------------------------------------------------------------------------|
| **Java 17 (or higher)** | Язык разработки, использование рефлексии и коллекций для ORM-моделей         |
| **Hibernate**           | ORM-движок для трансляции объектных графов в реляционные таблицы             |
| **Spring Data JPA**     | Автоматическая генерация CRUD-репозиториев и динамический вывод SQL-запросов |
| **PostgreSQL 16.x**     | Целевая реляционная СУБД со строгим контролем внешних ключей (Foreign Keys). |

---

## 💻 Примеры использования API (Тестовый эндпоинт)

Для верификации каскадных операций и проверки жизненного цикла сущностей был доработан контроллер `HealthController`.

### 1. Сохранение сущности с вложенной коллекцией логов
* **Запрос:** `GET http://localhost:8080/health/jpa`
* **Логика выполнения под капотом:**
```java
TaskEntity task = new TaskEntity("Проверка JPA"); // Состояние: Transient
task.addLog(new LogEntity("Задача создана через /health/jpa")); // Связывание в памяти
TaskEntity saved = taskRepository.save(task); // Состояние: Managed. Каскадное сохранение Task и Log

```

* **Ответ (200 OK):** `Сохранена задача id=1, логов: 1`

### 2. Прямая проверка состояния таблиц в PostgreSQL

При подключении к базе данных `taskdb` через консольный клиент `psql` или любой GUI-инструмент, можно убедиться, что одна DML-команда в коде корректно заполнила обе таблицы со строгим соблюдением ограничений внешнего ключа:

```sql
taskdb=> SELECT * FROM tasks;
 id | completed |    title     
----+-----------+--------------
  1 | f         | Проверка JPA

taskdb=> SELECT * FROM logs;
 id |         created_at         |             message              | task_id 
----+----------------------------+----------------------------------+---------
  1 | 2026-07-18 14:10:00.123456 | Задача создана через /health/jpa |       1

```

*(Hibernate автоматически выполнил генерацию DDL-команд `CREATE TABLE` и настроил `ALTER TABLE ADD CONSTRAINT` благодаря режиму `ddl-auto: update`).*

---

## 🧠 Полученные знания и навыки (Learning Outcomes)

* **Получил практический опыт** работы со сложными механизмами объектно-реляционного отображения (ORM), полностью отказавшись от ручного написания SQL INSERT/UPDATE запросов.
* **Изучил принципы** безопасного построения двунаправленных связей и управления каскадными операциями над зависимыми сущностями.
* **Научился применять** ленивую загрузку (`FetchType.LAZY`) для оптимизации производительности СУБД при вычитке связанных графов объектов.
* **Улучшил понимание** транзакционного контекста и жизненного цикла персистентных сущностей в JPA/Hibernate.
* **Освоил лучшие практики** защиты целостности данных, сочетая автогенерацию схемы на этапе разработки с ручным управлением ссылками через защитные методы (`addLog`).

---

## 🎯 Главный вывод занятия

Использование связки JPA и Hibernate избавляет от написания огромного объема шаблонного JDBC-кода и ручного маппинга данных. Мы освоили жизненный цикл сущностей и правила проектирования связей, создав надежный каркас данных для реализации полноценного REST API управления задачами (Task API).

---

---

# [EN] Working with JPA and Hibernate (Lesson 6)

## 📌 Brief Description

This repository focuses on integrating Object-Relational Mapping (**ORM**) into a Java enterprise application using the **JPA** specification and its **Hibernate** implementation. Building directly upon the PostgreSQL infrastructure established in Lesson 5, this module addresses the fundamental architectural dilemma known as **Object-Relational Impedance Mismatch**.

The codebase demonstrates how Java domain objects and their memory graphs seamlessly map to PostgreSQL relational structures. It configures cascade propagation, handles complex multi-table relationships, and leverages declarative data access layers via Spring Data JPA.

---

## 🎯 Learning Goals

* **Bridge the Impedance Mismatch:** Master ORM patterns to transform object-oriented Java code cleanly into highly consistent relational schemas.
* **Manage Entity Relationships:** Design cohesive bidirectional models and optimize lazy relationship graphs.
* **Configure State Cascading:** Automate synchronization between parent and child entity lifecycles directly at the persistence layer.
* **Leverage Declarative Repositories:** Eliminate boilerplate data-access code by adopting auto-generated Spring Data JPA query interfaces.

---

## 📚 Theoretical Insights

### Core Concepts & Enterprise Application:

1. **JPA vs Hibernate vs Spring Data JPA**
* *JPA (Java Persistence API):* A vendor-agnostic specification providing annotations and rules for managing relational data within Java applications.
* *Hibernate:* A feature-rich ORM provider implementing the JPA specification, dynamically translating entity graph states into database-specific SQL statements.
* *Spring Data JPA:* An abstraction layer that delivers proxy-based repository implementations at runtime based on developer-defined interfaces.


2. **Entity Lifecycle States**
* *Concept:* The four specific operational phases managed by the `EntityManager`: *Transient* (new object), *Managed* (tracked by persistence context), *Detached* (session closed), and *Removed* (scheduled for deletion).
* *Why it matters:* Enables internal optimizations like transactional caching and automated Dirty Checking to detect mutated state before flushing changes to the DB.
* *Backend Role:* Empowers engineers to interact with data using regular Java objects, deferring granular database updates to automated transactional boundaries.


3. **Lazy Fetching (FetchType.LAZY)**
* *Concept:* A proxy-driven data retrieval strategy that delays fetching child table rows until they are explicitly accessed by the application logic.
* *Why it matters:* Acts as a shield against critical performance bottlenecks, resolving the infamous N+1 query problem.
* *Backend Role:* Significantly minimizes runtime memory footprints and database round-trips by retrieving audit logs only when strictly requested.



---

## 🛠 Practical Implementation

The practical lab sets up a classic One-to-Many relational hierarchy between an overarching operational task and its detailed transaction audit logs.

### Key Implementation Milestones:

* **Declarative Entity Modeling (`TaskEntity` & `LogEntity`):** Mapped domain fields using exact JPA definitions, enforcing `length = 255` constraints and `nullable = false` parameters. Configured sequential primary keys using `GenerationType.IDENTITY` mapped onto PostgreSQL `bigserial` columns.
* **Protected No-Args Reflection Constructors:** Implemented encapsulation-friendly `protected` empty constructors to allow Hibernate's reflection engine to rebuild entity instances smoothly from query result sets.
* **Bidirectional Relationship Ownership:** Defined the child `LogEntity` as the driving relationship owner using `@ManyToOne` coupled with a distinct `@JoinColumn` declaration. Configured the parent `TaskEntity` with `cascade = CascadeType.ALL` and `orphanRemoval = true` to clean up detached history nodes seamlessly from database tables.
* **State Synchronization Guard:** Maintained bidirectional object pointer alignment in volatile memory using an atomic domain helper method: `addLog(LogEntity log)`.
* **Query Derivation Interface:** Extended `JpaRepository` with the custom `findByCompleted(boolean completed)` signature, prompting Spring Data to parse it directly into native SQL queries at runtime.

---

## 💻 Tech Stack

| Technology          | Purpose                                                                           |
|---------------------|-----------------------------------------------------------------------------------|
| **Java 17+**        | Core programming language using advanced reflection and standard collections      |
| **Hibernate**.      | Enterprise ORM engine mapping programmatic classes to standard databases          |
| **Spring Data JPA** | Automated proxy CRUD repository generation and dynamic query building             |
| **PostgreSQL 16.x** | Targeted enterprise storage ensuring strict relational integrity via Foreign Keys |

---

## 💻 API Usage Examples (Diagnostic Endpoints)

To test cascade persistence and entities transitions through their lifecycle states, the diagnostic `HealthController` endpoint was extended.

### 1. Persisting an Entity with a Nested Log Collection

* **Request:** `GET http://localhost:8080/health/jpa`
* **Under-the-Hood Lifecycle Logic:**

```java
TaskEntity task = new TaskEntity("Проверка JPA"); // State: Transient
task.addLog(new LogEntity("Задача создана через /health/jpa")); // Memory linkage
TaskEntity saved = taskRepository.save(task); // State: Managed. Cascades both entities to DB

```

* **Response (200 OK):** `Сохранена задача id=1, логов: 1`

### 2. Live Database Integrity Verification via PostgreSQL CLI

Connecting directly to the live `taskdb` instance confirms that a single invocation of the repository layer populates both physical tables under rigid Foreign Key constraints:

```sql
taskdb=> SELECT * FROM tasks;
 id | completed |    title     
----+-----------+--------------
  1 | f         | Проверка JPA

taskdb=> SELECT * FROM logs;
 id |         created_at         |             message              | task_id 
----+----------------------------+----------------------------------+---------
  1 | 2026-07-18 14:10:00.123456 | Задача создана через /health/jpa |       1

```

*(Hibernate automatically evaluated entity metadata and performed DDL actions like `CREATE TABLE` and `ALTER TABLE ADD CONSTRAINT` on the fly due to `ddl-auto: update` configuration).*

---

## 🧠 Learning Outcomes

* **Acquired production experience** utilizing advanced Object-Relational Mapping (ORM) frameworks, eliminating repetitive and fragile manual SQL string concatenations.
* **Mastered the design principles** of robust bidirectional object structures and safe cascading models.
* **Successfully applied** lazy loading configurations (`FetchType.LAZY`) to prevent resource exhaustion and speed up database interactions.
* **Enhanced systemic grasp** of enterprise design patterns, tracking the behavioral movements of data objects through standard persistence contexts.
* **Adopted industry best practices** for data consistency, using custom domain helpers (`addLog`) to prevent inconsistencies between in-memory collections and persistent tables.

---

## 🎯 Takeaway Conclusion

Leveraging the combined power of JPA and Hibernate liberates developers from writing excessive amounts of boilerplate JDBC code and handling manual index mapping. By mastering entity lifecycles and relationship models, we have established a highly scalable, enterprise-ready data architecture designed to power the upcoming Task Management REST API.

```