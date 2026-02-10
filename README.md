# API de Clasificación de Créditos con WEKA

Una aplicación **Spring Boot** que utiliza **WEKA (Waikato Environment for Knowledge Analysis)** para clasificar solicitudes de crédito mediante un modelo de aprendizaje automático. La aplicación implementa los principios de **Clean Architecture**.

---

## 🎯 Descripción del Proyecto

Este proyecto es una API REST que permite clasificar solicitudes de crédito como **Aprobado** o **Rechazado** basándose en características del solicitante como:
- Edad
- Ingresos
- Antigüedad laboral
- Deudas existentes
- Historial crediticio

La clasificación se realiza utilizando un modelo entrenado con WEKA, que está almacenado en `modelo_credito.model`.

---

## Estructura del Proyecto

```
WekaNode48/
├── src/
│   ├── main/
│   │   ├── java/com/example/credito/
│   │   │   ├── CreditoApplication.java          # Punto de entrada de la aplicación
│   │   │   ├── application/
│   │   │   │   └── ClasificarCreditoUseCase.java   # Lógica de negocio
│   │   │   ├── domain/
│   │   │   │   └── CreditoCaso.java               # Entidad de dominio
│   │   │   ├── infrastructure/
│   │   │   │   └── weka/
│   │   │   │       └── WekaModeloCreditoService.java # Integración con WEKA
│   │   │   └── interfaces/
│   │   │       └── rest/
│   │   │           ├── CreditoController.java     # Endpoints REST
│   │   │           ├── CreditoRequestDTO.java     # DTO de entrada
│   │   │           └── CreditoResponseDTO.java    # DTO de salida
│   │   └── resources/
│   │       ├── application.properties          # Configuración de la app
│   │       ├── modelo_credito.model           # Modelo WEKA entrenado
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

#### Opción A: Usando Maven
```bash
mvn spring-boot:run
```

#### Opción B: Crear JAR y ejecutar
```bash
mvn clean package
java -jar target/credito-0.0.1-SNAPSHOT.jar
```

### 4️⃣ Acceder a la Aplicación
Una vez que veas el mensaje `Application started`, accede a:
- **Frontend**: http://localhost:8080
- **API REST**: http://localhost:8080/api/credito/clasificar

---

##  Uso de la API

### Endpoint Principal: Clasificar Crédito

**URL**: `POST /api/credito/clasificar`

**Headers**:
```
Content-Type: application/json
```

**Request Body**:
```json
{
  "edad": 35,
  "ingresos": 50000,
  "antiguedadLaboral": 5,
  "tieneDeudas": false,
  "historial": "bueno"
}
```

**Response (Éxito - 200)**:
```json
{
  "clasificacion": "Aprobado",
  "probabilidad": 0.85
}
```

### Parámetros de Entrada
| Parámetro | Tipo | Descripción | Ejemplo |
|-----------|------|-------------|---------|
| `edad` | Integer | Edad del solicitante | 35 |
| `ingresos` | Double | Ingresos mensuales/anuales | 50000 |
| `antiguedadLaboral` | Integer | Años en el trabajo actual | 5 |
| `tieneDeudas` | Boolean | ¿Tiene deudas activas? | false |
| `historial` | String | Historial crediticio (bueno/malo/regular) | "bueno" |

---

## Interfaz Web

El proyecto incluye un frontend HTML/CSS/JavaScript ubicado en `src/main/resources/static/`:

- **index.html**: Formulario interactivo para clasificar créditos
- **styles.css**: Estilos visuales
- **app.js**: Lógica de comunicación con la API REST

Accede al frontend en: **http://localhost:8080**

---

## Flujo de la Aplicación

```
1. Usuario ingresa datos en el formulario (Frontend)
   ↓
2. Frontend envía POST a /api/credito/clasificar (API REST)
   ↓
3. CreditoController recibe la solicitud
   ↓
4. ClasificarCreditoUseCase procesa la lógica de negocio
   ↓
5. WekaModeloCreditoService carga el modelo y predice
   ↓
6. Respuesta se envía al Frontend
   ↓
7. Usuario ve el resultado (Aprobado/Rechazado)
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
**Solución**: Verifica que `modelo_credito.model` esté en `src/main/resources/`.

---

## 📝 Notas Importantes

- El modelo WEKA (`modelo_credito.model`) debe estar en el classpath
- La aplicación requiere Java 17+
- CORS está habilitado para permitir requests desde diferentes orígenes
- La respuesta incluye tanto la clasificación como la probabilidad de predicción

---

**Última actualización**: Febrero 10, 2026
