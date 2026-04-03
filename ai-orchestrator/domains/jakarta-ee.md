# Domain: Jakarta EE

## Versiones
- Jakarta Servlet: 6.0.0
- JAX-RS: 3.1.0 (`jakarta.ws.rs`)
- Runtime: Payara 6 (provee ambas implementaciones — `scope provided`)
- Java: 17

## ApplicationConfig

Un único archivo registra todos los recursos JAX-RS:

```java
package com.reloader.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("webresources")
public class ApplicationConfig extends Application { }
```

URL base resultante: `http://host:8080/reloaderproject/webresources/`

## JAX-RS Resource

```java
@Path("dominio")
public class XxxResource {

    @POST
    @Path("accion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response metodo(String body) { ... }

    @GET
    @Path("accion/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response metodoPorId(@PathParam("id") int id) { ... }
}
```

## Servlet (legado / formularios web)

```java
@WebServlet("/ruta")
public class XxxServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { ... }
}
```

## Imports clave

```java
// JAX-RS
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

// Servlet
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
```

## Diferencias Servlet vs JAX-RS Resource

| | Servlet | JAX-RS Resource |
|---|---|---|
| Uso | Formularios web, JSP | APIs REST (JSON) |
| Ruta | `@WebServlet("/ruta")` | `@Path("ruta")` + `@ApplicationPath` |
| Input | `request.getParameter("x")` | `String body` (JSON) |
| Output | `response.sendRedirect(...)` | `Response.ok(json).build()` |
| Content-Type | HTML | `application/json` |
