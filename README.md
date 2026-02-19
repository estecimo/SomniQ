# API de Clasificación de Calidad de Sueño con WEKA

Una aplicación **Spring Boot** que utiliza **WEKA (Waikato Environment for Knowledge Analysis)** para clasificar la calidad del sueño mediante un modelo de aprendizaje automático. La aplicación implementa los principios de **Clean Architecture**.

---

## 🎯 Descripción del Proyecto

Este proyecto es una API REST que permite clasificar la calidad del sueño como **Buena** o **Mala** basándose en características y hábitos del usuario como:
- Percepción subjetiva del sueño
- Duración y latencia del sueño
- Somnolencia diurna
- Frecuencia de uso de medicamentos
- Adicción a internet y hábitos de compra online
- Datos demográficos (sexo)

La clasificación se realiza utilizando un modelo entrenado con WEKA, que está almacenado en `Ciencias exactas_Unpruned model.model`.

---

## Estructura del Proyecto

```
WekaNode48/
├── src/
│   ├── main/
│   │   ├── java/com/example/calidadsueno/
│   │   │   ├── CalidadSuenoApplication.java          # Punto de entrada de la aplicación
│   │   │   ├── application/
│   │   │   │   └── ClasificarCalidadSuenoUseCase.java   # Lógica de negocio
│   │   │   ├── domain/
│   │   │   │   └── CasoCalidadSueno.java               # Entidad de dominio
│   │   │   ├── infrastructure/
│   │   │   │   └── weka/
│   │   │   │       └── WekaModeloCalidadSueno.java # Integración con WEKA
│   │   │   └── interfaces/
│   │   │       └── rest/
│   │   │           ├── CalidadSuenoController.java     # Endpoints REST
│   │   │           ├── CalidadSuenoRequestDTO.java     # DTO de entrada
│   │   │           └── CalidadSuenoResponseDTO.java    # DTO de salida
│   │   └── resources/
│   │       ├── application.properties          # Configuración de la app
│   │       ├── Ciencias exactas_Unpruned model.model  # Modelo WEKA entrenado
│   │       └── static/
│   │           ├── index.html                 # Frontend
│   │           ├── styles.css                 # Estilos
│   │           └── app.js                     # Lógica del frontend
│   └── test/                                   # Tests unitarios
├── pom.xml                                      # Configuración Maven
└── README.md                                    # Este archivo
```

### Arquitectura por Capas

- **Interfaces (REST)**: Controladores y DTOs para la comunicación HTTP
- **Application**: Casos de uso (lógica de aplicación)
- **Domain**: Entidades y lógica de negocio central
- **Infrastructure**: Implementación de servicios externos (WEKA)

---

## Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|-------------|---------|----------|
| **Java** | 17 | Lenguaje de programación |
| **Spring Boot** | 3.2.2 | Framework web |
| **WEKA** | 3.8.6 | Machine Learning |
| **Maven** | Latest | Gestor de dependencias |
| **HTML/CSS/JavaScript** | - | Frontend |

---

## Prerrequisitos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

### Requerimientos Obligatorios
- **Java Development Kit (JDK)**: Versión 17 o superior
  - Verifica la instalación: `java -version`
- **Maven**: Versión 3.6 o superior
  - Verifica la instalación: `mvn -version`

### Verificación Rápida
```bash
java -version
# Salida esperada: java version "17..." (o superior)

mvn -version
# Salida esperada: Apache Maven 3.6.0 (o superior)
```

---

## Instalación y Ejecución

### 1️⃣ Clonar o Descargar el Proyecto
```
```

### 2️⃣ Compilar el Proyecto
```bash
mvn clean compile
```

Este comando:
- Limpia compilaciones previas (`clean`)
- Compila el código fuente (`compile`)
- Descarga automáticamente las dependencias necesarias

### 3️⃣ Ejecutar la Aplicación

#### Usando Maven
```bash
mvn spring-boot:run
```

### 4️⃣ Acceder a la Aplicación
Una vez que veas el mensaje `Application started`, accede a:
- **Frontend**: http://localhost:8080

---

##  Uso de la API

### Endpoint Principal: Clasificar Calidad de Sueño

**URL**: `POST /api/calidad-sueno/clasificar`

