# Architecture

## Patrón general
Arquitectura de 3 capas: REST Resource → Service → DAO → DatabaseConnection

## Stack
- Runtime: Payara 6 (Jakarta EE 10), Java 17
- REST: JAX-RS 3.1 (`jakarta.ws.rs`)
- DB: Azure SQL Server via JDBC (`mssql-jdbc 12.6.1`)
- JSON: `org.json`
- Build: Maven, WAR packaging
- Deploy: Docker → Azure Container Registry → Azure Web App

## Capas

### `com.reloader.rest.*` — REST Resources (JAX-RS)
- Recibe el JSON del cliente como `String`
- Parsea con `JSONObject`
- Llama al Service
- Devuelve `Response` con JSON string
- Anotaciones: `@Path`, `@POST`, `@GET`, `@Consumes`, `@Produces`

### `com.reloader.services.*` — Services
- Lógica de negocio e intermediario entre Resource y DAO
- Instancia directa del DAO concreto (`new XxxSqlServerDAO()`)
- No conoce JAX-RS ni HTTP

### `com.reloader.dao.*` — Interfaces DAO
- Define el contrato de acceso a datos
- Sin implementación, solo firmas de método

### `com.reloader.sqlserverdao.*` — Implementaciones SQL Server
- Implementa la interface DAO
- Usa `DatabaseConnection.getConnection()`
- Ejecuta stored procedures via `CallableStatement`
- Parsea `ResultSet` a `JSONObject` / `JSONArray`

### `com.reloader.auth.*` — NO TOCAR
- `DatabaseConnection.java` → fuente de conexión, lee env vars `DB_URL`, `DB_USER`, `DB_PASSWORD`
- `LoginServlet.java` → servlet de login web (form POST → JSP)

### `com.reloader.health.*` — NO TOCAR
- `HealthServlet.java` → responde `200 OK` en `GET /health` para Azure health probes

## Flujo de request REST

```
POST /reloaderproject/webresources/{path}
        ↓
XxxResource (@Path, @POST, consume/produce JSON)
        ↓
XxxService (instancia DAO, orquesta)
        ↓
XxxDAO (interface) → XxxSqlServerDAO (stored procedure)
        ↓
DatabaseConnection.getConnection() → Azure SQL Server
```

## ApplicationConfig

```java
@ApplicationPath("webresources")
public class ApplicationConfig extends Application { }
```

Un único `ApplicationConfig` registra todos los recursos JAX-RS automáticamente.

## Convención de paquetes

```
com.reloader
├── rest/           → ApplicationConfig + Resources JAX-RS
├── services/       → Services (lógica de negocio)
├── dao/            → Interfaces DAO
├── sqlserverdao/   → Implementaciones SQL Server
├── auth/           → DatabaseConnection, LoginServlet (no tocar)
└── health/         → HealthServlet (no tocar)
```

## Principios obligatorios
- Un Resource por dominio (LoginResource, UsuarioResource, etc.)
- Un Service por Resource
- Un DAO interface + un SqlServerDAO por Service
- No mezclar lógica de negocio en el Resource
- No mezclar acceso a datos en el Service
