package com.example.coffeeshop;

import android.os.Bundle;

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
    private String userName;
    private String userEmail;
    private List<CartItem> cartItems; // lista itens carrinho

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // lista do carrinho
        cartItems = new ArrayList<>();

        // dados do usuário - LoginActivity
        userName = getIntent().getStringExtra("USER_NAME");
        userEmail = getIntent().getStringExtra("USER_EMAIL");

        // BottomNavigationView
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_shop, R.id.navigation_cart, R.id.navigation_order) //  carrinho e pedidos
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    // usuário aos fragmentos
    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    // manipular carrinho
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
