# Flow: Crear un endpoint REST nuevo

Seguir este flujo cada vez que se agrega un endpoint REST al proyecto.

## Paso 1 — Definir el contrato

Antes de crear cualquier archivo, acordar con el usuario:

```
Endpoint:  POST /webresources/{dominio}/{accion}
Request:   { "campo1": "valor", "campo2": "valor" }
Response:  { "campo": valor }
SP:        schema.sp_NombreProcedure(param1, param2)
```

## Paso 2 — Verificar pom.xml

Confirmar que existen estas dependencias:
- `jakarta.ws.rs-api` 3.1.0 (provided)
- `org.json` 20240303

Si faltan → agregarlas antes de continuar.

## Paso 3 — DAO interface

Crear en `com.reloader.dao`:

```java
package com.reloader.dao;

public interface XxxDAO {
    TipoRetorno metodo(parametros) throws Exception;
}
```

## Paso 4 — SqlServerDAO

Crear en `com.reloader.sqlserverdao`:

```java
package com.reloader.sqlserverdao;

import com.reloader.auth.DatabaseConnection;
import com.reloader.dao.XxxDAO;
import java.sql.*;

public class XxxSqlServerDAO implements XxxDAO {

    @Override
    public TipoRetorno metodo(parametros) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{call schema.sp_Nombre(?, ?)}");
            stmt.setString(1, param1);
            stmt.setString(2, param2);
            ResultSet rs = stmt.executeQuery();
            // procesar y retornar
        }
    }
}
```

## Paso 5 — Service

Crear en `com.reloader.services`:

```java
package com.reloader.services;

import com.reloader.sqlserverdao.XxxSqlServerDAO;

public class XxxService {
    private final XxxSqlServerDAO dao = new XxxSqlServerDAO();

    public TipoRetorno metodo(parametros) throws Exception {
        return dao.metodo(parametros);
    }
}
```

## Paso 6 — Resource JAX-RS

Crear o actualizar en `com.reloader.rest`:

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

## Paso 7 — Verificar ApplicationConfig

Confirmar que `ApplicationConfig.java` existe en `com.reloader.rest`. Si no existe → crearlo.

## Paso 8 — Commit

Solo cuando el usuario lo pida explícitamente. Formato:

```
feat: agrega endpoint {dominio}/{accion}

- XxxDAO.java: interface de acceso a datos
- XxxSqlServerDAO.java: ejecuta {sp_Nombre}
- XxxService.java: intermediario
- XxxResource.java: POST /webresources/{dominio}/{accion}

Authored-By: Reloader - Resembrink Correa
```
