package com.example.onlineshopping.service;

import com.example.onlineshopping.mapping.ShoppingItemMapper;
import com.example.onlineshopping.entity.ShoppingItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ShoppingServiceImpl implements ShoppingService
{
    @Autowired
    private ShoppingItemMapper shoppingItemMapper;

    @Override
    public List<ShoppingItem> GetShoppingItems() {
        log.info(this.getClass().getName() + ".GetShoppingItems");
        return shoppingItemMapper.GetShoppingItems();
    }
}
