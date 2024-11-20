package com.example.coffeeshop.ui.shop;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.example.coffeeshop.model.Product;
import com.google.android.material.tabs.TabLayout;
import java.util.List;
import java.util.ArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private int selectedCategory = 0; // 0 = Cafés, 1 = Salgados, 2 = Doces

    // Esse método irá ser chamado para criar a view do Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout para este fragmento
        View rootView = inflater.inflate(R.layout.shop_activity, container, false);

        // Inicializa as views do layout
        tabLayout = rootView.findViewById(R.id.tabLayout);
        itemsRecyclerView = rootView.findViewById(R.id.itemsRecyclerView);

        // Configura o RecyclerView
        itemsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Inicializa o adaptador
        productAdapter = new ProductAdapter(productList);
        itemsRecyclerView.setAdapter(productAdapter);

        // Cria a lista de produtos (essa lista deve vir do seu backend)
        loadProducts();

        // Configura as abas de categorias
        tabLayout.addTab(tabLayout.newTab().setText("Cafés"));
        tabLayout.addTab(tabLayout.newTab().setText("Salgados"));
        tabLayout.addTab(tabLayout.newTab().setText("Doces"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedCategory = tab.getPosition();
                loadProducts(); // Filtra os produtos conforme a categoria
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return rootView;
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
                    Log.d("ShopFragment", "Resposta da API: " + response.body().toString()); // Adicionado log
                    productList.clear();
                    productList.addAll(response.body());
                    productAdapter.notifyDataSetChanged(); // Atualizar RecyclerView
                } else {
                    // Lidar com erro na resposta
                    Log.e("ShopFragment", "Erro ao carregar produtos: " + response.message());
                }
            }



            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Lidar com falhas na requisição
                Log.e("ShopFragment", "Falha na API: " + t.getMessage());
            }
        });
    }
}
