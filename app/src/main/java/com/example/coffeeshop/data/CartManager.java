package com.example.coffeeshop.data;

import com.example.coffeeshop.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItem(CartItem item) {
        for (CartItem existingItem : cartItems) {
            if (existingItem.getProduct().getName().equals(item.getProduct().getName())) {
                // Atualiza a quantidade do item existente
                existingItem.setQuantity(item.getQuantity());
                return;
            }
        }
        // Adiciona como um novo item se não existir
        if (item.getQuantity() > 0) {
            cartItems.add(item);
        }
    }

    public void removeItem(CartItem item) {
        cartItems.removeIf(existingItem ->
                existingItem.getProduct().getName().equals(item.getProduct().getName()));
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems); // Retorna uma cópia da lista para evitar modificações externas
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    public void clearCart() {
        cartItems.clear();
    }
}
