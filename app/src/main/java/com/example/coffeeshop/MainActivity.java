package com.example.coffeeshop;

import android.os.Bundle;
import android.util.Log;

import com.example.coffeeshop.model.CartItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.coffeeshop.databinding.ActivityMainBinding;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String userId;
    private String userName;
    private String userFullName;
    private String userEmail;
    private List<CartItem> cartItems; // Lista de itens do carrinho

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuração do layout com View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializa a lista do carrinho
        cartItems = new ArrayList<>();

        // Recebe os dados do usuário passados pela LoginActivity
        userId = getIntent().getStringExtra("USER_ID");
        userName = getIntent().getStringExtra("USER_USERNAME");
        userFullName = getIntent().getStringExtra("USER_NAME");
        userEmail = getIntent().getStringExtra("USER_EMAIL");


        // Validação de dados recebidos
        if (userId == null || userFullName == null || userEmail == null || userName == null) {
            Log.e("MainActivity", "Dados do usuário incompletos! Finalizando atividade.");
            finish(); // Finaliza a MainActivity se os dados estiverem incompletos
            return;
        }

        // Configuração do BottomNavigationView
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_shop, R.id.navigation_cart, R.id.navigation_order) // IDs das abas (loja, carrinho, pedidos)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    // Métodos para expor os dados do usuário para os fragmentos
    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    // Métodos para manipular o carrinho
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addItemToCart(CartItem item) {
        cartItems.add(item);
    }

    public void removeItemFromCart(CartItem item) {
        cartItems.remove(item);
    }

    public void clearCart() {
        cartItems.clear();
    }
}
