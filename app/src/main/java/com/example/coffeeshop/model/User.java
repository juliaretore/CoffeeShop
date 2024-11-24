package com.example.coffeeshop.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("id") // ID único retornado pelo backend
    private String id;

    @SerializedName("name") // Nome completo do usuário
    private String name;

    @SerializedName("username") // Nome de usuário (@usuario) para login
    private String username;

    @SerializedName("email") // Email do usuário
    private String email;

    @SerializedName("password") // Senha do usuário
    private String password;

    @SerializedName("address") // Endereço do usuário
    private String address;

    @SerializedName("phone") // Telefone do usuário
    private String phone;

    @SerializedName("orders") // Lista de pedidos associados ao usuário
    private List<Order> orders;

    public User() {
        // Construtor vazio necessário para Gson
    }

    // Construtor para criar um novo usuário (sem pedidos/id)
    public User(String name, String username, String email, String password, String address, String phone) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    // Construtor completo, usado ao receber dados do backend
    public User(String userId, String name, String username, String email, String address, String phone, List<Order> orders) {
        this.id = userId;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.orders = orders;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
