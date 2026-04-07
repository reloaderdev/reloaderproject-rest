package com.reloader.dao;

import org.json.JSONObject;

public interface NotificationDAO {
    JSONObject upsertDeviceToken(long userId, String platform, String deviceToken) throws Exception;
}
