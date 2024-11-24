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
        User loginCredentials = new User(null, username, null, password, null, null); // Ajustado para incluir os novos campos

        // log para verificar os dados enviados - apagar depois
        System.out.println("LoginDataSource:Enviando dados de login para o servidor");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        Call<UserResponse> call = apiService.loginUser(loginCredentials);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                System.out.println("LoginDataSource: Resposta recebida do servidor");
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();
                    System.out.println("Resposta do servidor recebida:");
                    System.out.println("Success: " + userResponse.isSuccess());
                    System.out.println("Message: " + userResponse.getMessage());

                    if (userResponse.getUser() != null) {
                        User user = userResponse.getUser();
                        // Log dos dados do usuário retornado pelo servidor
                        System.out.println("dados do usuário retornado pelo servidor:");
                        System.out.println("ID do usuário: " + user.getId());
                        System.out.println("Nome do usuário: " + user.getName());
                        System.out.println("Username: " + user.getUsername());
                        System.out.println("Email: " + user.getEmail());
                        System.out.println("Address: " + user.getAddress());
                        System.out.println("Phone: " + user.getPhone());
                    }else {
                        System.out.println("LoginDataSource: Resposta não contém usuário.");
                    }
                    if (userResponse.isSuccess()) {
                        // dados completos retornados pelo backend p/ criar o LoggedInUser
                        User user = userResponse.getUser();
                        // Log dos dados que serão usados para criar o LoggedInUser
                        System.out.println("Criando LoggedInUser com os seguintes dados:");
                        System.out.println("ID: " + user.getId());
                        System.out.println("Name: " + user.getName());
                        System.out.println("Username: " + user.getUsername());
                        System.out.println("Email: " + user.getEmail());
                        System.out.println("Address: " + user.getAddress());
                        System.out.println("Phone: " + user.getPhone());
                        System.out.println("Orders: " + user.getOrders());

                        LoggedInUser loggedInUser = new LoggedInUser(
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
