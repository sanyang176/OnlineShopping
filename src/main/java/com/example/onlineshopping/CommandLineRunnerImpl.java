package com.example.onlineshopping;

import com.example.onlineshopping.redis.RedisService;
import com.example.onlineshopping.constants.Constants;
import com.example.onlineshopping.mapping.ShoppingItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Autowired
    private ShoppingItemMapper shoppingItemMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public void run(String... args) {
        redisService.ClearRedisLock();
        var shoppingItemList = shoppingItemMapper.GetShoppingItems();
        redisService.SetKeyValue(Constants.RedisStockKeyConstant + "shoppingItemList",shoppingItemList);
    }
}
