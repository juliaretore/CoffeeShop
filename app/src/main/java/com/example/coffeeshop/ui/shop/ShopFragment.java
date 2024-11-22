package com.example.coffeeshop.ui.shop;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coffeeshop.data.CartManager;
import com.example.coffeeshop.model.Product;
import com.google.android.material.tabs.TabLayout;
import java.util.List;
import java.util.ArrayList;
import com.example.coffeeshop.api.ApiService;
import com.example.coffeeshop.api.ApiClient;

import com.example.coffeeshop.R;
import androidx.recyclerview.widget.GridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopFragment extends Fragment {
    private RecyclerView itemsRecyclerView;
    private TabLayout tabLayout;
    private List<Product> productList = new ArrayList<>();
    private ProductAdapter productAdapter;
    private int selectedCategory = 0; // 0 = Drinks, 1 = Bebidas, 2 = Conveniência
    private TextView welcomeTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout para este fragmento
        View rootView = inflater.inflate(R.layout.shop_activity, container, false);

        // Inicializa as views do layout
        tabLayout = rootView.findViewById(R.id.tabLayout);
        itemsRecyclerView = rootView.findViewById(R.id.itemsRecyclerView);
        welcomeTextView = rootView.findViewById(R.id.welcome_user);

        // Configura o RecyclerView
        itemsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Inicializa o adaptador com o CartManager
        productAdapter = new ProductAdapter(productList, CartManager.getInstance());
        itemsRecyclerView.setAdapter(productAdapter);

        // Atualiza a mensagem de boas-vindas
        updateWelcomeMessage();

        // Carregar produtos iniciais
        loadProducts();

        // Configura as abas de categorias
        configureTabLayout();

        return rootView;
    }

    private void configureTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Drinks"));
        tabLayout.addTab(tabLayout.newTab().setText("Bebidas"));
        tabLayout.addTab(tabLayout.newTab().setText("Conveniência"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedCategory = tab.getPosition();
                loadProducts(); // Atualiza os produtos conforme a categoria selecionada
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    // Atualiza a mensagem de boas-vindas
    private void updateWelcomeMessage() {
        String userName = getActivity().getIntent().getStringExtra("USER_NAME");

        if (userName != null) {
            welcomeTextView.setText("Bem-vindo, " + userName + "!");
        } else {
            welcomeTextView.setText("Bem-vindo!");
        }
    }

    // Carrega produtos conforme a categoria selecionada
    private void loadProducts() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        // Fazer a requisição de produtos com base na categoria
        Call<List<Product>> call = apiService.getProducts(selectedCategory);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("ShopFragment", "Resposta da API: " + response.body().toString());
                    productList.clear();
                    productList.addAll(response.body());
                    productAdapter.notifyDataSetChanged(); // Atualiza o RecyclerView
                } else {
                    Log.e("ShopFragment", "Erro ao carregar produtos: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("ShopFragment", "Falha na API: " + t.getMessage());
            }
        });
    }
}
