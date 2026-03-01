package com.reloader.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class DatabaseConnection {

    static {
        try {
            DriverManager.registerDriver(new SQLServerDriver());
            System.out.println("SQL Server Driver registrado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {

        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        System.out.println("========== VARIABLES DE ENTORNO ==========");
        System.out.println("DB_URL = " + url);
        System.out.println("DB_USER = " + user);
        System.out.println("DB_PASSWORD = " + (password != null ? "********" : null));
        System.out.println("==========================================");

        if (url == null || url.trim().isEmpty()) {
            throw new RuntimeException("DB_URL es null o vacío");
        }

        if (user == null || user.trim().isEmpty()) {
            throw new RuntimeException("DB_USER es null o vacío");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new RuntimeException("DB_PASSWORD es null o vacío");
        }

        return DriverManager.getConnection(url, user, password);
    }
}