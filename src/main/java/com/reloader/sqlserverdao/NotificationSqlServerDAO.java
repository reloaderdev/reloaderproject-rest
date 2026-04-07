package com.reloader.sqlserverdao;

import com.reloader.auth.DatabaseConnection;
import com.reloader.dao.NotificationDAO;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class NotificationSqlServerDAO implements NotificationDAO {

    @Override
    public JSONObject upsertDeviceToken(long userId, String platform, String deviceToken) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call notification.sp_UpsertDeviceToken(?, ?, ?)}")) {

            stmt.setLong(1, userId);
            stmt.setString(2, platform);
            stmt.setString(3, deviceToken);

            try (ResultSet rs = stmt.executeQuery()) {
                JSONObject response = new JSONObject();
                response.put("saved", 0);

                if (rs.next()) {
                    response.put("saved", 1);
                    response.put("deviceTokenId", rs.getLong("DeviceTokenId"));
                    response.put("userId", rs.getLong("UserId"));
                    response.put("platform", rs.getString("Platform"));
                    response.put("deviceToken", rs.getString("DeviceToken"));
                    response.put("isActive", rs.getBoolean("IsActive"));
                    response.put("lastSeenAt", rs.getString("LastSeenAt"));
                    response.put("updatedAt", rs.getString("UpdatedAt"));
                }

                return response;
            }
        }
    }
}
