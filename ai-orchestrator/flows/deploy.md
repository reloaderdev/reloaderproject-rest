# Flow: Deploy a Azure

## Estrategia actual

El proyecto usa dos workflows separados:

- `build.yml`
- `deploy.yml`

## Build (sin deploy)

`build.yml` corre en:

- `push` a `main`
- `pull_request`

Hace:

1. checkout
2. setup Java 17
3. `mvn clean package`

No hace:

- docker push
- deploy a Azure

## Deploy (solo cuando se decide)

`deploy.yml` corre solo con tags:

- `deploy-*`

Ejemplo:

```bash
git tag deploy-1.0.16
git push origin deploy-1.0.16
```

Eso dispara:

1. checkout
2. setup Java 17
3. `mvn clean package`
4. login a ACR
5. build Docker
6. push al ACR
7. deploy a Azure Web App

## Beneficio de este modelo

- se puede hacer push frecuente sin tocar Azure
- se puede trabajar por ramas
- se pueden hacer PRs y squash merge
- Azure solo se actualiza cuando el equipo decide publicar

## Flujo recomendado

### Trabajo diario

```bash
git add .
git commit -m "feat: cambio normal"
git push origin mi-rama
```

o merge a `main` cuando ya corresponde.

Eso solo valida build.

### Publicacion

```bash
git checkout main
git pull
git tag deploy-1.0.16
git push origin deploy-1.0.16
```

Eso si despliega a Azure.

## Secrets necesarios

- `AZURE_CREDENTIALS`
- `ACR_USERNAME`
- `ACR_PASSWORD`
- `ACR_LOGIN_SERVER`
- `AZURE_WEBAPP_NAME`

## Referencia de seguimiento

- `knowledge/OPERATING_MODEL.md`
- `knowledge/STATUS_2026-04-03.md`
