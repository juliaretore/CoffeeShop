package com.example.coffeeshop.ui.login;

import com.example.coffeeshop.model.Order;

import java.util.List;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String id;
    private String name;
    private String username;
    private String email;
    private String address;
    private String phone;
    private List<Order> orders;


    LoggedInUserView(String id, String name, String username, String email, String address, String phone, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.orders = orders;
    }

    String getId(){
        return id;
    }
    String getName() {
        return name;
    }

    String getUsername(){
        return username;
    }
    String getEmail() {
        return email;
    }

    String getAddress() {
        return address;
    }

    String getPhone() {
        return phone;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}

