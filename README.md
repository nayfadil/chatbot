# chatbot
ChatbotApp with angular and springboot

# Chatbot Application

This is a simple chatbot application built with Spring Boot for the backend and Angular for the frontend. The application uses H2 in-memory database to store conversation history and FAQ data.

## Prerequisites

Before running the application, ensure you have the following installed on your machine:

- [Java JDK 11 or higher](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/install.html)
- [Node.js and npm](https://nodejs.org/en/download/) (for the Angular frontend)
- [Angular CLI](https://angular.io/cli) (install via npm: `npm install -g @angular/cli`)

## Clone the Repository

Clone the repository to your local machine using Git:

```bash
git clone https://github.com/nayfadil/chatbot.git
cd chatbot
```

## Running the Spring Boot Application

```bash
mvn clean install

mvn spring-boot:run
```

- Access the application: Once the Spring Boot application is running, it will be accessible at: localhost:8080
