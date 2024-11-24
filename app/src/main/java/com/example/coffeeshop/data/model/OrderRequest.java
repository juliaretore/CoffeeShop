package com.example.coffeeshop.data.model;

import com.example.coffeeshop.model.CartItem;
import java.util.List;

public class OrderRequest {
    private String userId;
    private List<CartItem> items;
    private double totalPrice;

    public OrderRequest(String userId, List<CartItem> items, double totalPrice) {
        this.userId = userId;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    // Getters e Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
