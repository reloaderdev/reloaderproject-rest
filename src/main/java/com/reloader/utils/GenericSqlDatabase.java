package com.reloader.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

public class GenericSqlDatabase {

    public JSONArray obtenerDataJSONArray2(ResultSet rs) throws SQLException {

        JSONArray documentoJsonArray = new JSONArray();

        JSONArray nombredecolumnas = new JSONArray();

        for (int a = 1; a <= rs.getMetaData().getColumnCount(); a++) {

            JSONObject jsoncabecera = new JSONObject();

            jsoncabecera.put("nombrecabecera", rs.getMetaData().getColumnName(a));

            nombredecolumnas.put(jsoncabecera);

        }

        int contador = 1;

        while (rs.next()) {

            JSONObject documentoJSON = new JSONObject();

            documentoJSON.put("contador", contador);

            for (int z = 0; z < nombredecolumnas.length(); z++) {

                JSONObject jsoncolumnas = nombredecolumnas.getJSONObject(z);

                documentoJSON.put(jsoncolumnas.get("nombrecabecera").toString(), rs.getObject(jsoncolumnas.get("nombrecabecera").toString()));

            }

            documentoJsonArray.put(documentoJSON);

            contador++;

        }

        return documentoJsonArray;

    }

    public JSONArray obtenerDataJSONArray(ResultSet rs) throws SQLException {
        JSONArray documentoJsonArray = new JSONArray();
        JSONArray nombredecolumnas = new JSONArray();
        for (int a = 1; a <= rs.getMetaData().getColumnCount(); a++) {
            JSONObject jsoncabecera = new JSONObject();
            jsoncabecera.put("nombrecabecera", rs.getMetaData().getColumnName(a));
            nombredecolumnas.put(jsoncabecera);
        }
        int contador = 1;
        while (rs.next()) {
            JSONObject documentoJSON = new JSONObject();
            documentoJSON.put("contador", contador);
            for (int z = 0; z < nombredecolumnas.length(); z++) {
                JSONObject jsoncolumnas = nombredecolumnas.getJSONObject(z);
                documentoJSON.put(jsoncolumnas.get("nombrecabecera").toString(), rs.getObject(jsoncolumnas.get("nombrecabecera").toString()));
            }
            documentoJsonArray.put(documentoJSON);
            contador++;
        }
        return documentoJsonArray;
    }

    public JSONObject obtenerDataJSON(ResultSet rs) throws SQLException {
        JSONObject documentoJson = new JSONObject();
        JSONArray nombredecolumnas = new JSONArray();
        for (int a = 1; a <= rs.getMetaData().getColumnCount(); a++) {
            JSONObject jsoncabecera = new JSONObject();
            jsoncabecera.put("nombrecabecera", rs.getMetaData().getColumnName(a));
            nombredecolumnas.put(jsoncabecera);
        }
        int contador = 1;
        while (rs.next()) {
            documentoJson.put("contador", contador);
            for (int z = 0; z < nombredecolumnas.length(); z++) {
                JSONObject jsoncolumnas = nombredecolumnas.getJSONObject(z);
                documentoJson.put(jsoncolumnas.get("nombrecabecera").toString(), rs.getObject(jsoncolumnas.get("nombrecabecera").toString()));
            }
            contador++;
        }
        return documentoJson;
    }

    public JsonArray obtenerDataJsonArray(ResultSet rs) throws SQLException {

        JsonArray documentoJsonArray = new JsonArray();
        JsonArray nombredecolumnas = new JsonArray();
        for (int a = 1; a <= rs.getMetaData().getColumnCount(); a++) {
            JsonObject jsoncabecera = new JsonObject();
            jsoncabecera.addProperty("nombrecabecera", rs.getMetaData().getColumnName(a));
            nombredecolumnas.add(jsoncabecera);
        }

        int contador = 1;
        while (rs.next()) {
            JsonObject documentoJSON = new JsonObject();
            documentoJSON.addProperty("contador", contador);
            for (int z = 0; z < nombredecolumnas.size(); z++) {
                JsonObject jsoncolumnas = nombredecolumnas.get(z).getAsJsonObject();
                String columnName = jsoncolumnas.get("nombrecabecera").getAsString();

                Object columnValue = rs.getObject(columnName);
                if (columnValue != null) {
                    documentoJSON.addProperty(columnName, columnValue.toString());
                } else {
                    documentoJSON.addProperty(columnName, "0");
                }
            }
            documentoJsonArray.add(documentoJSON);
            contador++;
        }
        return documentoJsonArray;
    }
}
