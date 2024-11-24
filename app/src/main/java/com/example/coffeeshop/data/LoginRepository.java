package com.example.coffeeshop.data;

import com.example.coffeeshop.data.LoginDataSource;
import com.example.coffeeshop.data.model.LoggedInUser;

public class LoginRepository {

    private static volatile LoginRepository instance;
    private LoginDataSource dataSource;

    // Cache do usuário logado
    private LoggedInUser loggedInUser;

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
        dataSource.login(username, password, new LoginDataSource.ResultCallback<LoggedInUser>() {
            @Override
            public void onSuccess(Result<LoggedInUser> result) {
                // Salvar o usuário logado em cache
                if (result instanceof Result.Success) {
                    loggedInUser = ((Result.Success<LoggedInUser>) result).getData();
                }
                callback.onSuccess(result);
            }

            @Override
            public void onError(Exception exception) {
                callback.onError(exception);
            }
        });
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    public void logout() {
        loggedInUser = null;
        // Se necessário, implementar lógica adicional para limpar dados persistidos
    }
}
