# Operating Model

## Fuente de verdad

- La unica fuente de verdad de la arquitectura de automatizacion es `ai-orchestrator/`
- El codigo fuente del proyecto es la fuente de verdad funcional
- Todo debe persistirse en:
  - `knowledge/` para reglas estables
  - `domains/` para tecnologia
  - `flows/` para procesos
  - `skills/` para automatizacion
- No depender de memoria conversacional como fuente unica

## Ejecucion

- Preferir cambios pequenos, verificables y documentados
- El despliegue normal puede ser automatico o manual
- Cuando algo deba recordarse para la siguiente jornada, dejarlo en `knowledge/`

## GitHub Actions actual

Se separo el pipeline en dos workflows:

- `build.yml`
- `deploy.yml`

### Build

Corre en:

- `push` a `main`
- `pull_request`

Hace solo validacion:

1. checkout
2. setup Java 17
3. `mvn clean package`

### Deploy

Corre solo cuando se empuja un tag:

- `deploy-*`

Ejemplo:

```bash
git tag deploy-1.0.16
git push origin deploy-1.0.16
```

Eso si hace:

1. build WAR
2. build Docker
3. push a ACR
4. deploy a Azure

Secrets requeridos en GitHub:

- `AZURE_CREDENTIALS`
- `ACR_USERNAME`
- `ACR_PASSWORD`
- `ACR_LOGIN_SERVER`
- `AZURE_WEBAPP_NAME`

## Deploy manual validado

Tambien existe flujo manual validado:

1. `mvn clean package`
2. `docker build`
3. `docker push` al ACR
4. actualizar imagen del App Service
5. reiniciar el Web App si hace falta

Version validada en esta jornada:

- `reloaderprodacr.azurecr.io/reloader-backend:1.0.15`

Base actual del backend:

- `reloader-games-db`

## Reglas de interaccion

1. Explicaciones directas al punto
2. Respetar el estilo del usuario
3. No cambiar patrones sin justificarlo
4. Documentar decisiones importantes en el orquestador

## Referencia de seguimiento

Para retomar manana:

- `knowledge/STATUS_2026-04-03.md`
- `flows/deploy.md`

## Fases del proyecto

- Fase 1:
  - base nueva
  - login
  - integracion backend
  - despliegue base
  - documentacion de arranque
- Fase 2:
  - home centrado en personaje
  - equipamiento
  - stats
  - shell visual del portal
