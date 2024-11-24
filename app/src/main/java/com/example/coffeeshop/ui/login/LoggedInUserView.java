package com.example.coffeeshop.ui.login;

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


    LoggedInUserView(String id, String name, String username, String email, String address, String phone) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
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
}
