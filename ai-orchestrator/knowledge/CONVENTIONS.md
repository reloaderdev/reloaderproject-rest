# Conventions

## Commits
- Firma obligatoria: `Authored-By: Reloader - Resembrink Correa Egoavil`
- Nunca usar `Co-Authored-By: Claude Sonnet 4.6` ni variantes
- No hacer commit ni push automáticamente — solo cuando se pide explícitamente
- Los mensajes de commit siempre en español

### Estructura de commit message

```
tipo: descripción corta del cambio

- archivo o módulo: qué se hizo y por qué
- archivo o módulo: qué se hizo y por qué

Authored-By: Reloader - Resembrink Correa
```

Ejemplo:
```
feat: agrega endpoint REST de login

- LoginResource.java: resource JAX-RS POST /webresources/auth/login
- LoginService.java: intermediario entre resource y DAO
- LoginDAO.java: interface de acceso a datos
- LoginSqlServerDAO.java: ejecuta sp_LoginUser via CallableStatement

Authored-By: Reloader - Resembrink Correa
```

## Git
- Nunca hacer merge, push directo ni ninguna operación sobre main sin instrucción explícita
- Push a main dispara el pipeline de GitHub Actions automáticamente

## Naming

### Clases
| Capa | Convención | Ejemplo |
|---|---|---|
| Resource | `XxxResource` | `LoginResource` |
| Service | `XxxService` | `LoginService` |
| DAO interface | `XxxDAO` | `LoginDAO` |
| DAO implementación | `XxxSqlServerDAO` | `LoginSqlServerDAO` |

### Endpoints REST
- Base path: `/webresources/{dominio}/{accion}`
- Ejemplo: `POST /webresources/auth/login`

### JSON (org.json)
- Parsear request: `new JSONObject(body)`
- Construir response: `new JSONObject()` + `.put("key", value)`
- Devolver como String: `jsonObject.toString()`

## Código

### Resource — patrón estándar
```java
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
            // extraer campos
            // llamar service
            JSONObject response = new JSONObject();
            response.put("key", value);
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

### DAO interface — patrón estándar
```java
public interface XxxDAO {
    TipoRetorno metodo(parametros) throws Exception;
}
```

### SqlServerDAO — patrón estándar
```java
public class XxxSqlServerDAO implements XxxDAO {
    @Override
    public TipoRetorno metodo(parametros) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{call schema.sp_Nombre(?, ?)}");
            stmt.setString(1, param1);
            stmt.setString(2, param2);
            ResultSet rs = stmt.executeQuery();
            // procesar rs
        }
    }
}
```

### Service — patrón estándar
```java
public class XxxService {
    private final XxxSqlServerDAO dao = new XxxSqlServerDAO();

    public TipoRetorno metodo(parametros) throws Exception {
        return dao.metodo(parametros);
    }
}
```

## pom.xml — dependencias base
```xml
<!-- Servlet API — provided por Payara -->
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>6.0.0</version>
    <scope>provided</scope>
</dependency>

<!-- JAX-RS — provided por Payara -->
<dependency>
    <groupId>jakarta.ws.rs</groupId>
    <artifactId>jakarta.ws.rs-api</artifactId>
    <version>3.1.0</version>
    <scope>provided</scope>
</dependency>

<!-- SQL Server JDBC -->
<dependency>
    <groupId>com.microsoft.sqlserver</groupId>
    <artifactId>mssql-jdbc</artifactId>
    <version>12.6.1.jre11</version>
</dependency>

<!-- JSON -->
<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20240303</version>
</dependency>
```
