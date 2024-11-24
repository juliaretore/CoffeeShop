package com.example.coffeeshop.ui.orders;

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

import com.example.coffeeshop.MainActivity;
import com.example.coffeeshop.R;
import com.example.coffeeshop.model.Order;

import java.util.List;

public class OrderFragment extends Fragment {

    private RecyclerView recyclerViewOrders;
    private OrderAdapter orderAdapter;
    private TextView textViewNoOrders;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar o layout do fragmento
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        // Inicializar os elementos do layout
        recyclerViewOrders = view.findViewById(R.id.recyclerViewOrders);
        textViewNoOrders = view.findViewById(R.id.textViewNoOrders);

        // Configurar o RecyclerView
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obter os pedidos do usuário
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            List<Order> orders = mainActivity.getUserOrders(); // Função que retorna os pedidos
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

        return view;
    }
}
