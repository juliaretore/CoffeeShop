package com.example.coffeeshop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Certifique-se de ter um layout chamado activity_splash

        // Aguarda 3 segundos antes de iniciar a próxima atividade
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Troca para a próxima atividade
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Fecha a SplashActivity
            }
        }, 3000); // 3000ms = 3 segundos
    }
}
