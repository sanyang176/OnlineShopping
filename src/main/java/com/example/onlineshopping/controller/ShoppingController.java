package com.example.onlineshopping.controller;

import com.example.onlineshopping.entity.ShoppingItem;
import com.example.onlineshopping.service.ShoppingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @RequestMapping("/")
    public String Init() {
        return "index";
    }

    @RequestMapping("/itemInfo/{itemType}")
    public String GetAllShoppingItems(Model model,@PathVariable String itemType) {
        List<ShoppingItem> items =  shoppingService.GetShoppingItems().stream().filter(x -> x.getItemType().equalsIgnoreCase(itemType)).collect(Collectors.toList());
        model.addAttribute("items", items);
        SetPatternAndImagePath(model,"../css/common.css","../");
        return "itemInfo";
    }

    @RequestMapping("/spike/{id}")
    public String SpikeShoppingItems(Model model,@PathVariable int id) {
        shoppingService.SpikeShoppingItems(id);
        SetPatternAndImagePath(model,"../../../css/common.css","../../../");
        return "itemInfo";
    }

    private void SetPatternAndImagePath(Model model, String patternPath, String imagePath) {
        model.addAttribute("cssPath", patternPath);
        model.addAttribute("imgPath", imagePath);
    }
}
