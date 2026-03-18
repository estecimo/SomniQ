# API de Evaluación de Calidad de Sueño con WEKA

Una aplicación **Spring Boot** que utiliza **WEKA (Waikato Environment for Knowledge Analysis)** para evaluar la calidad de sueño en adultos jóvenes mediante un modelo de aprendizaje automático de tipo Árbol de Decisión (J48).

Además, integra la **API de Gemini** para proporcionar explicaciones detalladas y personalizadas sobre los resultados obtenidos.

---

## 🎯 Descripción del Proyecto

Este proyecto es una API REST que permite clasificar la calidad de sueño como **Buena** o **Mala** basándose en el modelo entrenado con datos reales de estudiantes de Ciencias de la Salud o de el Campus de Ciencias Exactas e Ingenierías.

---

## Branches

- **master**: rama principal
- **release/11feb26-cs-salud**: rama usando el modelo para el campus de ciencias de la salud
- **release/11feb26-ing-cs-exactas**: rama usando el modelo para el campus de ciencias exactas e ingenierías

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

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 3.2**
- **WEKA 3.8** (Machine Learning)
- **Gemini API** (Generative AI)
- **HTML/CSS/JS** (Frontend)
