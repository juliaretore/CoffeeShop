package com.example.coffeeshop.view.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshop.R;
import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.view.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private RecyclerView recyclerViewOrders;
    private OrderAdapter orderAdapter;
    private TextView textViewNoOrders;
    private List<Order> orders;

    @Nullable
    @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_order, container, false);

            recyclerViewOrders = view.findViewById(R.id.recyclerViewOrders);
            textViewNoOrders = view.findViewById(R.id.textViewNoOrders);

            recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));

            // Inicializar a lista de pedidos
            orders = new ArrayList<>();

            // Inicializa o adaptador com a lista de pedidos
            orderAdapter = new OrderAdapter(orders);
            recyclerViewOrders.setAdapter(orderAdapter);

            // Verificar se há pedidos para exibir
            updateOrdersList();

            return view;
        }

        public void updateOrdersList() {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                orders = mainActivity.getUserOrders();  // Atualiza a lista de pedidos
                if (orders != null && !orders.isEmpty()) {
                    textViewNoOrders.setVisibility(View.GONE);
                    recyclerViewOrders.setVisibility(View.VISIBLE);
                    orderAdapter = new OrderAdapter(orders);
                    recyclerViewOrders.setAdapter(orderAdapter);
                } else {
                    recyclerViewOrders.setVisibility(View.GONE);
                    textViewNoOrders.setVisibility(View.VISIBLE);
                }
            }
        }
    }
