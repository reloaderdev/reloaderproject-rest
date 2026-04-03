# Domain: SQL Server

## Driver
- `mssql-jdbc 12.6.1.jre11` (Microsoft oficial)
- Registrado con static initializer en `DatabaseConnection`

## DatabaseConnection

Fuente única de conexión. **No modificar sin pedido explícito.**

```java
// Uso estándar con try-with-resources
try (Connection conn = DatabaseConnection.getConnection()) {
    // ...
}
```

Lee las variables de entorno: `DB_URL`, `DB_USER`, `DB_PASSWORD`

## Stored Procedures — CallableStatement

```java
CallableStatement stmt = conn.prepareCall("{call schema.sp_NombreProcedure(?, ?)}");
stmt.setString(1, param1);
stmt.setString(2, param2);
ResultSet rs = stmt.executeQuery();
```

## Leer ResultSet a JSONObject

```java
JSONObject result = new JSONObject();
if (rs.next()) {
    result.put("campo1", rs.getString("Campo1"));
    result.put("campo2", rs.getInt("Campo2"));
}
```

## Leer ResultSet a JSONArray

```java
JSONArray array = new JSONArray();
while (rs.next()) {
    JSONObject item = new JSONObject();
    item.put("campo1", rs.getString("Campo1"));
    item.put("campo2", rs.getInt("Campo2"));
    array.put(item);
}
```

## Stored procedure actual
- `auth.sp_LoginUser(username, password)` → devuelve `IsAuthenticated` (1 = ok, 0 = fallo)

## Convenciones
- Siempre usar try-with-resources para la conexión
- Los nombres de columnas del ResultSet deben coincidir exactamente con los definidos en el SP
- Los schemas de los SPs se definen en la base de datos, no en Java
