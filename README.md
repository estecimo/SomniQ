# API de Clasificación de Calidad de Sueño con WEKA + Gemini AI

Una aplicación **Spring Boot** que utiliza **WEKA (Waikato Environment for Knowledge Analysis)** para clasificar la calidad del sueño y **Google Gemini AI** para generar explicaciones inteligentes. La aplicación implementa los principios de **Clean Architecture**.

---

## 🎯 Descripción del Proyecto

Este proyecto es una API REST que permite:

1. **Clasificar la calidad del sueño** como **Buena** o **Mala** basándose en características y hábitos del usuario
2. **Generar explicaciones personalizadas** usando Google Gemini AI especializado en Medicina del Sueño

### Características del Usuario
- Percepción subjetiva del sueño
- Duración y latencia del sueño
- Somnolencia diurna
- Frecuencia de uso de medicamentos
- Adicción a internet y hábitos de compra/venta online
- Datos demográficos (sexo)

### Nuevas Funcionalidades (v1.1.0)
✨ **Google Gemini API** - Las explicaciones ahora son generadas por un modelo IA especialista en Medicina del Sueño, proporcioando análisis personalizados basados en los datos específicos del usuario.

---

## Estructura del Proyecto

```
WekaNode48/
├── src/
│   ├── main/
│   │   ├── java/com/example/calidadsueno/
│   │   │   ├── CalidadSuenoApplication.java                 # Punto de entrada
│   │   │   ├── application/
│   │   │   │   └── ClasificarCalidadSuenoUseCase.java       # Lógica de negocio
│   │   │   ├── domain/
│   │   │   │   └── CasoCalidadSueno.java                   # Entidad de dominio
│   │   │   ├── infrastructure/
│   │   │   │   └── weka/
│   │   │   │       ├── WekaModeloCalidadSueno.java         # Integración WEKA
│   │   │   │       └── GeminiService.java                  # Integración Gemini AI ✨
│   │   │   └── interfaces/
│   │   │       └── rest/
│   │   │           ├── CalidadSuenoController.java         # Endpoints REST
│   │   │           ├── CalidadSuenoRequestDTO.java         # DTO clasificación
│   │   │           ├── CalidadSuenoResponseDTO.java        # DTO respuesta
│   │   │           └── ExplicacionRequestDTO.java          # DTO explicación ✨
│   │   └── resources/
│   │       ├── application.properties                       # Configuración
│   │       ├── Ciencias exactas_Unpruned model.model        # Modelo WEKA
│   │       └── static/
│   │           ├── index.html                              # Frontend
│   │           ├── styles.css                              # Estilos
│   │           └── app.js                                  # Lógica frontend
│   └── test/                                                # Tests unitarios
├── pom.xml                                                  # Configuración Maven
└── README.md                                                # Este archivo
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
| **WEKA** | 3.8.6 | Machine Learning - Clasificación |
| **Google Gemini** | 1.5 Flash | IA generativa para explicaciones ✨ |
| **Maven** | Latest | Gestor de dependencias |
| **HTML/CSS/JavaScript** | - | Frontend interactivo |

---

## Prerrequisitos

### Requerimientos Obligatorios
- **Java Development Kit (JDK)**: Versión 17 o superior
- **Maven**: Versión 3.6 o superior

Verifica las instalaciones:
```bash
java -version
# Salida esperada: java version "17..." (o superior)

mvn -version
# Salida esperada: Apache Maven 3.6.0 (o superior)
```

### Requerimientos Opcionales
- **API Key de Google Gemini**: Necesaria para la funcionalidad de explicaciones
  - Obtén tu clave en: [Google AI Studio](https://aistudio.google.com/app/apikey)
  - La primera clave es gratis con cuota generosa

---

## Instalación y Ejecución

### 1️⃣ Descargar el Proyecto
Descarga o clona el repositorio a tu máquina local.

### 2️⃣ Compilar el Proyecto
```bash
mvn clean compile
```

Este comando:
- Limpia compilaciones previas
- Compila el código fuente
- Descarga automáticamente las dependencias

### 3️⃣ Configurar Google Gemini (Opcional pero Recomendado)

Edita `src/main/resources/application.properties`:

```properties
server.port=8080
gemini.api.key=tu_api_key_gemini_aqui
```

### 4️⃣ Ejecutar la Aplicación

```bash
mvn spring-boot:run
```
📡 API Endpoints

### 1️⃣ Clasificar Calidad de Sueño

Predice la calidad del sueño usando el modelo WEKA entrenado.

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

**Response (200)**:
```json
{
  "clasificacion": "Buena"
}
```

### 2️⃣ Generar Explicación con Gemini AI ✨

Genera una explicación personalizada del resultado usando Google Gemini.

**URL**: `POST /api/calidad-sueno/explicar`

**Headers**:
```
Content-Type: application/json
```

**Request Body** (Incluye los 10 parámetros + resultado):
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
  "latencia": "Normal (5-15 minutos)",
  "resultado": "Buena"
}
```

