package com.example.coffeeshop.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //private static final String BASE_URL = "https://coffeeshop-wrs1.onrender.com/";
    private static final String BASE_URL = "http://10.0.2.2:8000/"; //para testes e implementacao

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Para converter JSON
                    .build();
        }
        return retrofit;
    }
}


