package com.example.coffeeshop.data;

import com.example.coffeeshop.api.ApiService;
import com.example.coffeeshop.api.ApiClient;
import com.example.coffeeshop.data.Result;
import com.example.coffeeshop.data.model.LoggedInUser;
import com.example.coffeeshop.data.model.UserResponse;
import com.example.coffeeshop.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginDataSource {

    public void login(String username, String password, ResultCallback<LoggedInUser> callback) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        User loginCredentials = new User(username, null, password, null, null);

        // Log para verificar os dados enviados
        System.out.println("LoginDataSource: Enviando dados de login");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        Call<UserResponse> call = apiService.loginUser(loginCredentials);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();
                    if (userResponse.isSuccess()) {
                        LoggedInUser user = new LoggedInUser("user-id", userResponse.getUser().getUsername());
                        System.out.println("LoginDataSource: Login bem-sucedido para " + user.getDisplayName());
                        callback.onSuccess(new Result.Success<>(user));
                    } else {
                        System.out.println("LoginDataSource: Falha no login - " + userResponse.getMessage());
                        callback.onError(new Exception(userResponse.getMessage()));
                    }
                } else {
                    System.out.println("LoginDataSource: Resposta do servidor não foi bem-sucedida");
                    callback.onError(new Exception("Resposta do servidor inválida"));
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                System.out.println("LoginDataSource: Falha de conexão - " + t.getMessage());
                callback.onError(new Exception("Erro de conexão", t));
            }
        });
    }


    public interface ResultCallback<T> {
        void onSuccess(Result<T> result);
        void onError(Exception exception);
    }
}
