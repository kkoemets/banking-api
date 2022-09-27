# Banking API

The aim of this project is to demonstrate how to create a simple banking API with a common Java stack.

### 1. Technology

#### 1.1. List of technologies used

* Java 17
* Spring Boot 2
* Postgres 13
* Liquibase
* MyBatis
* RabbitMQ
* JUnit 5
* Docker

### 2. Prerequisites

#### 2.1. List of prerequisites to run the applications:

1. Java 17 SDK: `sudo apt install -y openjdk-17-jdk`
2. Docker: `https://docs.docker.com/get-docker/`

### 3. Getting started on development environment

Development has been mainly done on WSL2 using IntelliJ and bash environment.

#### 3.1. Installation

##### 3.1.1. Postgres with Liquibase from project root

`cd docker/postgres && docker-compose up`

##### 3.1.2. RabbitMQ from project root

`cd docker/rabbitmq && docker-compose up`

#### 3.2. Testing

##### 3.2.1. Running all unit tests from project root

`./gradlew test --continue`

##### 3.2.2. Running all integration tests from project root

`./gradlew integrationTest --continue`

### 4. Applications

#### 4.1. List of applications with short description

* account-api - exposes REST API to create and get accounts, create and get transactions.
* daemon - consumes RabbitMQ queue messages.
