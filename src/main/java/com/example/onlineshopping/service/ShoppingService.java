package com.example.onlineshopping.service;

import com.example.onlineshopping.entity.ShoppingItem;

import java.util.List;

public interface ShoppingService {
    List<ShoppingItem> GetShoppingItems();
    boolean SpikeShoppingItems(int id);
}
