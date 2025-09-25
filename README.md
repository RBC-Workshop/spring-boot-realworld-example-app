# ![TODO App using Spring Boot](example-logo.png)

[![Actions](https://github.com/gothinkster/spring-boot-realworld-example-app/workflows/Java%20CI/badge.svg)](https://github.com/gothinkster/spring-boot-realworld-example-app/actions)

> ### Spring boot + MyBatis TODO application with task management, reminders, categories, and more.

This codebase demonstrates a fully fledged TODO application built with Spring Boot + MyBatis including task management, authentication, reminders, categories, and more.

This application was converted from the [RealWorld](https://github.com/gothinkster/realworld-example-apps) blogging platform to a task management system.

# Features

The TODO application includes the following features:

* Task management with status (TODO, IN_PROGRESS, COMPLETED, CANCELLED)
* Task priorities (LOW, MEDIUM, HIGH, URGENT)
* Due dates and reminders
* Task categories
* Task notes
* Mark tasks as important
* Filter tasks by status, priority, category, and due date
* View today's tasks and overdue tasks

# How it works

The application uses Spring Boot (Web, Mybatis) with a modern frontend.

* Uses Domain Driven Design to separate the business term and infrastructure term
* Uses MyBatis to implement the [Data Mapper](https://martinfowler.com/eaaCatalog/dataMapper.html) pattern for persistence
* Uses [CQRS](https://martinfowler.com/bliki/CQRS.html) pattern to separate the read model and write model
* Provides both REST API and GraphQL support

The code is organized as follows:

1. `api` is the web layer implemented by Spring MVC
2. `core` is the business model including entities and services
3. `application` is the high-level services for querying the data transfer objects
4. `infrastructure` contains all the implementation classes as the technique details
5. `static` contains the frontend UI built with HTML, CSS, and JavaScript

# Security

Integration with Spring Security and JWT token authentication.

The secret key is stored in `application.properties`.

# Database

It uses a SQLite database (for easy local testing without losing data after restart), which can be changed easily in the `application.properties` for any other database.

# Getting started

You'll need Java 11 installed.

    ./gradlew bootRun

To test that it works, open a browser tab at http://localhost:8080/categories.  
Alternatively, you can run

    curl http://localhost:8080/categories

# Try it out with [Docker](https://www.docker.com/)

You'll need Docker installed.
	
    ./gradlew bootBuildImage --imageName spring-boot-realworld-example-app
    docker run -p 8081:8080 spring-boot-realworld-example-app

# Try it out with the included frontend

The TODO application includes a modern frontend UI. After starting the application, visit http://localhost:8080 to access the web interface.

# Run test

The repository contains a lot of test cases to cover both api test and repository test.

    ./gradlew test

# Code format

Use spotless for code format.

    ./gradlew spotlessJavaApply

# Help

Please fork and PR to improve the project.
