package com.example.coffeeshop.model;

import com.example.coffeeshop.model.api.ApiService;
import com.example.coffeeshop.model.api.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginDataSource {

    public void login(String username, String password, ResultCallback<User> callback) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        User loginCredentials = new User(null, username, null, password, null, null); // Ajustado para incluir os novos campos

        Call<UserResponse> call = apiService.loginUser(loginCredentials);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                System.out.println("LoginDataSource: Resposta recebida do servidor");
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();

                    if (userResponse.getUser() != null) {
                        User user = userResponse.getUser();
                    }else {
                        System.out.println("LoginDataSource: Resposta não contém usuário.");
                    }
                    if (userResponse.isSuccess()) {
                        // dados completos retornados pelo backend p/ criar o LoggedInUser
                        User user = userResponse.getUser();
                        User loggedInUser = new User(
                                user.getId(),
                                user.getName(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getAddress(),
                                user.getPhone(),
                                user.getOrders()
                        );
                        System.out.println("LoginDataSource: Login bem-sucedido para " + loggedInUser.getName());
                        callback.onSuccess(new Result.Success<>(loggedInUser));
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
