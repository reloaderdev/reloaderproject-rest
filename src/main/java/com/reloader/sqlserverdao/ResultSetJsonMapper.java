package com.reloader.sqlserverdao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public final class ResultSetJsonMapper {

    private ResultSetJsonMapper() {
    }

    public static JSONArray toJsonArray(ResultSet rs) throws Exception {
        JSONArray array = new JSONArray();
        if (rs == null) {
            return array;
        }

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            JSONObject obj = new JSONObject();
            for (int i = 1; i <= columnCount; i++) {
                obj.put(metaData.getColumnLabel(i), rs.getObject(i));
            }
            array.put(obj);
        }

        return array;
    }
}
