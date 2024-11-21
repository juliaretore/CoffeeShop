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
        User loginCredentials = new User(username, null, password, null, null); // Ignorando outros campos para login
        Call<UserResponse> call = apiService.loginUser(loginCredentials);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();
                    if (userResponse.isSuccess()) {
                        LoggedInUser user = new LoggedInUser("user-id-from-response", "user-display-name-from-response");
                        callback.onSuccess(new Result.Success<>(user));
                    } else {
                        callback.onError(new Exception("Login failed: " + userResponse.getMessage()));
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                callback.onError(new Exception("Error logging in", t));
            }
        });
    }

    public interface ResultCallback<T> {
        void onSuccess(Result<T> result);
        void onError(Exception exception);
    }
}
