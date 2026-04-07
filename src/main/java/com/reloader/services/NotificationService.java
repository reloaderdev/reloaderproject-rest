package com.reloader.services;

import com.reloader.sqlserverdao.NotificationSqlServerDAO;
import org.json.JSONObject;

public class NotificationService {

    private final NotificationSqlServerDAO dao = new NotificationSqlServerDAO();

    public JSONObject upsertDeviceToken(long userId, String platform, String deviceToken) throws Exception {
        return dao.upsertDeviceToken(userId, platform, deviceToken);
    }
}
