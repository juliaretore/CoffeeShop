package com.example.coffeeshop.model;

import com.google.gson.annotations.SerializedName;

public class Product {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("description")
        private String description;

        @SerializedName("price")
        private double price;

        @SerializedName("image")
        private String image;

        @SerializedName("category")
        private int category;


        public int getId() {
                return id;
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
