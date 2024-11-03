package com.example.onlineshopping.controller;

import com.example.onlineshopping.entity.ShoppingItem;
import com.example.onlineshopping.service.ShoppingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//Add
@Controller
@Slf4j
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @GetMapping("/")
    public String GetAllShoppingItems(Model model) {
        List<ShoppingItem> items =  shoppingService.GetShoppingItems();
        for (var item : items) {
            log.info(item.getId() + "," + item.getItemName() +"," + item.getPrice() +"," + item.getStockSize());
        }
        model.addAttribute("items", items);
        return "index";
    }
}
