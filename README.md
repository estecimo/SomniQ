# API de Evaluación de Calidad de Sueño con WEKA

Una aplicación **Spring Boot** que utiliza **WEKA (Waikato Environment for Knowledge Analysis)** para evaluar la calidad de sueño en adultos jóvenes mediante un modelo de aprendizaje automático de tipo Árbol de Decisión (J48). La aplicación sigue los principios de **Clean Architecture**.

Además, integra la **API de Gemini** para proporcionar explicaciones detalladas y personalizadas sobre los resultados obtenidos.

---

## 🎯 Descripción del Proyecto

Este proyecto es una API REST que permite clasificar la calidad de sueño como **Buena** o **Mala** basándose en:

- **Percepción subjetiva** de la calidad de sueño.
- **Duración** habitual del sueño (horas).
- **Latencia** del sueño (tiempo en quedarse dormido).
- **Antecedentes de COVID-19** en los últimos 6 meses.

El modelo fue entrenado con más de 800 registros reales de estudiantes de Ciencias de la Salud.

---

## Estructura del Proyecto

```
SomniQ/
├── src/
│   ├── main/
│   │   ├── java/com/example/calidadsueno/
│   │   │   ├── CalidadSuenoApplication.java     # Punto de entrada
│   │   │   ├── application/
│   │   │   │   └── ClasificarCalidadSuenoUseCase.java
│   │   │   ├── domain/
│   │   │   │   └── CasoCalidadSueno.java
│   │   │   ├── infrastructure/
│   │   │   │   ├── dto/
│   │   │   │   │   └── GeminiResponseDTO.java
│   │   │   │   ├── ServicioEvaluacionJ48.java   # Integración WEKA
│   │   │   │   └── GeminiService.java           # Integración Gemini API
│   │   │   └── interfaces/
│   │   │       └── rest/
│   │   │           ├── CalidadSuenoController.java
│   │   │           ├── CalidadSuenoRequestDTO.java
│   │   │           ├── CalidadSuenoResponseDTO.java
│   │   │           └── ExplicacionRequestDTO.java
│   │   └── resources/
│   │       ├── application.properties           # Configuración (API Keys)
│   │       ├── cienciasdelasalud.model          # Modelo WEKA entrenado
│   │       └── static/                          # Frontend
│   │           ├── images/
│   │           │   ├── geminibadsleep.webp
│   │           │   └── geminigoodspleep.webp
│   │           ├── index.html
│   │           ├── styles.css
│   │           └── app.js
│   └── test/                                    # Tests unitarios
├── pom.xml                                      # Configuración Maven
└── README.md
```

---

## 🚀 Instalación y Ejecución

### Prerrequisitos

- **Java 17** o superior
- **Maven 3.6** o superior
- **API Key de Google Gemini** (para la función de explicación)

### 1️⃣ Compilar

```bash
mvn clean install
```

### 2️⃣ Configurar API Key

Tienes dos opciones para configurar tu llave de Gemini:

**Opción A (Recomendada): Variable de Entorno**

```bash
# Windows PowerShell
$env:GEMINI_API_KEY="tu_api_key_aqui"
```

**Opción B: Archivo de Propiedades**
Edita `src/main/resources/application.properties`:

```properties
gemini.api.key=tu_api_key_aqui
```

### 3️⃣ Ejecutar

```bash
mvn spring-boot:run
```

Accede a la aplicación en: **http://localhost:8080**

---

## 📡 Endpoints de la API

### 1. Evaluar Calidad

**POST** `/api/calidad-sueno/evaluar`

```json
{
  "percepcionCalidad": "Buena",
  "duracionSueno": "Normal",
  "latencia": "Normal",
  "covid": "No"
}
```

### 2. Obtener Explicación (Gemini)

**POST** `/api/calidad-sueno/explicacion`

Recibe los mismos datos más el resultado de la evaluación para generar una explicación en lenguaje natural.

---

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 3.2**
- **WEKA 3.8** (Machine Learning)
- **Gemini API** (Generative AI)
- **HTML/CSS/JS** (Frontend)
