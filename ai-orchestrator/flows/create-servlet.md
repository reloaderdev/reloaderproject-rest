# Flow: Crear un Servlet nuevo

Seguir este flujo cuando se necesita un servlet Jakarta EE (para formularios web / JSP, no para REST).

## Cuándo usar Servlet vs JAX-RS Resource

| Caso | Usar |
|---|---|
| Formulario HTML → JSP | Servlet |
| API REST (JSON in/out) | JAX-RS Resource |
| Redirect / session management | Servlet |

## Paso 1 — Definir el contrato

```
Ruta:    /ruta
Método:  POST / GET
Input:   form params (request.getParameter)
Output:  redirect a JSP / response directo
```

## Paso 2 — Crear el Servlet

```java
package com.reloader.{paquete};

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/ruta")
public class XxxServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String param = request.getParameter("param");

        try {
            // lógica
            request.getSession().setAttribute("key", value);
            response.sendRedirect("pagina.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
```

## Paso 3 — Commit

Solo cuando el usuario lo pida explícitamente:

```
feat: agrega servlet {nombre}

- XxxServlet.java: maneja POST /ruta, {descripción de qué hace}

Authored-By: Reloader - Resembrink Correa
```
