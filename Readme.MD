# Pruebas Automatizadas para Aplicación TODO

Este proyecto contiene pruebas automatizadas para la aplicación TODO utilizando Playwright y JUnit en Java. 
El objetivo es validar las funcionalidades principales de la aplicación, como agregar tareas, completar tareas y limpiar tareas inactivas.

## Requisitos
Git: Para clonar el repositorio.
Maven: Para gestionar las dependencias y ejecutar las pruebas.

JDK 17: Para ejecutar el código en Java.
## Instalación
- Clonar el repositorio:

```bash
git clone https://github.com/jhonarias91/aut_test_todo_playwright_java.git
```

- Instalar Maven si no lo tienes:

Guía de instalación de Maven
- Instalar JDK 17:

Asegúrate de que java -version muestre Java 17 correctamente.
- Compilar e instalar dependencias:

```bash
mvn clean compile install
```

## Estructura de las Pruebas
Las pruebas automatizadas se ejecutan en el siguiente orden:

Agregar una nueva tarea:

Crea una nueva tarea en la lista de tareas activas y valida que esté al final de la lista.
Completar una tarea:

Marca una tarea como completada y valida que se haya movido a la lista de tareas inactivas.
Limpiar todas las tareas:

Limpia las tareas inactivas y valida que hayan sido eliminadas correctamente.
Se asume que el proceso de login funciona sin problemas, ya que este se maneja en el método beforeEach, lo que garantiza que todas las pruebas se ejecutan después de haber iniciado sesión.

## Trazabilidad 
Si deseas habilitar la trazabilidad durante la ejecución de las pruebas, puedes habilitar la opción ENABLE_TRACING en el código fuente. Esto generará un archivo trace.zip con la información de rastreo de cada prueba.

## Ejecución de las Pruebas
Para ejecutar todas las pruebas de manera secuencial, utiliza el siguiente comando:

```bash
mvn test
```

Si deseas ejecutar las pruebas con trazabilidad habilitada, asegúrate de cambiar la variable ENABLE_TRACING a true en el archivo de prueba TodoismTest.

## Cierre del Entorno de Pruebas
El entorno de Playwright se cierra automáticamente después de que todas las pruebas se hayan ejecutado, asegurando que no queden procesos abiertos.