**Response (200)**:
```json
{
  "explicacion": "Tu calidad de sueño se clasifica como Buena. Esto se debe a que mantienes una percepción positiva de tu sueño, duermes una duración normal (6-9 horas), no experimentas somnolencia diurna excesiva y tienes una latencia de sueño normal. Aunque utilizas medicamentos ocasionalmente, tu bajo nivel de adicción a internet y tus hábitos moderados con las compras online no están impactando negativamente tu descanso..."
}
```

### Parámetros de Entrada

| Parámetro | Tipo | Valores Posibles |
|-----------|------|------------------|
| `percepcion` | String | "Buena", "Mala" |
| `frecuenciaMedicacion` | String | "Ninguna vez en el último mes", "Una o dos veces en el último mes", "Una o más veces a la semana", "Diariamente" |
| `duracionSueno` | String | "Corta (< 6h)", "Normal (6-9h)", "Larga (> 9h)" |
| `somnolenciaDiurna` | String | "Normal", "Marginal", "Excesiva" |
| `adiccionInternet` | String | "Sí", "No" |
| `ventaOnline` | String | "Sí", "No" |
| `comprasOnline` | String | "Sí", "No" |
| `sexo` | String | "Hombre", "Mujer" |
| `nivelAdiccion` | String | "Sin adicción", "Leve", "Moderada", "Severa" |
| `latencia` | String | "Patológica (< 5m)", "Normal (5-15m)", "Prolongada (> 15m)" |

---

## 🎬 Flujo de la Aplicación

```
┌─────────────────────────────────────────────────────────────────┐
│                     1. Usuario completa formulario             │
│                           (Frontend)                           │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│            2. POST /api/calidad-sueno/clasificar               │
│                      (CalidadSuenoController)                  │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│             3. ClasificarCalidadSuenoUseCase                    │
│                   (Lógica de negocio)                          │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│            4. WekaModeloCalidadSueno                            │
│               (Carga modelo + predice)                         │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│        5. Resultado: "Buena" o "Mala"                           │
│            (Retorna al Frontend)                               │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼ ✨ NUEVO
┌─────────────────────────────────────────────────────────────────┐
│         6. POST /api/calidad-sueno/explicar                     │
│          (El frontend envía automáticamente)                   │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│              7. GeminiService                                   │
│            (Construye prompt especializado)                    │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│           8. Google Gemini 1.5 Flash API                        │
│        (IA experta en Medicina del Sueño)                      │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│            9. Explicación personalizada                         │
│            (Retorna al Frontend)                               │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│        10. Usuario ve clasificación + explicación               │
│                     en el Frontend                             │
└─────────────────────────────────────────────────────────────────┘
```

---

## 📚 Interfaz Web

El proyecto incluye un frontend moderno ubicado en `src/main/resources/static/`:

- **index.html**: Formulario interactivo con validación
- **styles.css**: Diseño responsivo y atractivo
- **app.js**: Comunicación con la API REST, manejo de respuestas

**Acceso**: http://localhost:8080

### Flujo del Frontend

1. Usuario completa el formulario de 10 campos
2. Frontend valida los datos
3. Envía clasificación al endpoint `/clasificar`
4. Muestra el resultado (Buena/Mala)
5. Automáticamente envía los datos + resultado a `/explicar`
6. Muestra la explicación generada por Gemini
7. Usuario puede completar otro formulario

---

## ⚙️ Configuración

### Archivo: `src/main/resources/application.properties`

```properties
# Puerto del servidor
server.port=8080

# API Key de Google Gemini (necesario para explicaciones)
gemini.api.key=tu_api_key_aqui
```

### Cambiar el Puerto

Si el puerto 8080 está en uso, modifica:

```properties
server.port=9090
```

Luego accede a: http://localhost:9090

### Configurar Gemini API Key

