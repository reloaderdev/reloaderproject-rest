# Domain: Docker

## Imagen base
`payara/server-full:6.2024.3-jdk17`

## Dockerfile actual

```dockerfile
FROM payara/server-full:6.2024.3-jdk17

COPY target/reloaderproject.war /opt/payara/appserver/glassfish/domains/domain1/autodeploy/

EXPOSE 8080
```

## Deploy path
El WAR se copia al directorio de autodeploy de Payara:
`/opt/payara/appserver/glassfish/domains/domain1/autodeploy/`

Payara detecta el WAR automáticamente y lo despliega sin configuración adicional.

## Context path
La app queda disponible en: `http://host:8080/reloaderproject/`

## Variables de entorno requeridas en runtime

| Variable | Descripción |
|---|---|
| `DB_URL` | JDBC connection string de Azure SQL Server |
| `DB_USER` | Usuario de base de datos |
| `DB_PASSWORD` | Contraseña de base de datos |

## Correr localmente

```bash
docker build -t reloaderproject-app .
docker run --name reloader-container -p 8080:8080 \
  -e DB_URL="jdbc:sqlserver://<host>:1433;databaseName=<db>;encrypt=true" \
  -e DB_USER="<user>" \
  -e DB_PASSWORD="<password>" \
  reloaderproject-app
```

## Notas
- Claude no ejecuta docker build ni docker run
- El build de imagen lo hace el pipeline de GitHub Actions
- Ver `domains/azure.md` para el flujo de deploy completo
