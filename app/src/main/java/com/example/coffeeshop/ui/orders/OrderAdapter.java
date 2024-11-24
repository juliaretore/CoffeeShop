package com.example.coffeeshop.ui.orders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coffeeshop.R;
import com.example.coffeeshop.model.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList != null ? orderList : new ArrayList<>();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.textViewOrderTime.setText(order.getTimestamp());
        holder.textViewTotalPrice.setText(
                holder.itemView.getContext().getString(R.string.order_total, order.getTotalPrice())
        );
        holder.textViewItemCount.setText(
                holder.itemView.getContext().getString(R.string.order_items, order.getItems().size())
        );
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void updateOrders(List<Order> newOrders) {
        this.orderList.clear();
        this.orderList.addAll(newOrders);
        notifyDataSetChanged();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOrderTime, textViewTotalPrice, textViewItemCount;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderTime = itemView.findViewById(R.id.textViewOrderTime);
            textViewTotalPrice = itemView.findViewById(R.id.textViewTotalPrice);
            textViewItemCount = itemView.findViewById(R.id.textViewItemCount);
        }
    }
}
