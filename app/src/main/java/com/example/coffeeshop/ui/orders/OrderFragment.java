package com.example.coffeeshop.ui.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coffeeshop.R;
import com.example.coffeeshop.model.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private RecyclerView recyclerViewOrders;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla o layout do fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        // Inicializa o RecyclerView
        recyclerViewOrders = view.findViewById(R.id.recyclerViewOrders);

        // Simulação de pedidos (substituir com dados reais ou API)
        orderList = new ArrayList<>();
        // Exemplo de pedido fictício
        // orderList.add(new Order("1", items, 50.0, "22/11/2024 14:30"));

        // Configura o Adapter
        orderAdapter = new OrderAdapter(orderList);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewOrders.setAdapter(orderAdapter);

        return view;
    }
}
