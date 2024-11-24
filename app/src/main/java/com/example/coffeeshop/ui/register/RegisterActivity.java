package com.example.coffeeshop.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeshop.R;
import com.example.coffeeshop.api.ApiClient;
import com.example.coffeeshop.api.ApiService;
import com.example.coffeeshop.data.model.UserResponse;
import com.example.coffeeshop.model.User;
import com.example.coffeeshop.ui.login.LoginActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullNameEditText, usernameEditText, emailEditText, passwordEditText, addressEditText, phoneEditText;
    private Button registerButton;
    private ProgressBar loadingProgressBar;

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
        registerButton.setOnClickListener(v -> registerUser());
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

    private void registerUser() {
        System.out.println("Método registerUser foi chamado!");

        // Capturar valores dos campos
        String fullName = fullNameEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        // Log para depuração
        System.out.println("Dados preenchidos: " +
                "\nFull Name: " + fullName +
                "\nUsername: " + username +
                "\nEmail: " + email +
                "\nPassword: " + password +
                "\nAddress: " + address +
                "\nPhone: " + phone);

        // Validação
        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        loadingProgressBar.setVisibility(View.VISIBLE);

        // Criar instância do User com o novo campo
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        User newUser = new User(fullName, username, email, password, address, phone);

        // Fazer a requisição de registro
        Call<UserResponse> call = apiService.registerUser(newUser);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                loadingProgressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();
                    if (userResponse.isSuccess()) {
                        Toast.makeText(RegisterActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        finish(); // Voltar para a tela anterior
                    } else {
                        Toast.makeText(RegisterActivity.this, "Erro: " + userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        System.out.println("Erro ao registrar: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(RegisterActivity.this, "Erro ao realizar cadastro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                loadingProgressBar.setVisibility(View.GONE);
                System.out.println("Erro de conexão: " + t.getMessage());
                t.printStackTrace();
                Toast.makeText(RegisterActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
