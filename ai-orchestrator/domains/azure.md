# Domain: Azure

## Recursos en uso
- **Azure Container Registry (ACR)**: `reloaderprodacr.azurecr.io`
- **Azure Web App**: despliega la imagen Docker del ACR
- **GitHub Actions**: pipeline CI/CD definido en `.github/workflows/deploy.yml`

## Flujo de deploy (automático al hacer push a main)

```
git push origin main
        ↓
GitHub Actions
  1. mvn clean package  →  reloaderproject.war
  2. docker build       →  imagen tagueada
  3. docker push        →  reloaderprodacr.azurecr.io/reloader-backend:1.0.<run_number>
  4. deploy             →  Azure Web App
```

## Tag de imagen
`reloaderprodacr.azurecr.io/reloader-backend:1.0.<run_number>`

## Secrets requeridos en GitHub
| Secret | Descripción |
|---|---|
| `AZURE_CREDENTIALS` | Service principal JSON para autenticación Azure |
| `ACR_USERNAME` | Usuario del Azure Container Registry |
| `ACR_PASSWORD` | Contraseña del Azure Container Registry |

## Health check
- Azure Web App usa `GET /health` para verificar que la app esté viva
- `HealthServlet` responde `200 OK` en esa ruta

## Variables de entorno en producción
Se configuran en Azure Web App → Configuration → Application settings:
- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`
