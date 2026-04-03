# Skill: Crear DAO

Acción atómica para generar el par interface + implementación SQL Server.

## Input requerido
- Nombre del dominio (ej: `Login`, `Usuario`)
- Firma del método: nombre, parámetros, tipo de retorno
- Nombre del stored procedure: `schema.sp_Nombre`

## Output

### 1. Interface — `com.reloader.dao.XxxDAO`

```java
package com.reloader.dao;

public interface XxxDAO {
    TipoRetorno metodo(Tipo param1, Tipo param2) throws Exception;
}
```

### 2. Implementación — `com.reloader.sqlserverdao.XxxSqlServerDAO`

```java
package com.reloader.sqlserverdao;

import com.reloader.auth.DatabaseConnection;
import com.reloader.dao.XxxDAO;
import java.sql.*;

public class XxxSqlServerDAO implements XxxDAO {

    @Override
    public TipoRetorno metodo(Tipo param1, Tipo param2) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{call schema.sp_Nombre(?, ?)}");
            stmt.setString(1, param1);
            stmt.setString(2, param2);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("NombreColumna");
            }
            return valorDefault;
        }
    }
}
```

## Notas
- Siempre usar try-with-resources (`try (Connection conn = ...)`)
- Los nombres de columna del `rs.getXxx("...")` deben coincidir exactamente con los del SP
- Si retorna múltiples filas → usar `while (rs.next())` y construir `JSONArray`
- Si retorna una sola fila → usar `if (rs.next())` y construir `JSONObject` o retornar primitivo
