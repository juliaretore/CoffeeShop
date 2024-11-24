package com.example.coffeeshop.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.coffeeshop.data.LoginDataSource;
import com.example.coffeeshop.data.LoginRepository;
import com.example.coffeeshop.data.Result;
import com.example.coffeeshop.data.model.LoggedInUser;
import com.example.coffeeshop.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;


    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // Log para verificar o início do processo de login
        System.out.println("LoginViewModel: Chamando login para username: " + username);

        loginRepository.login(username, password, new LoginDataSource.ResultCallback<LoggedInUser>() {
            @Override
            public void onSuccess(Result<LoggedInUser> result) {
                LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();

                // Log para verificar os dados recebidos do repositório
                System.out.println("LoginViewModel: Dados recebidos do LoginRepository:");
                System.out.println("ID: " + data.getUserId());
                System.out.println("Nome: " + data.getName());
                System.out.println("Username: " + data.getUsername());
                System.out.println("Email: " + data.getEmail());
                System.out.println("Address: " + data.getAddress());
                System.out.println("Phone: " + data.getPhone());

                // Atualizar o LiveData com o resultado do login
                loginResult.postValue(new LoginResult(
                        new LoggedInUserView(
                                data.getUserId(),
                                data.getName(),
                                data.getUsername(),
                                data.getEmail(),
                                data.getAddress(),
                                data.getPhone()
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
