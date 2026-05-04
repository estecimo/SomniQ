# API de Clasificación de Calidad de Sueño con WEKA + Gemini AI

Aplicación **Spring Boot** que utiliza **WEKA** para clasificar la calidad del sueño y **Google Gemini AI** para generar explicaciones inteligentes.

---

## 🎯 Descripción del Proyecto

API REST que permite:

1. **Clasificar la calidad del sueño** como **Buena** o **Mala** según hábitos y características del usuario.
2. **Generar explicaciones personalizadas** usando Google Gemini AI (especialista en Medicina del Sueño).

### Características Analizadas

- Percepción subjetiva del sueño
- Duración y latencia del sueño
- Somnolencia diurna
- Frecuencia de uso de medicamentos
- Adicción a internet y hábitos online
- Datos demográficos (sexo)

---

## Estructura del Proyecto

```
SomniQ/
├── src/
│   ├── main/
│   │   ├── java/com/example/calidadsueno/
│   │   │   ├── CalidadSuenoApplication.java
│   │   │   ├── application/
│   │   │   │   └── ClasificarCalidadSuenoUseCase.java
│   │   │   ├── config/
│   │   │   │   └── RestConfig.java
│   │   │   ├── domain/
│   │   │   │   └── CasoCalidadSueno.java
│   │   │   ├── infrastructure/
│   │   │   │   └── weka/
│   │   │   │       ├── WekaModeloCalidadSueno.java
│   │   │   │       ├── GeminiService.java
│   │   │   │       └── dto/
│   │   │   │           └── GeminiResponseDTO.java
│   │   │   └── interfaces/
│   │   │       └── rest/
│   │   │           ├── CalidadSuenoController.java
│   │   │           ├── CalidadSuenoRequestDTO.java
│   │   │           ├── CalidadSuenoResponseDTO.java
│   │   │           └── ExplicacionRequestDTO.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── Ciencias exactas_Unpruned model.model
│   │       └── static/
│   │           ├── index.html
│   │           ├── styles.css
│   │           ├── app.js
│   │           └── images/
│   │               ├── geminigoodspleep.webp
│   │               ├── geminibadsleep.webp
|   |               └── uady.png
│   └── test/
├── pom.xml
└── README.md
```

---

## Tecnologías Utilizadas

| Tecnología      | Versión       | Propósito                                  |
|-----------------|---------------|--------------------------------------------|
| Java            | 17            | Lenguaje de programación                   |
| Spring Boot     | 3.2.2         | Framework web y REST API                   |
| WEKA            | 3.8.6         | Machine Learning (Árbol de decisión J48)   |
| Google Gemini   | 1.5 Flash     | IA generativa para explicaciones           |
| Maven           | 3.6+          | Gestión de dependencias y build            |
| HTML/CSS/JS     | ES6           | Frontend interactivo                       |

---

## Prerrequisitos

- **Java 17+**
- **Maven 3.6+**
- **API Key de Google Gemini** (obligatoria para explicaciones)

Verifica instalaciones:
```bash
java -version
mvn -version
```

---

## Instalación y Ejecución

1. **Clona el repositorio**
2. **Compila el proyecto**
   ```bash
   mvn clean compile
   ```
3. **Configura Google Gemini**
   - Opción A: Edita `src/main/resources/application.properties`:
     ```properties
     server.port=8080
     gemini.api.key=tu_api_key_gemini_aqui
     ```
   - Opción B: Variable de entorno (Windows):
     ```bash
     set GEMINI_API_KEY=tu_api_key_aqui
     ```
4. **Ejecuta la aplicación**
   ```bash
   mvn spring-boot:run
   ```
   o
   ```bash
   mvn clean package
   java -jar target/calidadsueno-0.0.1-SNAPSHOT.jar
   ```

