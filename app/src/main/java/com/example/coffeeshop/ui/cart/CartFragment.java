package com.example.coffeeshop.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshop.MainActivity;
import com.example.coffeeshop.R;
import com.example.coffeeshop.api.ApiClient;
import com.example.coffeeshop.api.ApiService;
import com.example.coffeeshop.data.CartManager;
import com.example.coffeeshop.data.model.OrderRequest;
import com.example.coffeeshop.model.CartItem;
import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements CartAdapter.OnCartItemActionListener {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView totalTextView;
    private Button checkoutButton;
    private List<CartItem> cartItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        // RecyclerView
        recyclerView = rootView.findViewById(R.id.cart_recycler_view);
        totalTextView = rootView.findViewById(R.id.cart_total_text);
        checkoutButton = rootView.findViewById(R.id.checkout_button);


        // configura o RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // itens do carrinho
        cartItems = CartManager.getInstance().getCartItems();

        // adaptador
        cartAdapter = new CartAdapter(cartItems, this);
        recyclerView.setAdapter(cartAdapter);

        updateTotal();

        checkoutButton.setOnClickListener(v -> performCheckout());

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
    private void performCheckout() {
        // Validação do carrinho
        if (cartItems.isEmpty()) {
            Toast.makeText(getContext(), "Carrinho vazio", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obter dados do usuário a partir da MainActivity
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity == null) {
            Toast.makeText(getContext(), "Erro ao acessar dados do usuário", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = mainActivity.getUserId();
        if (userId == null || userId.isEmpty()) {
            Toast.makeText(getContext(), "Usuário não identificado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criar a lista de itens do pedido
        List<OrderRequest.OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            int productId = cartItem.getProduct().getId(); // Certifique-se que `getId()` existe no Product
            int quantity = cartItem.getQuantity();
            orderItems.add(new OrderRequest.OrderItem(productId, quantity));
        }

        // Criar o pedido
        double totalPrice = CartManager.getInstance().getTotal();
        OrderRequest orderRequest = new OrderRequest(userId, orderItems, totalPrice);

        // Log do pedido
        System.out.println("Pedido sendo enviado:");
        System.out.println("UserId: " + userId);
        System.out.println("Itens: " + cartItems.size());
        System.out.println("Total: " + totalPrice);
        // Enviar pedido ao backend
        sendOrder(orderRequest);
    }


    private void sendOrder(OrderRequest orderRequest) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        apiService.createOrder(orderRequest).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println("Resposta do servidor: Pedido criado com sucesso!");
                    Toast.makeText(getContext(), "Pedido realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    // Limpar carrinho
                    CartManager.getInstance().clearCart();
                    cartAdapter.notifyDataSetChanged();
                    updateTotal();
                } else {
                    System.out.println("Erro na resposta do servidor: " + response.message());
                    Toast.makeText(getContext(), "Erro ao realizar pedido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast.makeText(getContext(), "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}