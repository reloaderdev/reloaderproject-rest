# Scaling Model

## Objetivo

Documentar la estrategia de crecimiento esperada para `reloaderproject-rest` sin obligar a implementar toda la complejidad desde el inicio.

Esta guia existe para dejar claro:

- que la Fase 1 actual es intencionalmente simple
- que la arquitectura actual no bloquea el crecimiento
- como deberia escalar el sistema mas adelante

## Estado actual

Hoy el proyecto funciona con una arquitectura simple y valida para Fase 1:

- una aplicacion WAR
- una sola fuente de datos global
- una sola configuracion de conexion basada en:
  - `DB_URL`
  - `DB_USER`
  - `DB_PASSWORD`
- una base principal de juego:
  - `reloader-games-db`

Esto es correcto para:

- login
- registro
- personajes
- equipamiento
- lapis
- stats
- web
- servlets
- servicios REST

## Decision de ingenieria

No sobreingenierizar en Fase 1.

La prioridad es:

- avanzar funcionalmente
- validar negocio
- estabilizar el dominio
- no agregar desgaste tecnico ni emocional innecesario

Por eso, en esta etapa:

- si se permite una sola conexion global
- si se permite una sola base principal por modulo
- si se permite una sola aplicacion con una infraestructura sencilla

## Escalado esperado

Mas adelante, el sistema puede crecer en cuatro dimensiones.

### 1. Escalado por dominio de negocio

Ejemplos:

- `reloader-games`
- `reloader-assistance`
- otros modulos futuros

Regla:

- cada dominio importante puede tener su propia base de datos
- no mezclar dominios muy distintos en una sola base si el negocio se separa naturalmente

### 2. Escalado por entorno

Ejemplos:

- `reloader-games-dev-db`
- `reloader-games-qa-db`
- `reloader-games-prod-db`

Regla:

- separar desarrollo, validacion y produccion
- no usar produccion para pruebas de estructura o datos

### 3. Escalado por modulo dentro del backend

El backend puede crecer por modulos logicos:

- `auth`
- `character`
- `guild`
- `catalog`
- `build`
- `equipment`

Regla:

- separar paquetes por dominio
- evitar mezclar todo en `rest` o `sqlserverdao`

### 4. Escalado por fuente de datos

En una etapa posterior, la aplicacion podria soportar:

- multiples datasources
- multiples bases en el mismo Azure SQL Server logico
- un datasource por modulo o por contexto

Esto todavia no se implementa en Fase 1.

## Estrategia recomendada de evolucion

### Etapa 1

- una sola app
- una sola base principal
- una sola conexion global

### Etapa 2

- misma app
- mas modulos de negocio
- mejor separacion por paquetes
- capa de acceso a datos mas organizada

### Etapa 3

- soporte de multiples bases por dominio o entorno
- proveedor de conexiones o `DataSourceProvider`
- configuracion por modulo y por ambiente

### Etapa 4

- si el negocio crece mucho, separar servicios o aplicaciones por dominio

## Arquitectura objetivo a futuro

Cuando el proyecto crezca, la direccion recomendada es:

- mantener una fuente de verdad por dominio
- mantener una base por negocio cuando el dominio lo justifique
- usar el mismo Azure SQL Server logico cuando administrativamente convenga
- mover el backend hacia una capa de infraestructura mas formal

Ejemplo conceptual:

```text
reloaderproject-rest
├── auth
├── character
├── guild
├── catalog
├── build
├── equipment
└── infrastructure
    └── db
        ├── ConnectionProvider
        ├── DataSourceProvider
        └── DatabaseContext
```

## Regla arquitectonica importante

No implementar multiples datasources solo porque es posible.

Solo hacerlo cuando exista una necesidad real como:

- otro dominio claramente separado
- otro entorno que deba aislarse
- otra base con ciclo de vida propio
- otra aplicacion consumiendo otra fuente de verdad

## Pendientes de crecimiento

### Fotos y assets

Pendiente futuro recomendado:

- usar `Azure Blob Storage` para fotos y assets livianos

Motivacion:

- no guardar imagenes binarias dentro de SQL Server si no es necesario
- usar la base de datos solo para referencias estructuradas
- mantener menor peso en tablas transaccionales
- facilitar carga de fotos de perfil, avatars, iconos de items y recursos visuales

Enfoque recomendado:

- guardar archivos en `Azure Blob Storage`
- guardar en la base solo la referencia:
  - `PhotoUrl`
  - `AvatarUrl`
  - `ItemImageUrl`
  - o `BlobPath`

Casos posibles a futuro:

- foto del usuario
- avatar del personaje
- imagen de item
- imagen de lapis
- insignia o imagen de gremio

Nota:

- para volumenes bajos como unos cientos de MB, esta opcion suele ser razonable y mas limpia que almacenar binarios dentro de la base

## Conclusion

La arquitectura actual es valida para Fase 1 y permite crecer despues.

No es necesario implementar hoy todo el modelo enterprise.

La decision correcta en esta etapa es:

- avanzar con una base estable
- documentar la direccion futura
- y escalar solo cuando el negocio lo exija
