package com.example.coffeeshop.view.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.coffeeshop.controller.LoginController;
import com.example.coffeeshop.model.LoginDataSource;
import com.example.coffeeshop.model.LoginRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor.
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginController.class)) {
            return (T) new LoginController(LoginRepository.getInstance(new LoginDataSource()));
        } else {
            throw new IllegalArgumentException("Classe ViewModel desconhecida: " + modelClass.getName());
        }
    }
}
