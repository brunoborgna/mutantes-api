# 🧬 Detector de Mutantes API

Este proyecto es una API REST desarrollada en **Java** con **Spring Boot** para detectar si una secuencia de ADN pertenece a un mutante o a un humano, basándose en el análisis de patrones en una matriz (NxN).

El sistema cumple con los requisitos funcionales de detección horizontal, vertical y oblicua, y ha sido optimizado para manejar grandes volúmenes de datos mediante estrategias de **Terminación Temprana** y **Caché de Resultados (Hash SHA-256)**.

---

## 🔗 Enlaces del Proyecto

* ☁️ **API en Producción (Render):** [https://mutantes-api-borgnabruno.onrender.com](https://mutantes-api-borgnabruno.onrender.com)
* 📄 **Documentación Swagger:** [https://mutantes-api-borgnabruno.onrender.com/swagger-ui.html](https://mutantes-api-borgnabruno.onrender.com/swagger-ui.html)
* 🐙 **Repositorio GitHub:** [https://github.com/brunoborgna/mutantes-api](https://github.com/brunoborgna/mutantes-api)

---

## 🚀 Tecnologías Utilizadas

* **Java 17** (Compatible con 21)
* **Spring Boot 3**
* **H2 Database** (Base de datos en memoria para persistencia)
* **Gradle** (Gestor de dependencias)
* **JUnit 5 & Mockito** (Testing)
* **Lombok** (Reducción de código repetitivo)
* **SpringDoc OpenAPI** (Documentación visual con Swagger)

---

## 🛠️ Instrucciones de Ejecución (Local)

### Prerrequisitos
Tener instalado **Java 17** o superior. No es necesario tener Gradle instalado, el proyecto incluye el wrapper.

### 1. Descargar el Código
Descarga el archivo `.zip`, descomprímelo y abre una terminal en la carpeta raíz del proyecto.

### 2. Ejecutar la Aplicación
Puedes iniciar el servidor utilizando los siguientes comandos según tu sistema operativo:

**En Windows:**

./gradlew.bat bootRun
En Linux / Mac:



./gradlew bootRun
Una vez iniciado, verás el log indicando que la aplicación corre en el puerto 8080.

📡 Uso de la API
📄 Documentación Interactiva (Swagger UI)
La forma más recomendada de probar la API localmente es a través de Swagger.

👉 URL Local: http://localhost:8080/swagger-ui.html

Endpoints Principales
1. Detectar Mutante
Analiza una secuencia de ADN. Retorna 200 OK si es mutante, 403 Forbidden si es humano.

Método: POST

URL: /mutant

Body (JSON):



{
  "dna": [
    "ATGCGA",
    "CAGTGC",
    "TTATGT",
    "AGAAGG",
    "CCCCTA",
    "TCACTG"
  ]
}
2. Estadísticas
Devuelve el conteo de verificaciones y el ratio de mutantes.

Método: GET

URL: /stats

Respuesta (JSON):



{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
3. Health Check
Verifica que la aplicación esté corriendo correctamente.

Método: GET

URL: /actuator/health

Respuesta: {"status": "UP"}

🧪 Testing y Cobertura
El proyecto cuenta con una suite de tests exhaustiva (>35 tests) que cubre casos borde, validaciones, lógica de negocio y controladores.

Ejecutar Tests Automatizados
Para correr todos los tests y verificar que el sistema funciona:

Windows:

./gradlew.bat test
Linux / Mac:

./gradlew test
Ver Reporte de Cobertura (Code Coverage)
El proyecto genera un reporte HTML detallado sobre la cobertura de código (superior al 80%).

Ejecuta el comando:

./gradlew.bat test jacocoTestReport
Abre el archivo generado en tu navegador: build/reports/jacoco/test/html/index.html

💾 Base de Datos (H2)
La aplicación utiliza una base de datos en memoria H2. Los registros se guardan utilizando un Hash SHA-256 del ADN para evitar duplicados y optimizar la velocidad de búsqueda.

Consola Web H2: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb

User: sa

Password: (dejar vacío)