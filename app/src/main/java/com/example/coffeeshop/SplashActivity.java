package com.example.coffeeshop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // espera 3 segundos antes de abrir o app
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // abre o app
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // fecha a tela de "carregamento"
            }
        }, 3000);
    }
}
