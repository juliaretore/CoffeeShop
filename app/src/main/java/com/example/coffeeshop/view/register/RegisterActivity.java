package com.example.coffeeshop.view.register;

import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.coffeeshop.R;
import com.example.coffeeshop.controller.RegisterController;
import android.content.Intent;
import android.widget.TextView;
import com.example.coffeeshop.view.login.LoginActivity;


public class RegisterActivity extends AppCompatActivity {

    private EditText fullNameEditText, usernameEditText, emailEditText, passwordEditText, addressEditText, phoneEditText;
    private Button registerButton;
    private ProgressBar loadingProgressBar;
    private RegisterController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // Inicializar os campos
        fullNameEditText = findViewById(R.id.register_full_name);
        usernameEditText = findViewById(R.id.register_username);
        emailEditText = findViewById(R.id.register_email);
        passwordEditText = findViewById(R.id.register_password);
        addressEditText = findViewById(R.id.register_address);
        phoneEditText = findViewById(R.id.register_phone);
        registerButton = findViewById(R.id.register_button);
        loadingProgressBar = findViewById(R.id.register_loading);

        // Configurar o botão de registro
        controller = new RegisterController(this);

        registerButton.setOnClickListener(v -> controller.onRegisterClicked(
                fullNameEditText.getText().toString().trim(),
                usernameEditText.getText().toString().trim(),
                emailEditText.getText().toString().trim(),
                passwordEditText.getText().toString().trim(),
                addressEditText.getText().toString().trim(),
                phoneEditText.getText().toString().trim()
        ));
        TextView textViewLogin = findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar para a LoginActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                // Opcional: Finalizar a RegisterActivity para evitar voltar a ela
                finish();
            }
        });
    }

    public void showLoading() {
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        loadingProgressBar.setVisibility(View.GONE);
    }

    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }
}
