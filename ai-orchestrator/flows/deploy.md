# Flow: Deploy a Azure

## Deploy automático (modo normal)

El deploy a producción se hace con solo:

```bash
git add {archivos}
git commit -m "mensaje"
git push origin main
```

GitHub Actions hace el resto automáticamente:
1. Build WAR (`mvn clean package`)
2. Build Docker image
3. Push a ACR (`reloaderprodacr.azurecr.io/reloader-backend:1.0.<run_number>`)
4. Deploy a Azure Web App

## Verificar que el deploy funcionó

1. Ir a GitHub → Actions → ver el workflow corriendo
2. Esperar que aparezca en verde
3. Hacer `GET /health` en la URL del Web App → debe responder `200 OK`

## Secrets necesarios en GitHub

Si el pipeline falla por autenticación, verificar que estos secrets existen en el repo:
- `AZURE_CREDENTIALS`
- `ACR_USERNAME`
- `ACR_PASSWORD`

## Build local (solo para verificar que compila)

```bash
cd reloaderproject-rest
mvn clean package
```

Output esperado: `BUILD SUCCESS` y `target/reloaderproject.war`

**Claude no ejecuta estos comandos.** Solo los prepara para que el usuario los ejecute.

## Troubleshooting

| Problema | Acción |
|---|---|
| Pipeline falla en docker login | Verificar secrets ACR_USERNAME / ACR_PASSWORD |
| App no levanta en Azure | Ver logs en Azure Web App → Log stream |
| Health check falla | Verificar que HealthServlet responde en /health |
| WAR no se despliega en Payara | Verificar path en Dockerfile: `/opt/payara/appserver/glassfish/domains/domain1/autodeploy/` |
