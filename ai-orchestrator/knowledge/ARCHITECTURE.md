# Architecture

## Patron general

Arquitectura de 3 capas:

`REST Resource -> Service -> DAO -> DatabaseConnection`

## Stack

- Runtime: Payara 6 / Jakarta EE 10
- Java 17
- REST: JAX-RS
- DB: Azure SQL Server via JDBC
- JSON: `org.json`
- Build: Maven WAR
- Deploy: Docker -> Azure Container Registry -> Azure Web App

## Capas

### `com.reloader.rest.*`

- endpoints REST
- reciben JSON
- llaman al service
- devuelven JSON

### `com.reloader.services.*`

- logica de negocio
- orquestacion entre resource y DAO

### `com.reloader.dao.*`

- contratos DAO

### `com.reloader.sqlserverdao.*`

- implementaciones SQL Server
- ejecutan stored procedures
- transforman result sets a JSON

### `com.reloader.auth.*`

- `DatabaseConnection.java`
- `LoginServlet.java`

### `com.reloader.health.*`

- health check del Web App

## Flujo actual de login

1. `POST /webresources/auth/login`
2. `LoginResource`
3. `LoginService`
4. `LoginSqlServerDAO`
5. `auth.sp_LoginUser`
6. `core.sp_GetPrimaryCharacterContext`

Resultado esperado:

- `isAuthenticated`
- `user`
- `character` cuando exista
- `guild` cuando exista
- `equipment`, `sockets`, `baseStats`, `assignedStats`, `finalStats`

## Estado actual relevante

- el backend ya autentica contra `reloader-games-db`
- `auth.sp_LoginUser` devuelve la sesion base del usuario
- `core.sp_GetPrimaryCharacterContext` devuelve el contexto del personaje
- el login web y REST ya estan remodelados para esta base nueva

## Escalado futuro

La estrategia de crecimiento esta documentada en:

- `knowledge/SCALING_MODEL.md`

El estado puntual de esta jornada esta documentado en:

- `knowledge/STATUS_2026-04-03.md`
