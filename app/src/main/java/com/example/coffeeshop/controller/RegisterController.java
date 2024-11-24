package com.example.coffeeshop.controller;

import com.example.coffeeshop.view.register.RegisterActivity;
import com.example.coffeeshop.model.api.ApiClient;
import com.example.coffeeshop.model.api.ApiService;
import com.example.coffeeshop.model.UserResponse;
import com.example.coffeeshop.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterController {
    private final RegisterActivity view;

    public RegisterController(RegisterActivity view) {
        this.view = view;
    }

    public void onRegisterClicked(String fullName, String username, String email, String password, String address, String phone) {
        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            view.showError("Preencha todos os campos");
            return;
        }

        view.showLoading();

        // Criar instância do User com os dados fornecidos
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        User newUser = new User(fullName, username, email, password, address, phone);

        // Fazer a requisição de registro
        Call<UserResponse> call = apiService.registerUser(newUser);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                view.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();
                    if (userResponse.isSuccess()) {
                        view.showSuccess("Cadastro realizado com sucesso!");
                    } else {
                        view.showError("Erro: " + userResponse.getMessage());
                    }
                } else {
                    view.showError("Erro ao realizar cadastro");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                view.hideLoading();
                view.showError("Erro de conexão");
            }
        });
    }
}
