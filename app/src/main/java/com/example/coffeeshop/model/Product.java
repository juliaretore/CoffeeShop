package com.example.coffeeshop.model;
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String  image;
    private int category;

    // Construtor
    public Product(String name, String description, double price, String  image, int category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public int getCategory() {
        return category;
    }
}
