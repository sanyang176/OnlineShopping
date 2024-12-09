package com.example.onlineshopping.controller;

import com.example.onlineshopping.constants.Constants;
import com.example.onlineshopping.entity.ShoppingItem;
import com.example.onlineshopping.redis.RedisService;
import com.example.onlineshopping.service.LoginService;
import com.example.onlineshopping.service.ShoppingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private LoginService loginService;

    @RequestMapping("/")
    public String Init(Model model)
    {
        var isLogin = redisService.TryGetKeyValue(Constants.RedisLoginKeyConstant);
        model.addAttribute("loginStatus",loginService.SetLoginStatus(isLogin != null));
        SetUserName(model);
        return "index";
    }

    @RequestMapping("/itemInfo/{itemType}")
    public String GetAllShoppingItems(Model model, @PathVariable String itemType) {
        List<ShoppingItem> items = shoppingService.GetShoppingItems().stream().filter(x -> x.getItemType().equalsIgnoreCase(itemType)).collect(Collectors.toList());
        model.addAttribute("items", items);
        SetPatternAndImagePath(model, "../css/common.css", "../");
        var isLogin = redisService.TryGetKeyValue(Constants.RedisLoginKeyConstant);
        model.addAttribute("loginStatus",loginService.SetLoginStatus(isLogin != null));
        SetUserName(model);
        return "itemInfo";
    }

    @RequestMapping("/spike/{id}")
    public String SpikeShoppingItems(Model model, @PathVariable int id) {
        var isLogin = redisService.TryGetKeyValue(Constants.RedisLoginKeyConstant);
        if(isLogin == null) return "login";
        shoppingService.SpikeShoppingItems(id);
        SetPatternAndImagePath(model, "../../../css/common.css", "../../../");
        return "buySuccess";
    }

    @RequestMapping("/itemInfo/search")
    public String SearchItems(Model model, HttpServletRequest request) {
        var searchKey = request.getParameter("searchKey");
        var searchResult = shoppingService.GetShoppingItemsByType(searchKey);
        model.addAttribute("items", searchResult);
        SetPatternAndImagePath(model, "../css/common.css", "../");
        SetUserName(model);
        return "itemInfo";
    }

    private void SetPatternAndImagePath(Model model, String patternPath, String imagePath) {
        model.addAttribute("cssPath", patternPath);
        model.addAttribute("imgPath", imagePath);
    }

    private void SetUserName(Model model) {
        var userName = (String)redisService.TryGetKeyValue(Constants.RedisLoginKeyConstant);
        model.addAttribute("userName", userName == null ? "" : "用户" + userName);
    }
}