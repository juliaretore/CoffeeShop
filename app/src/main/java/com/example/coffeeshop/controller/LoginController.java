package com.example.coffeeshop.controller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.coffeeshop.model.LoginDataSource;
import com.example.coffeeshop.model.LoginFormState;
import com.example.coffeeshop.model.LoginRepository;
import com.example.coffeeshop.model.LoginResult;
import com.example.coffeeshop.model.Result;
import com.example.coffeeshop.R;
import com.example.coffeeshop.model.User;

public class LoginController extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;


    public LoginController(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // Log para verificar o início do processo de login
        System.out.println("LoginViewModel: Chamando login para username: " + username);

        loginRepository.login(username, password, new LoginDataSource.ResultCallback<User>() {
            @Override
            public void onSuccess(Result<User> result) {
                User data = ((Result.Success<User>) result).getData();

                // Log para verificar os dados recebidos do repositório
                System.out.println("LoginViewModel: Dados recebidos do LoginRepository:");
                System.out.println("ID: " + data.getId());
                System.out.println("Nome: " + data.getName());
                System.out.println("Username: " + data.getUsername());
                System.out.println("Email: " + data.getEmail());
                System.out.println("Address: " + data.getAddress());
                System.out.println("Phone: " + data.getPhone());

                // Atualizar o LiveData com o resultado do login
                loginResult.postValue(new LoginResult(
                        new User(
                                data.getId(),
                                data.getName(),
                                data.getUsername(),
                                data.getEmail(),
                                data.getAddress(),
                                data.getPhone(),
                                data.getOrders()
                        )
                ));
            }

            @Override
            public void onError(Exception exception) {
                System.out.println("LoginViewModel: Erro no login - " + exception.getMessage());
                loginResult.postValue(new LoginResult(R.string.login_failed));
            }
        });
    }


    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
