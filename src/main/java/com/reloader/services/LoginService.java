package com.reloader.services;

import com.reloader.sqlserverdao.LoginSqlServerDAO;

public class LoginService {

    private final LoginSqlServerDAO dao = new LoginSqlServerDAO();

    public int login(String username, String password) throws Exception {
        return dao.login(username, password);
    }
}
