# Operating Model

## Fuente de verdad

- La única fuente de verdad de la arquitectura de automatización es `ai-orchestrator/`
- El código fuente del proyecto es la fuente de verdad funcional
- Todo debe persistirse en: `knowledge/` (reglas estables), `domains/` (por tecnología), `flows/` (procesos), `skills/` (automatización)
- Prohibido usar memoria interna de Claude como fuente de verdad
- Prohibido duplicar información entre archivos
- Si hay algo que recordar → escribirlo en el `.md` correspondiente dentro del proyecto

## Ejecución

- Claude no debe ejecutar procesos pesados (mvn, docker build, docker run)
- Solo debe preparar comandos, validaciones y pasos
- El usuario ejecuta manualmente o vía pipeline
- El pipeline de GitHub Actions se dispara automáticamente con push a `main`

## Deploy automático

Basta con hacer `git push origin main`. El pipeline hace:
1. `mvn clean package` → genera `reloaderproject.war`
2. `docker build` → imagen tagueada con el run number
3. `docker push` → sube a Azure Container Registry (`reloaderprodacr.azurecr.io`)
4. Deploy a Azure Web App

Secrets requeridos en GitHub: `AZURE_CREDENTIALS`, `ACR_USERNAME`, `ACR_PASSWORD`

## Reglas de interacción

1. Proponer estructura antes de crear archivos o carpetas — no crear sin aprobación
2. Respetar el estilo del usuario (naming, validaciones, patrones)
3. Explicaciones directas al punto — sin relleno
4. Respetar el patrón existente de cada módulo — no cambiarlo sin que se pida
5. No modificar código funcional sin pedido explícito
6. Comunicarse siempre en español; el código en el idioma original
