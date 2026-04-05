# Conventions

## Git

- no hacer commit ni push automatico sin pedido explicito
- no tocar `main` sin instruccion del usuario

## Naming Java

### Clases

- Resource: `XxxResource`
- Service: `XxxService`
- DAO interface: `XxxDAO`
- DAO SQL Server: `XxxSqlServerDAO`

### Endpoints REST

- base: `/webresources/{dominio}/{accion}`
- ejemplo: `POST /webresources/auth/login`

## JSON

- parsear request con `new JSONObject(body)`
- construir response con `JSONObject` y `JSONArray`
- devolver `toString()`

## Login

- el login no debe quedarse solo en `isAuthenticated`
- debe devolver al menos la sesion base del usuario
- el contexto del personaje puede venir:
  - dentro del mismo login
  - o en un endpoint separado si el payload crece demasiado

## Stored procedures

- SP separado por defecto
- usar `@CONDICION` solo cuando realmente aporte
- agrupar por schema de dominio

## Referencia actual

Para continuar con el mismo criterio manana:

- `knowledge/STATUS_2026-04-03.md`

## Regla de fases

- cerrar cada fase con documentacion de handoff
- dejar inventario tecnico antes de abrir la siguiente fase
- no abrir una fase visual nueva sin preservar el estado funcional anterior
