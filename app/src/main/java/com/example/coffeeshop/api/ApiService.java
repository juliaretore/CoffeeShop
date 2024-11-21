package com.example.coffeeshop.api;
import com.example.coffeeshop.data.model.UserResponse;
import com.example.coffeeshop.model.Product;
import com.example.coffeeshop.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import java.util.List;

public interface ApiService {
    @GET("api/produtos")
    Call<List<Product>> getProducts(@Query("category") int category); // Endpoint com parâmetro de categoria
    @POST("api/register")
    Call<UserResponse> registerUser(@Body User user); // Endpoint para registrar usuários

    @POST("api/login")
    Call<UserResponse> loginUser(@Body User user); // Endpoint para login de usuários

}
