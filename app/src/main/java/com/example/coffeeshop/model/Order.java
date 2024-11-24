package com.example.coffeeshop.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Order {
    @SerializedName("id")
    private String id; // ID único do pedido

    @SerializedName("items")
    private List<CartItem> items; // Itens do pedido

    @SerializedName("totalPrice")
    private double totalPrice; // Preço total do pedido

    @SerializedName("timestamp")
    private String timestamp; // Hora do pedido (em ISO 8601 ou outro formato)

    public Order(String id, List<CartItem> items, double totalPrice, String timestamp) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
        this.timestamp = timestamp;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
