package com.reloader.sqlserverdao;

import com.reloader.auth.DatabaseConnection;
import com.reloader.dao.LoginDAO;

import java.sql.*;

public class LoginSqlServerDAO implements LoginDAO {

    @Override
    public int login(String username, String password) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{call auth.sp_LoginUser(?, ?)}");
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("IsAuthenticated");
            }
            return 0;
        }
    }
}
