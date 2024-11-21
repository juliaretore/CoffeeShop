package com.example.coffeeshop;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.coffeeshop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String userName; // Armazena o nome do usuário
    private String userEmail; // Armazena o email do usuário

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configurar o layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Capturar os dados do usuário enviados pela LoginActivity
        userName = getIntent().getStringExtra("USER_NAME");
        userEmail = getIntent().getStringExtra("USER_EMAIL");

        // Configurar BottomNavigationView
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_shop) //R.id.navigation_cart, R.id.navigation_orders - quando tiver
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    // Métodos para fornecer os dados do usuário aos fragmentos
    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
