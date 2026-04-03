package com.reloader.dao;

public interface LoginDAO {
    int login(String username, String password) throws Exception;
}
