# ☕ Java Backend Development: Core Foundations (Lesson 2)

<p align="center">
  <a href="https://oracle.com/java/">
    <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  </a>
  <img src="https://img.shields.io/badge/Concurrency-Multithreading-0078D4?style=for-the-badge" alt="Concurrency" />
  <img src="https://img.shields.io/badge/Architecture-SOLID%20%2F%20OOP-4caf50?style=for-the-badge" alt="Architecture" />
  <img src="https://img.shields.io/badge/Spring%20Ready-Backend%20Preparation-green?style=for-the-badge" alt="Spring Ready" />
</p>

---

# [RU] Изучение основ Java для Backend (ООП, Коллекции, Исключения, Дата/Время, Многопоточность)

## 📌 Краткое описание
Этот репозиторий посвящен глубокому изучению и практическому применению фундаментальных концепций Java Core, которые составляют базис разработки современных распределенных систем. Понимание этих механизмов критически важно для перехода к Spring Boot, так как Enterprise-платформы функционируют в многопоточной среде и опираются на гибкие, отказоустойчивые архитектурные паттерны.

В рамках данного модуля реализованы две ключевые архитектурные задачи: проектирование слабосвязанной бизнес-модели на принципах ООП (SOLID) и создание высоконагруженного, потокобезопасного реестра задач с симуляцией конкурентной среды.

---

## 🎯 Цели изучения (Learning Goals)
* **Разработка под конкурентную нагрузку:** Научиться проектировать потокобезопасный код, понимать природу состояния гонки (Race Conditions) и предотвращать потерю данных.
* **Построение чистой архитектуры:** Освоить принципы SOLID, декомпозицию логики и разделение ответственности через интерфейсы и абстрактные классы.
* **Отказоустойчивость приложения:** Создать гибкую систему обработки исключений с помощью кастомных Runtime-исключений.
* **Интеграция со Spring Boot:** Подготовить теоретическую и практическую базу для понимания механизмов Dependency Injection, слоев Repository/Service и обработки конкурентных HTTP-запросов.

---

## 📚 Теоретическая часть (Theory)

### Ключевые концепции и их роль в Enterprise-разработке:

1. **Многопоточность и Concurrency Utility**
   * *Что это:* Выполнение параллельных операций в JVM и использование специализированных неблокирующих структур данных (`ConcurrentHashMap`, пулы потоков).
   * *Зачем:* Позволяет приложениям эффективно утилизировать ресурсы процессора и обрабатывать тысячи запросов одновременно.
   * *Роль в Backend:* Веб-сервер Spring (Tomcat) обрабатывает каждый запрос в отдельном потоке. Использование потокобезопасных коллекций исключает порчу данных в памяти при параллельном доступе.

2. **Иерархия Исключений (Checked vs Unchecked Exceptions)**
   * *Что это:* Разделение ошибок на контролируемые компилятором (Checked) и возникающие во время выполнения (Unchecked / Runtime).
   * *Зачем:* Кастомные Runtime-исключения позволяют избавить код от «засоряющих» блоков `throws` и `try-catch` на промежуточных слоях.
   * *Роль в Backend:* Позволяет выстроить архитектуру централизованной обработки ошибок (в Spring — `@RestControllerAdvice`), делегируя перехват исключений фреймворку.

3. **Разделение контракта и реализации (SOLID: ISP)**
   * *Что это:* Принцип разделения интерфейсов (Interface Segregation Principle).
   * *Зачем:* Гарантирует, что классы не будут зависеть от методов, которые они не используют. 
   * *Роль в Backend:* Интерфейсы позволяют отделить бизнес-логику от инфраструктуры (например, работа с БД, отправка уведомлений), делая компоненты заменяемыми и легко тестируемыми.

---

## 🛠 Практическая реализация (Practice)

В процессе практики были спроектированы и реализованы следующие модули:

### 1. Потокобезопасный реестр задач (`TaskRegistry`)
* База данных в памяти на основе `ConcurrentHashMap<Long, Task>`, исключающая блокировки всей таблицы при записи.
* Автоматическая фиксация времени создания задачи через `java.time.LocalDateTime` и расчет интервалов с помощью `ChronoUnit`.
* Кастомное исключение `TaskNotFoundException` (Unchecked) для прозрачной обработки ошибок.
* Автоматизированный нагрузочный тест с использованием `ExecutorService` (пул из 10 потоков), генерирующий 10 000 параллельных запросов для верификации отсутствия Race Condition.

### 2. Полиморфная модель геометрических фигур (`Shape & Drawable`)
* Абстрактный класс `Shape` для унификации бизнес-логики вычисления площади.
* Интерфейс `Drawable` для изоляции функционала отрисовки графики (ASCII-art).
* Реализация динамического приведения типов (`instanceof`) и runtime-полиморфизма при обработке гетерогенных коллекций в классе `Main`.

---

## 💻 Использованные технологии (Tech Stack)

| Technology              | Purpose                                                                        |
|-------------------------|--------------------------------------------------------------------------------|
| **Java 17 (or higher)** | Основной язык разработки, применение современного API даты/времени, Stream API |
| **Java Concurrency**    | `ExecutorService`, `ConcurrentHashMap` для симуляции высоконагруженной среды   |
| **Git**                 | Управление версиями, ведение чистого коммит-лога                               |

