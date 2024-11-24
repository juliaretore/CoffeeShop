package com.example.coffeeshop.view.shop;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.example.coffeeshop.controller.CartManager;
import com.example.coffeeshop.controller.CartController;
import com.example.coffeeshop.model.Product;
import com.google.android.material.tabs.TabLayout;
import com.example.coffeeshop.R;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.coffeeshop.controller.ShopController;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {
    private RecyclerView itemsRecyclerView;
    private TabLayout tabLayout;
    private List<Product> productList = new ArrayList<>();
    private ShopAdapter productAdapter;
    private TextView welcomeTextView;
    private ShopController shopController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout para este fragmento
        View rootView = inflater.inflate(R.layout.shop_activity, container, false);

        shopController = new ShopController(this);

        // Inicializa as views do layout
        tabLayout = rootView.findViewById(R.id.tabLayout);
        itemsRecyclerView = rootView.findViewById(R.id.itemsRecyclerView);
        welcomeTextView = rootView.findViewById(R.id.welcome_user);

        // Configura o RecyclerView
        itemsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Inicializa o adaptador
        productAdapter = new ShopAdapter(productList, CartController.getInstance(), shopController);
        itemsRecyclerView.setAdapter(productAdapter);

        // Inicializa o controller
        shopController = new ShopController(this);

        // Atualiza a mensagem de boas-vindas
        String userName = getActivity().getIntent().getStringExtra("USER_NAME");
        shopController.updateWelcomeMessage(userName);

        // Carregar produtos iniciais
        shopController.loadProducts();

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
                shopController.setSelectedCategory(tab.getPosition()); // Atualiza a categoria
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    // Método para atualizar a lista de produtos na UI
    public void updateProductList(List<Product> products) {
        productList.clear();
        productList.addAll(products);
        productAdapter.notifyDataSetChanged();
    }

    // Método para atualizar a mensagem de boas-vindas
    public void setWelcomeMessage(String message) {
        welcomeTextView.setText(message);
    }
}
