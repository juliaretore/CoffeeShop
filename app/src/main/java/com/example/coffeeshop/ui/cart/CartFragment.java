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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshop.MainActivity;
import com.example.coffeeshop.R;
import com.example.coffeeshop.api.ApiClient;
import com.example.coffeeshop.api.ApiService;
import com.example.coffeeshop.data.CartManager;
import com.example.coffeeshop.data.LoginDataSource;
import com.example.coffeeshop.data.LoginRepository;
import com.example.coffeeshop.data.model.LoggedInUser;
import com.example.coffeeshop.data.model.OrderRequest;
import com.example.coffeeshop.model.CartItem;
import com.example.coffeeshop.model.Order;

import java.util.List;

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

        // atualiza o total inicial
        updateTotal();

        // configura o botão
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
        // validar se o carrinho está vazio
        if (!validateCart()) return;

        // criar a requisição do pedido
        OrderRequest orderRequest = createOrderRequest();

        // enviar o pedido ao backend
        sendOrder(orderRequest);
    }

    private boolean validateCart() {
        if (cartItems.isEmpty()) {
            Toast.makeText(getContext(), "Carrinho vazio", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private OrderRequest createOrderRequest() {
        // Obter a MainActivity
        MainActivity mainActivity = (MainActivity) getActivity();

        // Certifique-se de que a MainActivity não é nula
        if (mainActivity == null) {
            throw new IllegalStateException("MainActivity não está disponível");
        }

        // Obter o e-mail do usuário
        String userEmail = mainActivity.getUserEmail();

        if (userEmail == null || userEmail.isEmpty()) {
            throw new IllegalStateException("E-mail do usuário não está disponível!");
        }

        // Obter o preço total
        double totalPrice = CartManager.getInstance().getTotal();

        // Criar e retornar o pedido
        return new OrderRequest(userEmail, cartItems, totalPrice);
    }


    private void sendOrder(OrderRequest orderRequest) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        apiService.createOrder(orderRequest).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Pedido realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    // Limpar o carrinho
                    CartManager.getInstance().clearCart();
                    cartAdapter.notifyDataSetChanged();
                    updateTotal();
                } else {
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