1. Ve a [Google AI Studio](https://aistudio.google.com/app/apikey)
2. Haz clic en "Create API Key"
3. Selecciona tu proyecto y crea la clave
4. Copia la clave y actualiza `application.properties`:

```properties
gemini.api.key=tu_clave_completa_aqui
```

5. Reinicia la aplicación:
```bash
mvn spring-boot:run
```
---

## 🐛 Solución de Problemas

### Error: "Java version X is not supported"
**Solución**: Instala Java 17 o superior desde [oracle.com](https://www.oracle.com/java/technologies/downloads/).
```bash
java -version  # Verifica la versión
```

### Error: "Maven command not found"
**Solución**: 
1. Instala Maven desde [maven.apache.org](https://maven.apache.org/download.cgi)
2. Añade Maven al PATH del sistema
3. Abre una nueva terminal y verifica:
```bash
mvn -version
```

### Error: "Puerto 8080 ya está en uso"
**Solución**: Cambia el puerto en `application.properties`:
```properties
server.port=8081
```

### Error: "No se puede cargar el modelo WEKA"
**Solución**: Verifica que el archivo `Ciencias exactas_Unpruned model.model` esté en:
- `src/main/resources/` (en desarrollo)
- O incluido en el JAR compilado

### Error: Endpoint `/explicar` no funciona
**Solución 1 - API Key no configurada**:
- Obtén tu clave en [Google AI Studio](https://aistudio.google.com/app/apikey)
- Añádela a `application.properties`

**Solución 2 - API Key inválida**:
- Verifica que la clave sea correcta
- Intenta regenerar una nueva clave en Google AI Studio

**Solución 3 - Sin conexión a internet**:
- Verifica tu conexión de Red
- Intenta con `curl` o `Postman` para verificar conectividad

**Solución 4 - Límite de cuota excedido**:
- Google Gemini tiene límites de uso gratuitos
- Consulta tu cuota en [Google Cloud Console](https://console.cloud.google.com/)

---

## 🎓 Información del Modelo WEKA

### Dataset
- **Registros**: 92 estudiantes de Ciencias Exactas e Ingenierías
- **Fuente**: Encuesta estructurada sobre hábitos de sueño
- **Modelo**: J48 (Árbol de decisión)

### Atributos de Entrada
1. Percepción propia del sueño
2. Medicamentos para dormir
3. Duración del sueño
4. Somnolencia durante el día
5. Adicción al Internet (Sí/No)
6. Nivel de adicción a internet
7. Uso de internet para vender
8. Uso de internet para comprar
9. Sexo (Hombre/Mujer)
10. Latencia del sueño (tiempo para dormir)

### Variable de Salida
- **Calidad de Sueño**: Buena / Mala (clasificación binaria)

### Rendimiento
- El modelo fue evaluado con 92 registros
- Utiliza algoritmo J48 (árbol de decisión)
- Optimizado sin pruning para máxima precisión

---

## 📝 Notas Importantes

### Arquitectura y Diseño
- ✅ Arquitectura limpia (Clean Architecture)
- ✅ Separación de responsabilidades por capas
- ✅ CORS habilitado para requests desde cualquier origen
- ✅ Uso de DTOs para transferencia de datos

### WEKA
- El modelo debe estar en el classpath en tiempo de ejecución
- Se carga una única vez al iniciar la aplicación
- Las predicciones son rápidas y determinísticas

### Gemini AI ✨
- Las explicaciones requieren conexión a internet
- Requiere configuración de API Key
- Primera clave gratuita con límites razonables
- El modelo es especialista en Medicina del Sueño
- No emite recomendaciones médicas, solo análisis

### Seguridad
- **⚠️ IMPORTANTE**: Nunca expongas tu API Key de Gemini en repositorios públicos
- Usa variables de entorno en producción
- Considera usar HTTPS en producción
- Valida todas las entradas en el servidor

### Requisitos de Servidor
- Java 17+ instalado
- 256 MB RAM disponible (mínimo)
- Conexión a internet para la funcionalidad Gemini

---

## 📞 Soporte

Si encuentras problemas:

1. Verifica los requisitos previos
2. Revisa los logs en la consola
3. Consulta la sección "Solución de Problemas"
4. Valida que la API Key de Gemini sea correcta

---

**Última actualización**: Febrero 18, 2026  
**Versión**: 1.1.0  
**Estado**: En revisión