**Headers**:
```
Content-Type: application/json
```

**Request Body**:
```json
{
  "percepcion": "Buena",
  "frecuenciaMedicacion": "Una o dos veces en el último mes",
  "duracionSueno": "Normal (6-9 horas)",
  "somnolenciaDiurna": "Normal",
  "adiccionInternet": "No",
  "ventaOnline": "No",
  "comprasOnline": "Sí",
  "sexo": "Hombre",
  "nivelAdiccion": "Sin adicción",
  "latencia": "Normal (5-15 minutos)"
}
```

**Response (Éxito - 200)**:
```json
{
  "clasificacion": "Buena"
}
```

### Parámetros de Entrada
| Parámetro | Tipo | Descripción | Ejemplo |
|-----------|------|-------------|---------|
| `percepcion` | String | Percepción subjetiva del sueño | "Buena" o "Mala" |
| `frecuenciaMedicacion` | String | Frecuencia de medicamentos para dormir | "Ninguna vez en el último mes" |
| `duracionSueno` | String | Duración del sueño | "Corta (< 6h)", "Normal (6-9h)", "Larga (> 9h)" |
| `somnolenciaDiurna` | String | Nivel de somnolencia diurna | "Normal", "Excesiva", "Marginal" |
| `adiccionInternet` | String | ¿Es adicto a internet? | "Sí" o "No" |
| `ventaOnline` | String | ¿Realiza ventas online? | "Sí" o "No" |
| `comprasOnline` | String | ¿Realiza compras online? | "Sí" o "No" |
| `sexo` | String | Género del usuario | "Hombre" o "Mujer" |
| `nivelAdiccion` | String | Nivel de adicción a internet | "Sin adicción", "Leve", "Moderada", "Severa" |
| `latencia` | String | Latencia del sueño (tiempo para dormir) | "Patológica (< 5m)", "Normal (5-15m)", "Prolongada (> 15m)" |

---

## Interfaz Web

El proyecto incluye un frontend HTML/CSS/JavaScript ubicado en `src/main/resources/static/`:

- **index.html**: Formulario interactivo para clasificar calidad de sueño
- **styles.css**: Estilos visuales
- **app.js**: Lógica de comunicación con la API REST

Accede al frontend en: **http://localhost:8080**

---

## Flujo de la Aplicación

```
1. Usuario ingresa datos en el formulario (Frontend)
   ↓
2. Frontend envía POST a /api/calidadsueno/clasificar (API REST)
   ↓
3. CalidadSuenoController recibe la solicitud
   ↓
4. ClasificarCalidadSuenoUseCase procesa la lógica de negocio
   ↓
5. WekaModeloCalidadSueno carga el modelo y predice
   ↓
6. Respuesta se envía al Frontend
   ↓
7. Usuario ve el resultado (Buena/Mala)
```

---

## ⚙️ Configuración

El archivo `src/main/resources/application.properties` contiene las configuraciones:

```properties
server.port=8080
```

### Cambiar Puerto de la Aplicación
Si necesitas cambiar el puerto (ej: 9090):

```properties
server.port=9090
```

Luego accede a: http://localhost:9090

---

## 🐛 Solución de Problemas

### Error: "Java version X is not supported"
**Solución**: Instala Java 17 o superior. Verifica con `java -version`.

### Error: Maven no encontrado
**Solución**: Instala Maven y añádelo al PATH del sistema. Luego reinicia la terminal.

### Error: Puerto 8080 en uso
**Solución**: Cambia el puerto en `application.properties`:
```properties
server.port=8081
```

### El modelo WEKA no se carga
**Solución**: Verifica que `Ciencias exactas_Unpruned model.model` esté en `src/main/resources/`.
 
---

## 📝 Notas Importantes

- El modelo WEKA (`Ciencias exactas_Unpruned model.model`) debe estar en el classpath
- La aplicación requiere Java 17+
- CORS está habilitado para permitir requests desde diferentes orígenes
- La respuesta incluye tanto la clasificación de calidad del sueño como la probabilidad de predicción
- El modelo utiliza características relacionadas con hábitos de sueño, salud mental e internet para realizar la clasificación

---

**Última actualización**: Febrero 17, 2026
