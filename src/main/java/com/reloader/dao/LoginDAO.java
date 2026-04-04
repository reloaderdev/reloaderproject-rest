package com.reloader.dao;

import org.json.JSONObject;

public interface LoginDAO {
    JSONObject login(String username, String password) throws Exception;
}
