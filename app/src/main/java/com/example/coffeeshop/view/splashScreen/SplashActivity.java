package com.example.coffeeshop.view.splashScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeshop.R;
import com.example.coffeeshop.view.main.MainActivity;
import com.example.coffeeshop.view.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // espera 3 segundos antes de redirecionar
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Verifica se o usuário já está autenticado
                SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

                Intent intent;
                if (isLoggedIn) {
                    // Se estiver autenticado, vai para a MainActivity
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    // Caso contrário, vai para a LoginActivity
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }

                startActivity(intent);
                finish(); // fecha a SplashActivity
            }
        }, 3000); // 3 segundos
    }
}
