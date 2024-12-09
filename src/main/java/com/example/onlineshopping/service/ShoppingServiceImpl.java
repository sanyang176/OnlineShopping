package com.example.onlineshopping.service;

import com.example.onlineshopping.redis.RedisService;
import com.example.onlineshopping.constants.Constants;
import com.example.onlineshopping.mapping.ShoppingItemMapper;
import com.example.onlineshopping.entity.ShoppingItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.onlineshopping.constants.Constants.RedisStockKeyConstant;

@Service
@Slf4j
public class ShoppingServiceImpl implements ShoppingService
{
    private final String ShoppingItemName = "shoppingItemList";

    @Autowired
    private ShoppingItemMapper shoppingItemMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public List<ShoppingItem> GetShoppingItems() {
        var object = redisService.TryGetKeyValue(RedisStockKeyConstant + ShoppingItemName);
        List<ShoppingItem> shoppingItems;
        if(object != null){
            var shoppingList = object instanceof List<?>? (List<ShoppingItem>)object : null;
            shoppingItems = shoppingList == null? shoppingItemMapper.GetShoppingItems() : shoppingList;
        }
        else{
            shoppingItems = shoppingItemMapper.GetShoppingItems();
            redisService.SetKeyValue(RedisStockKeyConstant + ShoppingItemName, shoppingItems);
        }
        return shoppingItems;
    }

    @Override
    public boolean SpikeShoppingItems(int productId) {
        //Add lock
        var lockKey = Constants.RedisLockKeyConstant + productId;
        var requestId = UUID.randomUUID().toString();
        var isLockSuccess = redisService.GetRedisLock(lockKey,requestId,Constants.RedisExpireTime);
        if(isLockSuccess){
            try {
                // 秒杀操作
                var object = redisService.TryGetKeyValue(RedisStockKeyConstant + ShoppingItemName);
                var shoppingList = object instanceof List<?>? (List<ShoppingItem>)object : null;
                var shoppingItemWithProductId = shoppingList.stream().filter(x -> x.getId() == productId).collect(Collectors.toList());
                var stock = shoppingItemWithProductId.get(0).getStockSize();
                if (stock > 0) {
                    var stockBefore = stock;
                    stock--;

                    //延时双删
                    try{
                    redisService.DeleteKeyValue(RedisStockKeyConstant + ShoppingItemName); // 减库存
                    shoppingItemMapper.UpdateShoppingItemById(productId,stock);
                    log.info(String.format("Product %d stock changed success. Stock changes from %d to %d.",productId,stockBefore,stock)); // 发送通知消息
                    Thread.sleep(3000);
                    redisService.DeleteKeyValue(RedisStockKeyConstant + ShoppingItemName);
                    }
                    catch (InterruptedException e){
                        log.error(e.getMessage());
                        return false;
                    }
                }
            }
            catch (Exception e){
                log.error(e.getMessage());
                return false;
            }
            finally {
                // 释放锁操作
                redisService.RemoveRedisLock(lockKey, requestId);
            }
        }

        return true;
    }

    @Override
    public List<ShoppingItem> GetShoppingItemsByType(String type) {
        var shoppingItems = GetShoppingItems();
        return shoppingItems.stream().filter(x -> x.getItemType().equals(type)).collect(Collectors.toList());
    }
}
