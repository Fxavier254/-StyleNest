package com.example.stylenest;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<Product> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItem(String name, String price, String size, String color, Object imageSource, int placeholderRes) {
        cartItems.add(new Product(name, price, size, color, imageSource, placeholderRes));
    }

    public List<Product> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public String getTotal() {
        int total = 0;
        for (Product item : cartItems) {
            try {
                String priceStr = item.price.replace("KES ", "").replace(",", "").trim();
                total += Integer.parseInt(priceStr);
            } catch (Exception e) {
                // ignore
            }
        }
        return "KES " + String.format("%,d", total);
    }

    public static class Product {
        public String name;
        public String price;
        public String size;
        public String color;
        public Object imageSource;
        public int placeholderRes;

        public Product(String name, String price, String size, String color, Object imageSource, int placeholderRes) {
            this.name = name;
            this.price = price;
            this.size = size;
            this.color = color;
            this.imageSource = imageSource;
            this.placeholderRes = placeholderRes;
        }
    }
}