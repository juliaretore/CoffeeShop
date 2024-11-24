package com.example.coffeeshop.data.model;

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

    public LoggedInUser(String userId, String name, String username, String email, String address, String phone) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
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
}