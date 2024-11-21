package com.example.coffeeshop.data;

import com.example.coffeeshop.data.LoginDataSource;
import com.example.coffeeshop.data.model.LoggedInUser;

public class LoginRepository {

    private static volatile LoginRepository instance;
    private LoginDataSource dataSource;

    // Singleton pattern
    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            synchronized (LoginRepository.class) {
                if (instance == null) {
                    instance = new LoginRepository(dataSource);
                }
            }
        }
        return instance;
    }

    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void login(String username, String password, LoginDataSource.ResultCallback<LoggedInUser> callback) {
        dataSource.login(username, password, callback);
    }
}
