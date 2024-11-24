package com.example.coffeeshop.controller;

import com.example.coffeeshop.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartController {
    private static CartController instance;
    private List<CartItem> cartItems;

    private CartController() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartController getInstance() {
        if (instance == null) {
            instance = new CartController();
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

    public void updateItemQuantity(CartItem cartItem) {
        // Verifica se o item já existe no carrinho
        for (CartItem item : cartItems) {
            if (item.getProduct().getName().equals(cartItem.getProduct().getName())) {
                // Atualiza a quantidade do item existente
                item.setQuantity(cartItem.getQuantity());
                return;
            }
        }
        // Caso o item não exista, adiciona ao carrinho
        cartItems.add(cartItem);
    }

    public void clearCart() {
        cartItems.clear();
    }
}
