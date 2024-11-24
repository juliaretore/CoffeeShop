package com.example.coffeeshop.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coffeeshop.MainActivity;
import com.example.coffeeshop.R;
import com.example.coffeeshop.ui.login.LoginActivity;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Obter os dados do usuário da MainActivity
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            String userName = mainActivity.getUserFullName();
            String userEmail = mainActivity.getUserEmail();
            String userUsername = mainActivity.getUserName();
            String userPhone = mainActivity.getUserPhone();
            String userAddress = mainActivity.getUserAddress();

            // Preencher as informações no layout
            TextView nameTextView = view.findViewById(R.id.profile_name);
            TextView userTextView = view.findViewById(R.id.profile_username);
            TextView emailTextView = view.findViewById(R.id.profile_email);
            TextView phoneTextView = view.findViewById(R.id.profile_phone);
            TextView addressTextView = view.findViewById(R.id.profile_address);


            nameTextView.setText(userName);
            userTextView.setText(userUsername);
            emailTextView.setText(userEmail);
            phoneTextView.setText(userPhone);
            addressTextView.setText(userAddress);
        }

        // Configurar o botão de logout
        Button logoutButton = view.findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(v -> {
            // Redirecionar para a tela de login
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpar a pilha de atividades
            startActivity(intent);

            // Opcional: exibir uma mensagem
            Toast.makeText(getContext(), "Você saiu da conta", Toast.LENGTH_SHORT).show();
        });
        return view;
    }
}
