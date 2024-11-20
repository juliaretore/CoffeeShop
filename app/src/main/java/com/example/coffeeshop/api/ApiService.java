package com.example.coffeeshop.api;
import com.example.coffeeshop.model.Product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.List;

public interface ApiService {
    @GET("api/produtos")
    Call<List<Product>> getProducts(@Query("category") int category); // Endpoint com parâmetro de categoria
}
