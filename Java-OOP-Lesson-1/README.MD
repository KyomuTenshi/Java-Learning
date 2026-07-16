# ☕ Java Backend Journey: OOP Fundamentals (Lesson 1)

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/OOP-Object--Oriented%20Programming-blue?style=for-the-badge" alt="OOP" />
  <img src="https://img.shields.io/badge/Backend%20Preparation-Spring%20Ready-green?style=for-the-badge" alt="Backend Preparation" />
</p>

---

# [RU] Изучение основ ООП на Java (Занятие 1)

## 📌 Краткое описание
Этот репозиторий посвящен глубокому повторению и практическому применению **Объектно-Ориентированного Программирования (ООП)** на Java. Данная тема является фундаментальным базисом Java Backend разработки. Любое enterprise-приложение на Spring Boot опирается на глубокое понимание полиморфизма, абстракции и правильного разделения ответственности. 

В рамках этого модуля на примере предметной области геометрических фигур реализована чистая архитектура, демонстрирующая переход от базового синтаксиса к промышленным паттернам проектирования.

---

## 🎯 Цели изучения (Learning Goals)
* **Переход от синтаксиса к проектированию:** Освоить применение четырех столпов ООП не как абстрактных терминов, а как инструментов создания гибкого кода.
* **Разделение ответственности (Separation of Concerns):** Понять разницу между наследованием состояния/поведения (`extends`) и реализацией контракта (`implements`).
* **Подготовка к Spring Framework:** Осознать, как полиморфизм и программирование от интерфейсов ложатся в основу Dependency Injection (DI) и слабосвязанной архитектуры.

---

## 📚 Теоретическая часть (Theory)

### Фундаментальные концепции ООП в контексте Backend-разработки:

1. **Абстракция (Abstraction)**
   * *Что это:* Выделение ключевых характеристик объекта и скрытие несущественных деталей реализации.
   * *Зачем:* Позволяет backend-разработчику проектировать систему на уровне высокоуровневых контрактов. Например, абстрактная сущность `Shape` декларирует метод `calculateArea()`, но делегирует вычисления конкретным фигурам.
   * *Роль в Backend:* Лежит в основе создания сервис-архетипов и слоев доступа к данным (Repository Layer).

2. **Инкапсуляция (Encapsulation)**
   * *Что это:* Сокрытие внутреннего состояния объекта и предоставление контролируемого доступа через публичные методы.
   * *Зачем:* Предотвращает несанкционированное или некорректное изменение данных извне, защищая бизнес-логику приложения.
   * *Роль в Backend:* Создание безопасных DTO (Data Transfer Objects) и Entity-классов, где доступ к полям строго регламентирован (через геттеры, сеттеры или иммутабельные конструкторы).

