# Skill: Crear Service

Acción atómica para generar un Service.

## Input requerido
- Nombre del dominio (ej: `Login`, `Usuario`)
- Métodos que debe exponer (mismos que el DAO)

## Output — `com.reloader.services.XxxService`

```java
package com.reloader.services;

import com.reloader.sqlserverdao.XxxSqlServerDAO;

public class XxxService {

    private final XxxSqlServerDAO dao = new XxxSqlServerDAO();

    public TipoRetorno metodo(Tipo param1, Tipo param2) throws Exception {
        return dao.metodo(param1, param2);
    }
}
```

## Notas
- El Service instancia directamente `XxxSqlServerDAO` (sin factory, sin DI framework)
- Solo orquesta — no tiene lógica de negocio propia salvo que sea necesario
- Si hay lógica adicional (transformar datos, validar, combinar resultados de múltiples DAOs) va aquí, no en el Resource
- Propagar las excepciones hacia el Resource con `throws Exception`