Accede a: [http://localhost:8080](http://localhost:8080)

---

## 📡 API Endpoints

### 1. Clasificar Calidad de Sueño

**Endpoint:**
- **POST** `/api/calidad-sueno/clasificar`

**Request Body:**
```json
{
  "percepcion": "Buena",
  "frecuenciaMedicacion": "Una o dos veces en el último mes",
  "duracionSueno": "Normal (6-9 horas)",
  "somnolenciaDiurna": "Normal",
  "latencia": "Normal (5-15 minutos)",
  "adiccionInternet": "No",
  "nivelAdiccion": "Sin adicción",
  "ventaOnline": "No",
  "comprasOnline": "Sí",
  "sexo": "Hombre"
}
```

**Response:**
```json
{
  "resultado": "Buena"
}
```

---

### 2. Generar Explicación con Gemini AI

**Endpoint:**
- **POST** `/api/calidad-sueno/explicar`

**Request Body:**
```json
{
  "percepcion": "Buena",
  "frecuenciaMedicacion": "Una o dos veces en el último mes",
  "duracionSueno": "Normal (6-9 horas)",
  "somnolenciaDiurna": "Normal",
  "latencia": "Normal (5-15 minutos)",
  "adiccionInternet": "No",
  "nivelAdiccion": "Sin adicción",
  "ventaOnline": "No",
  "comprasOnline": "Sí",
  "sexo": "Hombre",
  "resultado": "Buena"
}
```

**Response:**
```json
{
  "explicacion": "Tu calidad de sueño se clasifica como Buena. Esto se debe a que..."
}
```

---

## Parámetros de Entrada

| Parámetro            | Tipo   | Valores posibles                                               |
|----------------------|--------|---------------------------------------------------------------|
| percepcion           | String | "Buena", "Mala"                                               |
| frecuenciaMedicacion | String | "Ninguna vez en el último mes", "Una o dos veces en el último mes", etc. |
| duracionSueno        | String | "Corta (< 6 horas)", "Normal (6-9 horas)", "Larga (> 9 horas)" |
| somnolenciaDiurna    | String | "Normal", "Marginal", "Excesiva"                              |
| latencia             | String | "Patológica (< 5 minutos)", "Normal (5-15 minutos)", "Prolongada (> 15 minutos)" |
| adiccionInternet     | String | "Sí", "No"                                                    |
| nivelAdiccion        | String | "Sin adicción", "Leve", "Moderada", "Severa"                  |
| ventaOnline          | String | "Sí", "No"                                                    |
| comprasOnline        | String | "Sí", "No"                                                    |
| sexo                 | String | "Hombre", "Mujer"                                             |

---

## Interfaz Web

Frontend interactivo ubicado en `src/main/resources/static/`:

- **index.html**: Formulario con 10 campos de entrada
- **styles.css**: Estilos responsivos
- **app.js**: Lógica frontend y comunicación con API REST
- **images/**: Imágenes dinámicas según resultado (bueno/malo)

**Características:**
- Validación de campos requeridos
- Cambio dinámico de color e imagen según predicción
- Loading spinner durante solicitudes
- Explicación generada por Gemini AI

---

## Configuración

### application.properties

```properties
server.port=8080
server.servlet.context-path=/
spring.web.cors.allowed-origins=*
gemini.api.key=${GEMINI_API_KEY}
```

**Variables de entorno recomendadas:**
- `GEMINI_API_KEY`: Tu API Key de Google Gemini

---

## Solución de Problemas

| Problema | Solución |
|----------|----------|
| Java/Maven no instalado | Instala y verifica con `java -version` y `mvn -version` |
| Puerto 8080 ocupado | Cambia `server.port` en `application.properties` |
| Modelo WEKA no cargado | Verifica que `Ciencias exactas_Unpruned model.model` esté en `src/main/resources/` |
| `/explicar` no funciona | Configura `GEMINI_API_KEY` y verifica conexión a internet |
| CORS bloqueado | Habilitado globalmente en `@CrossOrigin(origins = "*")` |

---

## Información del Modelo WEKA

- **Nombre**: Ciencias exactas_Unpruned model
- **Algoritmo**: J48 (Árbol de decisión sin pruning)
- **Atributos de entrada**: 10 características del usuario
- **Atributo de salida**: "Calidad de sueño" (Buena/Mala)
- **Tipología**: Clasificador determinístico
- **Velocidad**: Predicción instantánea

---

## Arquitectura del Proyecto

### Clean Architecture - 4 Capas

1. **Interfaces (REST)**
   - Controladores y DTOs
   - Expone endpoints HTTP

2. **Application (Casos de Uso)**
   - `ClasificarCalidadSuenoUseCase`: Orquesta la lógica de negocio

3. **Domain (Núcleo)**
   - `CasoCalidadSueno`: Entidad de dominio con 10 atributos

4. **Infrastructure (Adaptadores)**
   - `WekaModeloCalidadSueno`: Carga y ejecuta modelo WEKA
   - `GeminiService`: Integración con Google Gemini
   - DTOs específicos de respuesta

---

## Notas Importantes

- **Clean Architecture**: Separación clara de responsabilidades por capas
- **CORS habilitado**: Permite acceso desde cualquier origen (usar con cuidado en producción)
- **API Key Gemini**: No la publiques en el repositorio, usa variables de entorno
- **Modelo WEKA**: Los valores de entrada deben coincidir exactamente con las etiquetas del modelo entrenado
- **Requisitos mínimos**: Java 17+, 512 MB RAM, conexión a internet para Gemini

---

## Uso desde Terminal (cURL)

**Clasificar:**
```bash
curl -X POST http://localhost:8080/api/calidad-sueno/clasificar \
  -H "Content-Type: application/json" \
  -d '{
    "percepcion": "Buena",
    "frecuenciaMedicacion": "Una o dos veces en el último mes",
    "duracionSueno": "Normal (6-9 horas)",
    "somnolenciaDiurna": "Normal",
    "latencia": "Normal (5-15 minutos)",
    "adiccionInternet": "No",
    "nivelAdiccion": "Sin adicción",
    "ventaOnline": "No",
    "comprasOnline": "Sí",
    "sexo": "Hombre"
  }'
```

---

**Última actualización**: Marzo 16, 2026  
**Versión**: 0.0.1-SNAPSHOT  
**Estado**: Funcional - Todos los endpoints disponibles

