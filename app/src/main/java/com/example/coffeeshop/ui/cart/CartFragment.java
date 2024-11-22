package com.example.coffeeshop.ui.cart;

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
import com.example.coffeeshop.data.CartManager;
import com.example.coffeeshop.model.CartItem;

import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.OnCartItemActionListener {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView totalTextView;
    private List<CartItem> cartItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        // Inicializa o RecyclerView e o texto do total
        recyclerView = rootView.findViewById(R.id.cart_recycler_view);
        totalTextView = rootView.findViewById(R.id.cart_total_text);

        // Configura o RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtém os itens do carrinho
        cartItems = CartManager.getInstance().getCartItems();

        // Inicializa o adaptador
        cartAdapter = new CartAdapter(cartItems, this);
        recyclerView.setAdapter(cartAdapter);

        // Atualiza o total inicial
        updateTotal();

        return rootView;
    }

    @Override
    public void onIncreaseQuantity(CartItem item) {
        item.setQuantity(item.getQuantity() + 1);
        cartAdapter.notifyDataSetChanged();
        updateTotal();
    }

    @Override
    public void onDecreaseQuantity(CartItem item) {
        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
        } else {
            CartManager.getInstance().removeItem(item);
        }
        cartAdapter.notifyDataSetChanged();
        updateTotal();
    }

    @Override
    public void onRemoveItem(CartItem item) {
        CartManager.getInstance().removeItem(item);
        cartAdapter.notifyDataSetChanged();
        updateTotal();
    }

    private void updateTotal() {
        double total = CartManager.getInstance().getTotal();
        totalTextView.setText(String.format("Total: R$ %.2f", total));
    }
}
