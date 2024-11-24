package com.example.coffeeshop.model.api;
import com.example.coffeeshop.model.OrderRequest;
import com.example.coffeeshop.model.UserResponse;
import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.model.Product;
import com.example.coffeeshop.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import java.util.List;

public interface ApiService {
    @GET("api/produtos")
    Call<List<Product>> getProducts(@Query("category") int category); // endpoint com parâmetro de categoria
    @POST("api/register")
    Call<UserResponse> registerUser(@Body User user); // endpoint para registrar usuários

    @POST("api/login")
    Call<UserResponse> loginUser(@Body User user); // endpoint para login de usuários

    @POST("api/orders")
    Call<Order> createOrder(@Body OrderRequest orderRequest);


    @GET("api/users/{username}")
    Call<User> getUserDetails(@Path("username") String username);


}
