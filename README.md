# API Detector de Mutantes

API REST desarrollada con Spring Boot que permite detectar si una secuencia de ADN pertenece a un mutante o a un humano.

## 📋 Tabla de Contenidos

- [Requisitos Previos](#requisitos-previos)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Instalación y Configuración](#instalación-y-configuración)
- [Ejecución de la Aplicación](#ejecución-de-la-aplicación)
- [Endpoints Disponibles](#endpoints-disponibles)
- [Ejemplos de Uso](#ejemplos-de-uso)
- [Testing](#testing)
- [Documentación API (Swagger)](#documentación-api-swagger)
- [Base de Datos](#base-de-datos)

---

## 🔧 Requisitos Previos

Antes de ejecutar la aplicación, asegúrate de tener instalado:

- **Java JDK 17 o superior**
  ```bash
  java -version
  ```
  Deberías ver algo como: `openjdk version "17.0.x"`

- **Git** (para clonar el repositorio)
  ```bash
  git --version
  ```

> **Nota:** No necesitas instalar Gradle globalmente, el proyecto incluye Gradle Wrapper (`gradlew`).

---

## 🚀 Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.5.8**
  - Spring Web
  - Spring Data JPA
  - Spring Boot DevTools
  - Spring Validation
- **H2 Database** (base de datos en memoria)
- **Lombok** (reducción de código boilerplate)
- **SpringDoc OpenAPI** (documentación Swagger)
- **JaCoCo** (cobertura de código)
- **JUnit 5** (testing)
- **Gradle** (gestión de dependencias)

---

## 📦 Instalación y Configuración

### 1. Clonar o ubicar el proyecto

Si ya tienes el proyecto descargado:
```bash
cd /home/fran/Datos/Downloads/IntegradorReyes2025
```

### 2. Verificar el entorno

```bash
# Verificar Java
java -version

# Verificar Gradle Wrapper
./gradlew -version
```

### 3. Dar permisos de ejecución a Gradle Wrapper (Linux/Mac)

```bash
chmod +x gradlew
```

---

## ▶️ Ejecución de la Aplicación

### Opción 1: Ejecutar con Gradle (Desarrollo)

```bash
./gradlew bootRun
```

La aplicación se iniciará en `http://localhost:8080`

### Opción 2: Compilar y ejecutar el JAR (Producción)

```bash
# 1. Limpiar y compilar el proyecto
./gradlew clean build

# 2. Ejecutar el JAR generado
java -jar build/libs/IntegradorReyes2025-0.0.1-SNAPSHOT.jar
```

### Opción 3: Ejecutar en un puerto personalizado

```bash
# Con Gradle
./gradlew bootRun --args='--server.port=8081'

# Con JAR
java -jar build/libs/IntegradorReyes2025-0.0.1-SNAPSHOT.jar --server.port=8081
```

### Verificar que la aplicación está corriendo

```bash
curl -X POST http://localhost:8080/health
```

Respuesta esperada:
```json
{
  "status": "UP",
  "timestamp": "2025-11-25T10:30:00"
}
```

---

## 🌐 Endpoints Disponibles

### 1. **POST /mutant** - Analizar ADN

Verifica si una secuencia de ADN corresponde a un mutante.

**Request:**
```json
{
  "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```

**Responses:**
- `200 OK` - Es un mutante
- `403 Forbidden` - Es un humano
- `400 Bad Request` - Secuencia de ADN inválida

---

### 2. **GET /stats** - Obtener estadísticas

Devuelve estadísticas sobre las verificaciones de ADN realizadas.

**Response:**
```json
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
```

---

### 3. **POST /health** - Health Check

Verifica el estado de la aplicación.

**Response:**
```json
{
  "status": "UP",
  "timestamp": "2025-11-25T10:30:00"
}
```

---

## 📝 Ejemplos de Uso

### Ejemplo 1: Detectar un Mutante (usando curl)

```bash
curl -X POST http://localhost:8080/mutant \
  -H "Content-Type: application/json" \
  -d '{
    "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
  }'
```

**Resultado esperado:** `HTTP 200 OK` (Es mutante)

---

### Ejemplo 2: Detectar un Humano

```bash
curl -X POST http://localhost:8080/mutant \
  -H "Content-Type: application/json" \
  -d '{
    "dna": ["ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"]
  }'
```

**Resultado esperado:** `HTTP 403 Forbidden` (Es humano)

---

### Ejemplo 3: Obtener estadísticas

```bash
curl -X GET http://localhost:8080/stats
```

**Resultado esperado:**
```json
{
  "count_mutant_dna": 1,
  "count_human_dna": 1,
  "ratio": 1.0
}
```

---

### Ejemplo 4: Usando Postman o Insomnia

1. **Método:** POST
2. **URL:** `http://localhost:8080/mutant`
3. **Headers:** `Content-Type: application/json`
4. **Body (raw JSON):**
   ```json
   {
     "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
   }
   ```

---

## 🧪 Testing

### Ejecutar todos los tests

```bash
./gradlew test
```

### Ver reporte de tests (HTML)

Después de ejecutar los tests, abre en tu navegador:
```
build/reports/tests/test/index.html
```

### Ver cobertura de código (JaCoCo)

```bash
./gradlew test jacocoTestReport
```

Abre el reporte en:
```
build/reports/jacoco/test/html/index.html
```

### Ejecutar un test específico

```bash
./gradlew test --tests "org.example.service.MutantDetectorTest"
```

---

## 📚 Documentación API (Swagger)

La aplicación incluye documentación interactiva con Swagger UI.

**Acceder a Swagger UI:**

Una vez la aplicación esté corriendo, visita:
```
http://localhost:8080/swagger-ui.html
```

o

```
http://localhost:8080/swagger-ui/index.html
```

Desde aquí podrás:
- Ver todos los endpoints disponibles
- Probar las APIs directamente desde el navegador
- Ver los schemas de request/response

---

## 💾 Base de Datos

### Configuración de H2

La aplicación usa **H2 Database** en modo en-memoria, lo que significa que los datos se pierden al reiniciar la aplicación.

**Configuración actual** (`application.properties`):
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
```

### Acceder a la Consola H2

1. Inicia la aplicación
2. Visita: `http://localhost:8080/h2-console`
3. Usa estas credenciales:
   - **JDBC URL:** `jdbc:h2:mem:testdb`
   - **User Name:** `sa`
   - **Password:** (dejar en blanco)

---

## 🛠️ Comandos Útiles de Gradle

```bash
# Limpiar el proyecto
./gradlew clean

# Compilar sin tests
./gradlew build -x test

# Ver dependencias
./gradlew dependencies

# Ver tasks disponibles
./gradlew tasks

# Ejecutar con logs detallados
./gradlew bootRun --info

# Ejecutar con stacktrace en caso de error
./gradlew build --stacktrace
```

---

## 🐛 Solución de Problemas

### Error: "Could not find method jacoco()"

Si encuentras este error, verifica que el plugin JaCoCo esté en `build.gradle`:
```groovy
plugins {
    id 'jacoco'
}
```

### Puerto 8080 ya en uso

Cambia el puerto en `application.properties`:
```properties
server.port=8081
```

O usa argumentos al ejecutar:
```bash
java -jar build/libs/IntegradorReyes2025-0.0.1-SNAPSHOT.jar --server.port=8081
```

### Error de permisos en Linux

```bash
chmod +x gradlew
```

---

## 📄 Estructura del Proyecto

```
src/
├── main/
│   ├── java/org/example/
│   │   ├── MutantDetectorApplication.java    # Clase principal
│   │   ├── controller/                        # Controladores REST
│   │   ├── service/                           # Lógica de negocio
│   │   ├── repository/                        # Acceso a datos
│   │   ├── entity/                            # Entidades JPA
│   │   ├── dto/                               # DTOs
│   │   ├── validation/                        # Validadores personalizados
│   │   ├── exception/                         # Manejo de excepciones
│   │   └── config/                            # Configuraciones
│   └── resources/
│       └── application.properties             # Configuración de la app
└── test/
    └── java/org/example/                      # Tests unitarios
```

---

## 🎯 Reglas de Negocio

Un humano es considerado **mutante** si se encuentran **más de una secuencia** de cuatro letras iguales, de forma:
- Horizontal
- Vertical
- Diagonal (ambas direcciones)

Las letras válidas son: **A, T, C, G**

La matriz de ADN debe ser **NxN** (cuadrada).

---

## 📞 Contacto y Soporte

Si tienes problemas o preguntas sobre la ejecución de la aplicación, revisa:
- Los logs de la aplicación en la consola
- El archivo `HELP.md` del proyecto
- La documentación de Spring Boot: https://spring.io/projects/spring-boot

---

## 📝 Licencia

Este proyecto es un ejercicio de demostración educativa.

---

**¡Listo! Ya puedes ejecutar la API y comenzar a detectar mutantes 🧬**

