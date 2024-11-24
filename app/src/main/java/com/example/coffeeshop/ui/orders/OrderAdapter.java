package com.example.coffeeshop.ui.orders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshop.R;
import com.example.coffeeshop.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orders;

    public OrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.textViewOrderId.setText("Pedido #" + order.getId());
        holder.textViewOrderDate.setText("Data: " + order.getTimestamp());
        holder.textViewOrderTotal.setText(String.format("Total: R$ %.2f", order.getTotalPrice()));
        holder.textViewItemCount.setText("Itens: " + order.getItems().size());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView textViewOrderId, textViewOrderDate, textViewOrderTotal, textViewItemCount;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewOrderDate = itemView.findViewById(R.id.textViewOrderDate);
            textViewOrderTotal = itemView.findViewById(R.id.textViewOrderTotal);
            textViewItemCount = itemView.findViewById(R.id.textViewItemCount);
        }
    }
}
