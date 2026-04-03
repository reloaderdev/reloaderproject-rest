# Skill: Crear JAX-RS Resource

Acción atómica para generar un REST Resource.

## Input requerido
- Dominio (`@Path` de clase): ej. `auth`, `usuario`
- Acción (`@Path` de método): ej. `login`, `perfil`
- Método HTTP: `@POST` / `@GET`
- Campos del JSON de request
- Tipo de retorno del service

## Output — `com.reloader.rest.XxxResource`

```java
package com.reloader.rest;

import com.reloader.services.XxxService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;

@Path("dominio")
public class XxxResource {

    private final XxxService service = new XxxService();

    @POST
    @Path("accion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response metodo(String body) {
        try {
            JSONObject json = new JSONObject(body);
            String campo = json.getString("campo");

            TipoRetorno resultado = service.metodo(campo);

            JSONObject response = new JSONObject();
            response.put("key", resultado);
            return Response.ok(response.toString()).build();

        } catch (Exception e) {
            e.printStackTrace();
            JSONObject error = new JSONObject();
            error.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error.toString()).build();
        }
    }
}
```

## Notas
- El body del request siempre llega como `String` y se parsea con `new JSONObject(body)`
- Siempre envolver en try/catch y retornar `500` con mensaje de error
- No poner lógica de negocio en el Resource — delegar al Service
- `ApplicationConfig` no necesita modificarse (descubre los Resources automáticamente)
