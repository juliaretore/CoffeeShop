package com.example.coffeeshop.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffeeshop.MainActivity;
import com.example.coffeeshop.R;
import com.example.coffeeshop.databinding.ActivityLoginBinding;
import com.example.coffeeshop.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;
        final TextView registerTextView = binding.textViewRegister;

        // Listener para o botão "Cadastre-se"
        registerTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }

            loadingProgressBar.setVisibility(View.GONE);

            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }

            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString()
                );
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString()
                );
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            loginViewModel.login(
                    usernameEditText.getText().toString(),
                    passwordEditText.getText().toString()
            );
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        // Exibir mensagem de boas-vindas
        String welcome = getString(R.string.welcome) + model.getName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

        // Criar um Intent para iniciar a MainActivity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        // Adicionar informações do usuário ao Intent
        intent.putExtra("USER_ID", model.getId()); // Certifique-se de enviar o ID
        intent.putExtra("USER_NAME", model.getName());
        intent.putExtra("USER_EMAIL", model.getEmail());
        intent.putExtra("USER_USERNAME", model.getUsername());
        intent.putExtra("USER_PHONE", model.getPhone());
        intent.putExtra("USER_ADDRESS", model.getAddress());

        // Logs para depuração
        System.out.println("LoginActivity: Dados enviados para MainActivity:");
        System.out.println("USER_ID: " + model.getId());
        System.out.println("USER_NAME: " + model.getName());
        System.out.println("USER_EMAIL: " + model.getEmail());
        System.out.println("USER_USERNAME: " + model.getUsername());
        System.out.println("USER_PHONE: " + model.getPhone());
        System.out.println("USER_ADDRESS: " + model.getAddress());

        // Garante que a LoginActivity seja removida da pilha
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        // Inicia a MainActivity
        startActivity(intent);

        // Finaliza a LoginActivity
        finish();
    }



    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
