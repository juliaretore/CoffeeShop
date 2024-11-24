package com.example.coffeeshop.data.model;

import com.example.coffeeshop.model.Order;

import java.util.List;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String name;
    private String username; // @usuario
    private String email;
    private String address;
    private String phone;
    private List<Order> orders;

    public LoggedInUser(String userId, String name, String username, String email, String address, String phone, List<Order> orders) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.orders = orders;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public List<Order> getOrders() {
        return orders;
    }

    // Setter para a lista de pedidos (se necessário)
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}