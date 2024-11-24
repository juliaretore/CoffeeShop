package com.example.coffeeshop.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    @SerializedName("id")
    private String id; // ID único do pedido

    @SerializedName("items")
    private List<OrderItem> items; // Itens do pedido

    @SerializedName("totalPrice")
    private double totalPrice; // Preço total do pedido

    @SerializedName("timestamp")
    private String timestamp; // Hora do pedido (em ISO 8601 ou outro formato)

    public Order(String id, List<OrderItem> items, double totalPrice, String timestamp) {
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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
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

    // Classe interna para representar itens dentro de um pedido
    public static class OrderItem implements Serializable {
        @SerializedName("productId")
        private int productId;

        @SerializedName("quantity")
        private int quantity;

        public OrderItem(int productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        // Getters e Setters
        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
