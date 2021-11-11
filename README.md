# Codigoton
Bancolombia codigoton

This is a personal project, intented to solve a programming excersise for a Hackaton. The description of the problem is in the file named: problem-statement.pdf

## Tech stack
- Java 11
- SpringBoot 2.5.6
- Gradle 7.3
- MySQL

## Run

In order to execute this program, follow these steps:

### Setup project

```bash
git clone https://github.com/camilohurtado/codigoton.git
cd codigoton
```

### Setup Database

```bash
cd docker
docker-compose up -d
```

### Run application
```bash
cd ..
gradle clean build bootRun
```

The project will run on port 8080 by default. Hit the following endpoint to get the result:

```bash
GET http://localhost:8080/invitations
```

