package com.reloader.services;

import com.reloader.sqlserverdao.LoginSqlServerDAO;
import org.json.JSONObject;

public class LoginService {

    private final LoginSqlServerDAO dao = new LoginSqlServerDAO();

    public JSONObject login(String username, String password) throws Exception {
        return dao.login(username, password);
    }
}