---

## 🧠 Полученные знания и навыки (Learning Outcomes)
* **Получил практический опыт** проектирования и тестирования многопоточного кода без классических мьютексов (избегая избыточного использования `synchronized`).
* **Изучил принципы** построения масштабируемых иерархий классов, разделяя структуры данных и функциональные интерфейсы.
* **Научился применять** современное Java Date/Time API для точного расчета временных интервалов бизнес-событий.
* **Улучшил понимание** архитектуры backend-приложений, осознав параллель между `TaskRegistry` и Spring Repository Layer.
* **Освоил лучшие практики** работы с Runtime-исключениями для построения чистого и легко поддерживаемого кода.

---
---

# [EN] Java Core Foundations for Backend (OOP, Collections, Exceptions, Date/Time, Multithreading)

## 📌 Brief Description
This repository is dedicated to the comprehensive study and practical application of critical Java Core concepts, which serve as the backbone for building scalable and distributed backend systems. Mastery of these mechanisms is a vital prerequisite for transitioning to Spring Boot, as modern web applications operate in highly concurrent environments and rely on robust, loose-coupled architectural design.

Within this module, two key backend concepts were engineered: a decoupled business model utilizing OOP (SOLID) principles, and a high-throughput, thread-safe task registry simulating real-world concurrent execution.

---

## 🎯 Learning Goals
* **Concurrent Programming:** Design thread-safe structures, diagnose Race Conditions, and enforce data consistency under heavy multi-threaded workloads.
* **Clean Architecture Design:** Implement SOLID principles, master abstraction, and decouple logic using dedicated interfaces.
* **Robust Exception Handling:** Build clean exception propagation structures using custom unchecked exceptions.
* **Spring Ecosystem Readiness:** Establish a concrete conceptual link to Spring Boot concepts such as Dependency Injection (DI), Service/Repository layering, and concurrent request processing.

---

## 📚 Theoretical Insights

### Key Concepts & Enterprise Applicability:

1. **Java Concurrency & Non-blocking I/O**
   * *Concept:* Parallel execution via JVM threads using specialized lock-free data structures (`ConcurrentHashMap`, Thread Pools).
   * *Why it matters:* Enables systems to handle thousands of concurrent requests efficiently.
   * *Backend Role:* A Spring Boot web server (like Tomcat) delegates each incoming HTTP request to a separate thread. Thread-safe collections prevent data corruption during parallel processing.

2. **Exception Propagation Architecture**
   * *Concept:* Separating Checked (compile-time) and Unchecked (runtime) exceptions.
   * *Why it matters:* Custom unchecked exceptions eliminate verbose boilerplate code (like `throws` clauses) on intermediary layers.
   * *Backend Role:* Facilitates clean global exception handling (e.g., Spring's `@RestControllerAdvice`), letting the framework intercept business-logic errors at the API boundary.

3. **Interface Segregation (SOLID: ISP)**
   * *Concept:* Designing small, highly focused interfaces rather than single-purpose, bloated contracts.
   * *Why it matters:* Prevents implementing classes from being forced to depend on methods they do not require.
   * *Backend Role:* Decouples high-level business rules from infrastructural concerns (database clients, external APIs), making code modular and testable.

---

## 🛠 Practical Implementation

During this module, the following production-like components were designed and verified:

### 1. High-Throughput Task Registry (`TaskRegistry`)
* An in-memory storage layer powered by `ConcurrentHashMap<Long, Task>`, preventing table-wide synchronization bottlenecks.
* Automated timestamping utilizing `java.time.LocalDateTime` and time elapsed calculations via `ChronoUnit`.
* Implementation of custom `TaskNotFoundException` (Unchecked) to streamline the application control flow.
* A robust load test running on `ExecutorService` (10 worker threads) generating 10,000 concurrent write operations to guarantee thread safety and verify zero data loss.

### 2. Polymorphic Geometric Model (`Shape & Drawable`)
* Abstract `Shape` base class to define mathematical state and contracts.
* `Drawable` interface to segregate render/presentation logic (ASCII rendering) from the core domain.
* Runtime polymorphism and dynamic downcasting (`instanceof`) to process heterogenous collections gracefully in the orchestrating `Main` class.

---

## 💻 Tech Stack

| Technology           | Purpose                                                     |
|----------------------|-------------------------------------------------------------|
| **Java 17+**         | Core programming language, modern Date/Time API, Stream API |
| **Java Concurrency** | `ExecutorService` thread pools, thread-safe collections     |
| **Git**              | Git workflow with clean, semantic commit history            |

---

## 🧠 Learning Outcomes
* **Gained hands-on experience** in engineering and verifying multi-threaded components without utilizing heavy synchronization blockers.
* **Mastered structural inheritance** by carefully dividing domain models and behavioral interfaces.
* **Applied the modern Java Date/Time API** for calculating time differences with microsecond-level accuracy.
* **Bridged the gap to enterprise frameworks** by drawing architectural parallels between `TaskRegistry` and Spring's Repository Layer.
* **Adopted best practices** for custom Runtime exception usage, achieving cleaner method signatures and maintainable codebases.