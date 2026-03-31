# backend_template

Spring Boot template with environment-first configuration using a local .env file.

## Prerequisites

- Java 21+
- Maven Wrapper included in this repo
- Windows PowerShell or CMD

## 1) Create your local .env file

Use one of the following commands from the project root.

### PowerShell

Copy-Item .env.example .env

### CMD

copy .env.example .env

## 2) Edit your local values

Open .env and update values as needed.

APP_NAME=backend_template
APP_ENV=local
SERVER_PORT=8080
JWT_SECRET=
JWT_EXPIRATION_MS=3600000

## 3) Run the application

### PowerShell

.\mvnw.cmd spring-boot:run

### CMD

mvnw.cmd spring-boot:run

## 4) Enable pre-commit auto-format

Set repository hooks path once so git uses the shared hook.

### PowerShell

git config core.hooksPath .githooks

### CMD

git config core.hooksPath .githooks

After this, every commit will automatically:

- run formatter (`spotless:apply`)
- run lint (`checkstyle:check`)
- re-stage formatted files

## Code quality commands

### PowerShell

.\mvnw.cmd spotless:apply
.\mvnw.cmd spotless:check
.\mvnw.cmd checkstyle:check

### CMD

mvnw.cmd spotless:apply
mvnw.cmd spotless:check
mvnw.cmd checkstyle:check

## How configuration works

- application.properties imports .env if it exists.
- Environment values are read from .env and can be overridden by system environment variables.
- Defaults are applied if values are missing.

Current mapped properties:

- APP_NAME -> spring.application.name
- APP_ENV -> app.environment
- SERVER_PORT -> server.port
- JWT_SECRET -> app.jwt.secret (optional, for JWT auth modules)
- JWT_EXPIRATION_MS -> app.jwt.expiration-ms (optional)

## Notes

- .env is ignored by git.
- .env.example is committed as a template.
- CI runs on pull requests and pushes to `main` and `master` via GitHub Actions.
