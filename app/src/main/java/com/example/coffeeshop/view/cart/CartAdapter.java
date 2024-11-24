package com.example.coffeeshop.view.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshop.R;
import com.example.coffeeshop.model.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private OnCartItemActionListener listener;

    // Construtor
    public CartAdapter(List<CartItem> cartItems, OnCartItemActionListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.productName.setText(cartItem.getProduct().getName());
        holder.productPrice.setText(String.format("R$ %.2f", cartItem.getProduct().getPrice()));
        holder.quantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.totalPrice.setText(String.format("R$ %.2f", cartItem.getTotalPrice()));

        // Botão de aumentar quantidade
        holder.increaseButton.setOnClickListener(v -> listener.onIncreaseQuantity(cartItem));

        // Botão de diminuir quantidade
        holder.decreaseButton.setOnClickListener(v -> listener.onDecreaseQuantity(cartItem));

        // Botão de remover item
        holder.removeButton.setOnClickListener(v -> {
            listener.onRemoveItem(cartItem);
            removeItem(position); // Atualiza o adaptador após a remoção
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // Método para remover um item e atualizar o adaptador
    public void removeItem(int position) {
        cartItems.remove(position);
        notifyItemRemoved(position); // Notifica que um item foi removido
        notifyItemRangeChanged(position, cartItems.size()); // Atualiza as posições dos itens restantes
    }

    // Método para atualizar a lista de itens do carrinho
    public void updateCartItems(List<CartItem> updatedCartItems) {
        this.cartItems = updatedCartItems;
        notifyDataSetChanged(); // Atualiza o adaptador completamente
    }

    // ViewHolder para os itens do carrinho
    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, quantity, totalPrice;
        Button increaseButton, decreaseButton, removeButton;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.cart_item_name);
            productPrice = itemView.findViewById(R.id.cart_item_price);
            quantity = itemView.findViewById(R.id.cart_item_quantity);
            totalPrice = itemView.findViewById(R.id.cart_item_total_price);
            increaseButton = itemView.findViewById(R.id.cart_item_increase);
            decreaseButton = itemView.findViewById(R.id.cart_item_decrease);
            removeButton = itemView.findViewById(R.id.cart_item_remove);
        }
    }

    // Interface para interações no adapter
    public interface OnCartItemActionListener {
        void onIncreaseQuantity(CartItem item);
        void onDecreaseQuantity(CartItem item);
        void onRemoveItem(CartItem item);
    }
}
