package com.example.coffeeshop.model;

public class LoginRepository {

    private static volatile LoginRepository instance;
    private LoginDataSource dataSource;

    // Cache do usuário logado
    private User loggedInUser;

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

    public void login(String username, String password, LoginDataSource.ResultCallback<User> callback) {
        dataSource.login(username, password, new LoginDataSource.ResultCallback<User>() {
            @Override
            public void onSuccess(Result<User> result) {
                // Salvar o usuário logado em cache
                if (result instanceof Result.Success) {
                    loggedInUser = ((Result.Success<User>) result).getData();
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

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void logout() {
        loggedInUser = null;
    }
}
