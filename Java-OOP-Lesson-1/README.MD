# 📘 Lesson 1. Java Backend Basics: OOP

## 1. Lesson Objectives

* Understand the practical application of Encapsulation, Inheritance, Polymorphism, and Abstraction in real-world code.
* Prepare for understanding Spring Boot application layers:

  * Entity
  * Service
  * Repository

---

# 2. Core Theory

### 🔒 Encapsulation

Hiding internal data (`private`) and providing controlled access through methods.

**Purpose:** protect object state and business logic from unwanted changes.

---

### 🧬 Inheritance

Reusing fields and methods from another class.

Represents an:

**"is-a" relationship**

Example:

```
Dog is an Animal
```

In modern backend development, inheritance is often replaced by **composition** (`has-a` relationship) to achieve better flexibility and loose coupling.

---

### 🔄 Polymorphism

Allowing different objects to be used through a common interface or base class.

This is the foundation for:

* Interfaces
* Dependency Injection
* Spring Framework architecture

---

### 🎭 Abstraction

Separating the contract ("what to do") from the implementation ("how to do it").

Usually achieved using:

* Interfaces
* Abstract classes

---

# 3. Practical Code Breakdown

## Project Structure

* 📄 `Shape.java` — Abstract class defining the core contract for calculating area.

* 📄 `Drawable.java` — Interface adding a separate capability to "draw".

* 📄 `Circle.java` / `Rectangle.java` — Concrete shapes implementing area calculation and ASCII-art output.

* 📄 `Main.java` — Entry point demonstrating polymorphic collections and type checking.

---

# 4. Key Mechanisms in the Code

## 1. Separation of Concerns (SOLID / Interface Segregation)

The `draw()` method is isolated into the `Drawable` interface instead of being placed directly inside the `Shape` class.

Reason:

* A shape must have an area.
* A shape does not necessarily need to know how to draw itself.

---

## 2. Runtime Polymorphism

Inside the loop, the program calls:

```java
for (Shape shape : shapes) {
    System.out.println("Площадь фигуры: " + shape.calculateArea());
}
```

The JVM automatically determines and executes the correct implementation depending on the actual object type:

* `Circle`
* `Rectangle`

This happens during runtime.

---

## 3. Safe Type Casting (`instanceof`)

When iterating through:

```java
List<Shape>
```

the compiler only knows methods available in `Shape`.

To safely call `draw()`, type checking and casting are required:

```java
if (shape instanceof Drawable) {
    ((Drawable) shape).draw();
}
```

This prevents unsafe casting errors.