3. **Наследование (Inheritance)**
   * *Что это:* Механизм, позволяющий одному классу наследовать свойства и методы другого.
   * *Зачем:* Избавляет от дублирования кода (DRY - Don't Repeat Yourself) и выстраивает иерархию типов (отношение *is-a*).
   * *Роль в Backend:* Создание базовых классов для сущностей (например, `BaseEntity` с общими полями `id`, `createdAt`, `updatedAt`).

4. **Полиморфизм (Polymorphism)**
   * *Что это:* Способность программы использовать объекты с одинаковым интерфейсом без информации о конкретном типе этого объекта.
   * *Зачем:* Позволяет писать расширяемый код. Мы работаем с типом `Shape`, а JVM во время выполнения сама решает, метод какого класса вызвать (*Dynamic Binding*).
   * *Роль в Backend:* Обеспечивает слабую связанность (Loose Coupling). Сервисы Spring работают с абстрактными интерфейсами репозиториев, что позволяет легко менять реализации (например, SQL на NoSQL) без изменения логики сервиса.

---

## 🛠 Практическая реализация (Practice)

В процессе практики была реализована консольная система для обработки и визуализации геометрических фигур со следующими компонентами:

* **Абстрактный класс `Shape`**: Задает общий контракт для расчета площади (`calculateArea()`).
* **Интерфейс `Drawable`**: Отдельный контракт для объектов, которые могут быть отрисованы на консоли. Применение принципа *Interface Segregation* (ISP из SOLID) — не каждая фигура обязана уметь себя рисовать.
* **Классы `Circle` и `Rectangle`**: Конкретные реализации фигур со скрытыми свойствами (`private`) и переопределенными методами расчета площади и отрисовки с аннотацией `@Override`.
* **Класс `Main`**: Демонстрация полиморфного обхода коллекции `List<Shape>` и приведения типов к `Drawable` на этапе выполнения.

---

## 💻 Использованные технологии (Tech Stack)

| Technology              | Purpose                                                               |
|-------------------------|-----------------------------------------------------------------------|
| **Java 17 (or higher)** | Основной язык программирования, использование современного синтаксиса |
| **Git**                 | Контроль версий, ведение чистого коммит-лога                          |
| **OOP Architecture**    | Архитектурный подход к проектированию связей между компонентами       |

---

## 🧠 Полученные знания и навыки (Learning Outcomes)
* **Получил практический опыт** проектирования иерархии классов с использованием абстрактных классов и интерфейсов.
* **Изучил принципы** безопасной инкапсуляции данных для предотвращения нарушения инвариантов бизнес-модели.
* **Научился применять** полиморфизм времени выполнения (*Runtime Polymorphism*) для обработки разнородных коллекций.
* **Улучшил понимание** архитектуры backend-приложений, проведя параллель между полиморфизмом Java и принципом Dependency Injection (DI) в Spring.
* **Освоил лучшие практики** разделения ответственности (Interface Segregation), что делает код легко расширяемым.

---
---

# [EN] Mastering OOP Fundamentals in Java (Lesson 1)

## 📌 Brief Description
This repository is dedicated to the deep-dive review and practical implementation of **Object-Oriented Programming (OOP)** in Java. OOP is the bedrock of professional Java Backend Development. Every modern enterprise system built on Spring Boot relies heavily on an intimate understanding of polymorphism, abstraction, and clean separation of concerns.

Using a geometric shape domain model, this project implements a clean-code architecture that demonstrates the transition from basic programming syntax to production-grade design patterns.

---

## 🎯 Learning Goals
* **Transition from Syntax to Design:** Apply the four pillars of OOP not as abstract dictionary definitions, but as practical tools to build highly maintainable codebases.
* **Separation of Concerns:** Master the core difference between state/behavior inheritance (`extends`) and contractual implementation (`implements`).
* **Spring Framework Readiness:** Understand how polymorphism and interface-driven programming lay the foundation for Dependency Injection (DI) and loosely coupled service architectures.

---

## 📚 Theoretical Insights

### Key OOP Concepts Applied to Backend Development:

1. **Abstraction**
   * *Concept:* Isolating essential object characteristics while hiding irrelevant implementation details.
   * *Application:* Declaring the high-level `Shape` entity with a `calculateArea()` method, leaving implementation specifics to individual subtypes.
   * *Backend Role:* Serves as the foundation for building abstract service contracts and data access interfaces (Repository Layer).

2. **Encapsulation**
   * *Concept:* Restricting direct access to an object's state and forcing interaction through controlled public methods.
   * *Application:* Defining fields like `radius` or `width`/`height` as `private` and controlling their lifecycles strictly via constructor arguments.
   * *Backend Role:* Crucial for designing secure Entity models and Data Transfer Objects (DTOs) where state corruption must be strictly prevented.

3. **Inheritance**
   * *Concept:* Allowing a child class to inherit state and behavior from a parent class to promote reusability.
   * *Application:* Establishing an *is-a* relationship where `Circle` and `Rectangle` extend `Shape`.
   * *Backend Role:* Used for creating reusable base classes (e.g., a `BaseEntity` holding common metadata fields like `id`, `createdAt`, `updatedAt`).

4. **Polymorphism**
   * *Concept:* Treating distinct objects uniformly through their common parent class or interface.
   * *Application:* Storing various shapes in a single `List<Shape>` and processing them dynamically using *Runtime Polymorphism* (Dynamic Binding).
   * *Backend Role:* Enables loose coupling. Spring services interact with database interfaces (e.g., `JpaRepository`) without knowing or caring about the underlying database technology.

---

## 🛠 Practical Implementation

The practical portion of this module features a console-based application modeling geometric shapes with the following component architecture:

* **`Shape` Abstract Class**: Establishes the mathematical contract for calculating area.
* **`Drawable` Interface**: Separates the console rendering capabilities from the mathematical model (implementing the **Interface Segregation Principle**).
* **`Circle` & `Rectangle` Classes**: Concrete implementations featuring strict encapsulation (`private` variables) and robust method overriding decorated with `@Override` annotations.
* **`Main` Entry Point**: Showcases polymorphic collection iteration, dynamic type checks using `instanceof`, and safe downstream interface casting.

---

## 💻 Tech Stack

| Technology                | Purpose                                                      |
|---------------------------|--------------------------------------------------------------|
| **Java 17+**              | Core programming language utilizing clean, modern OOP syntax |
| **Git**                   | Version control with semantic and clear commit messages      |
| **Clean Code Principles** | Strict separation of concerns and cohesive domain design     |

---

## 🧠 Learning Outcomes
* **Gained practical experience** designing class hierarchies leveraging the synergy between abstract classes and interfaces.
* **Learned best-practice patterns** for state encapsulation to safeguard business logic from unwanted external mutations.
* **Applied dynamic polymorphism** to handle polymorphic collections smoothly and cleanly.
* **Bridged Java theory with enterprise practice** by connecting basic Java polymorphism to Spring's Dependency Injection (DI) mechanism.
* **Mastered Interface Segregation (ISP)**, ensuring that classes are not forced to depend on methods they do not use.