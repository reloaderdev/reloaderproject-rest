package com.reloader.sqlserverdao;

import com.reloader.auth.DatabaseConnection;
import com.reloader.dao.CharacterDAO;
import com.reloader.utils.GenericSqlDatabase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class CharacterSqlServerDAO implements CharacterDAO {

    private final GenericSqlDatabase db = new GenericSqlDatabase();

    @Override
    public JSONObject getCharacterScreen(long userId) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call equipment.sp_GetCharacterScreenByUser(?)}")) {

            stmt.setLong(1, userId);
            stmt.execute();

            JSONObject response = new JSONObject();

            // Result set 1: info del personaje
            try (ResultSet rs = stmt.getResultSet()) {
                response.put("character", db.obtenerDataJSONArray(rs));
            }

            // Result set 2: slots equipados + sockets + lapis
            stmt.getMoreResults();
            try (ResultSet rs = stmt.getResultSet()) {
                response.put("equipment", db.obtenerDataJSONArray(rs));
            }

            // Result set 3: catalogo de lapis disponibles
            stmt.getMoreResults();
            try (ResultSet rs = stmt.getResultSet()) {
                response.put("lapisCatalog", db.obtenerDataJSONArray(rs));
            }

            return response;
        }
    }

    @Override
    public JSONObject saveLapisConfig(long characterId, JSONArray config) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call equipment.sp_SaveCharacterLapisConfig(?, ?)}")) {

            stmt.setLong(1, characterId);
            stmt.setString(2, buildXml(config));

            try (ResultSet rs = stmt.executeQuery()) {
                JSONObject response = new JSONObject();
                response.put("saved", 1);
                response.put("sockets", db.obtenerDataJSONArray(rs));
                return response;
            }
        }
    }

    private String buildXml(JSONArray config) {
        StringBuilder xml = new StringBuilder("<config>");
        for (int i = 0; i < config.length(); i++) {
            JSONObject item = config.getJSONObject(i);
            xml.append("<socket>");
            xml.append("<CharacterEquipmentId>").append(item.getLong("CharacterEquipmentId")).append("</CharacterEquipmentId>");
            xml.append("<SocketNumber>").append(item.getInt("SocketNumber")).append("</SocketNumber>");
            if (item.isNull("LapisId") || !item.has("LapisId")) {
                xml.append("<LapisId></LapisId>");
            } else {
                xml.append("<LapisId>").append(item.getLong("LapisId")).append("</LapisId>");
            }
            xml.append("</socket>");
        }
        xml.append("</config>");
        return xml.toString();
    }
}
