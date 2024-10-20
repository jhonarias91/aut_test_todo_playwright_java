# Aut testing for TODO app

## Install
- Clone the repository
```bash
    git clone https://github.com/jhonarias91/aut_test_todo_playwright_java.git
```
- Install maven
- Install JDK 17

```bash
mvn clean compile install
```
Las pruebas automáticas se ejecutan en el siguiente orden:
    - Agregar una nueva task
    - Completar Task
    - Limpiar Todas las tasks

Se asume que el login funcione correctamente, por lo que este se realiza en el beforeAll.

## Run
```bash
mvn install test
```
