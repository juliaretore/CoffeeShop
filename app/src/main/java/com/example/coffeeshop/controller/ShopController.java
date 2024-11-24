package com.example.coffeeshop.controller;

import android.util.Log;

import com.example.coffeeshop.model.api.ApiClient;
import com.example.coffeeshop.model.api.ApiService;
import com.example.coffeeshop.model.CartItem;
import com.example.coffeeshop.model.Product;
import com.example.coffeeshop.view.shop.ShopFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopController {
    private ShopFragment shopFragment;
    private int selectedCategory = 0; // Default category: Drinks

    public ShopController(ShopFragment fragment) {
        this.shopFragment = fragment;
    }

    // Atualiza os produtos com base na categoria selecionada
    public void loadProducts() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        // Fazer a requisição de produtos com base na categoria
        Call<List<Product>> call = apiService.getProducts(selectedCategory);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("ShopController", "Resposta da API: " + response.body().toString());
                    shopFragment.updateProductList(response.body());
                } else {
                    Log.e("ShopController", "Erro ao carregar produtos: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("ShopController", "Falha na API: " + t.getMessage());
            }
        });
    }

    // Atualiza a categoria selecionada
    public void setSelectedCategory(int category) {
        this.selectedCategory = category;
        loadProducts(); // Atualiza os produtos após a mudança de categoria
    }

    // Atualiza a mensagem de boas-vindas
    public void updateWelcomeMessage(String userName) {
        if (userName != null) {
            shopFragment.setWelcomeMessage("Bem-vindo, " + userName + "!");
        } else {
            shopFragment.setWelcomeMessage("Bem-vindo!");
        }
    }

    // Atualiza o carrinho (adicionando ou removendo itens)
    public void updateCart(Product product, int quantity) {
        CartController cartController = CartController.getInstance();
        if (quantity == 0) {
            cartController.removeItem(new CartItem(product, 0));
        } else {
            cartController.addItem(new CartItem(product, quantity));
        }
        // Aqui podemos chamar um método para atualizar a UI, se necessário
    }

    // Obtém a quantidade de um produto no carrinho
    public int getQuantityFromCart(Product product) {
        CartController cartController = CartController.getInstance();
        for (CartItem item : cartController.getCartItems()) {
            if (item.getProduct().getName().equals(product.getName())) {
                return item.getQuantity();
            }
        }
        return 0;
    }
}
