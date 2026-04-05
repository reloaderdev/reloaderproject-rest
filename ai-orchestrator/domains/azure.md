# Domain: Azure

## Recursos en uso

- Azure SQL logical server:
  - `reloader-sql-server.database.windows.net`
- Azure SQL Database activa:
  - `reloader-games-db`
- Azure Container Registry:
  - `reloaderprodacr.azurecr.io`
- Azure Web App:
  - `reloadersystem`
- Resource Group:
  - `rg-reloader-app`

## Flujo de deploy actual

### Build normal

Se ejecuta con:

- `push` a `main`
- `pull_request`

Workflow:

- `build.yml`

Hace:

1. checkout
2. setup Java 17
3. `mvn clean package`

No despliega Azure.

### Deploy automatizado

Se ejecuta solo con tags:

- `deploy-*`

Ejemplo:

```bash
git tag deploy-1.0.16
git push origin deploy-1.0.16
```

Workflow:

- `deploy.yml`

Hace:

1. build WAR
2. build Docker
3. push ACR
4. deploy Azure Web App

## Imagen publicada

Ejemplo de imagen validada manualmente:

- `reloaderprodacr.azurecr.io/reloader-backend:1.0.15`

En el deploy por tags, el `IMAGE_TAG` sale del nombre del tag.

## Variables de entorno productivas

Se configuran en:

- Azure Web App
  - `reloadersystem`
  - `Configuration -> Application settings`

Variables:

- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`

## Base actual del backend

Valor esperado en `DB_URL`:

```text
jdbc:sqlserver://reloader-sql-server.database.windows.net:1433;database=reloader-games-db;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;
```

## Health check

- `GET /health`

## Pendientes futuros

- conectar `reloader.dev` a Azure App Service
- mantener `azurewebsites.net` como respaldo inicial
- evaluar Blob Storage para assets visuales
