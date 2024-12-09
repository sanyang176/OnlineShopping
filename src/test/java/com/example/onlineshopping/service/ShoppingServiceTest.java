package com.example.onlineshopping.service;

import com.example.onlineshopping.redis.RedisService;
import com.example.onlineshopping.entity.ShoppingItem;
import com.example.onlineshopping.mapping.ShoppingItemMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class ShoppingServiceTest {

    @InjectMocks
    ShoppingServiceImpl shoppingServiceImpl;

    @Mock(lenient = true)
    ShoppingItemMapper shoppingItemMapper;

    @Mock(lenient = true)
    RedisService redisService;

    @ParameterizedTest
    @ValueSource(booleans = {true,false})
    public void TestGetShoppingItems(boolean isRedisExist)
    {
        //Arrange
        Assertions.assertNotNull(shoppingServiceImpl);
        Assertions.assertNotNull(shoppingItemMapper);
        var shoppingItem = new ShoppingItem();
        shoppingItem.setId(1);
        shoppingItem.setItemName("test");
        shoppingItem.setPrice(2);
        shoppingItem.setStockSize(3);
        shoppingItem.setItemType("123");
        shoppingItem.setPictureName("testPicture");
        var arrayList = new ArrayList<ShoppingItem>();
        arrayList.add(shoppingItem);
        Mockito.when(shoppingItemMapper.GetShoppingItems()).thenReturn(arrayList);
        if(isRedisExist){
            Mockito.when(redisService.TryGetKeyValue(anyString())).thenReturn(arrayList);
        }
        else{
            Mockito.when(redisService.TryGetKeyValue(anyString())).thenReturn(null);
        }

        //Act
        var result = shoppingServiceImpl.GetShoppingItems();

        //Assert
        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result.get(0).getId(),1);
        Assertions.assertEquals(result.get(0).getItemName(),"test");
        Assertions.assertEquals(result.get(0).getPrice(),2);
        Assertions.assertEquals(result.get(0).getStockSize(),3);
        Assertions.assertEquals(result.get(0).getItemType(),"123");
        Assertions.assertEquals(result.get(0).getPictureName(),"testPicture");
    }

    @Test
    public void TestSpikeShoppingItems()
    {
        //Arrange
        Assertions.assertNotNull(shoppingServiceImpl);
        Assertions.assertNotNull(shoppingItemMapper);
        var shoppingItem = new ShoppingItem();
        shoppingItem.setId(1);
        shoppingItem.setItemName("test");
        shoppingItem.setPrice(2);
        shoppingItem.setStockSize(3);
        shoppingItem.setItemType("123");
        shoppingItem.setPictureName("testPicture");
        var arrayList = new ArrayList<ShoppingItem>();
        arrayList.add(shoppingItem);
        Mockito.when(shoppingItemMapper.GetShoppingItems()).thenReturn(arrayList);
        Mockito.when(redisService.GetRedisLock(anyString(),anyString(),anyLong())).thenReturn(true);
        Mockito.when(redisService.TryGetKeyValue(anyString())).thenReturn(arrayList);

        //Act
        var result = shoppingServiceImpl.SpikeShoppingItems(1);

        //Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void TestSpikeShoppingItemsWhenGetFalse()
    {
        //Arrange
        Assertions.assertNotNull(shoppingServiceImpl);
        Assertions.assertNotNull(shoppingItemMapper);
        var shoppingItem = new ShoppingItem();
        shoppingItem.setId(1);
        shoppingItem.setItemName("test");
        shoppingItem.setPrice(2);
        shoppingItem.setStockSize(3);
        shoppingItem.setItemType("123");
        shoppingItem.setPictureName("testPicture");
        var arrayList = new ArrayList<ShoppingItem>();
        arrayList.add(shoppingItem);
        Mockito.when(shoppingItemMapper.GetShoppingItems()).thenReturn(arrayList);
        Mockito.when(redisService.GetRedisLock(anyString(),anyString(),anyLong())).thenReturn(true);

        //Act
        var result = shoppingServiceImpl.SpikeShoppingItems(1);

        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void TestSpikeShoppingItemsWhenDeleteFalse() throws InterruptedException {
        //Arrange
        Assertions.assertNotNull(shoppingServiceImpl);
        Assertions.assertNotNull(shoppingItemMapper);
        var shoppingItem = new ShoppingItem();
        shoppingItem.setId(1);
        shoppingItem.setItemName("test");
        shoppingItem.setPrice(2);
        shoppingItem.setStockSize(3);
        shoppingItem.setItemType("123");
        shoppingItem.setPictureName("testPicture");
        var arrayList = new ArrayList<ShoppingItem>();
        arrayList.add(shoppingItem);
        Mockito.when(shoppingItemMapper.GetShoppingItems()).thenReturn(arrayList);
        Mockito.when(redisService.GetRedisLock(anyString(),anyString(),anyLong())).thenReturn(true);
        Mockito.when(redisService.TryGetKeyValue(anyString())).thenReturn(arrayList);
        Mockito.doThrow(new InterruptedException()).when(redisService).DeleteKeyValue(anyString());

        //Act
        var result = shoppingServiceImpl.SpikeShoppingItems(1);

        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void TestGetShoppingItemsByType()
    {
        //Arrange
        Assertions.assertNotNull(shoppingServiceImpl);
        Assertions.assertNotNull(shoppingItemMapper);
        var shoppingItem = new ShoppingItem();
        shoppingItem.setId(1);
        shoppingItem.setItemName("test");
        shoppingItem.setPrice(2);
        shoppingItem.setStockSize(3);
        shoppingItem.setItemType("123");
        shoppingItem.setPictureName("testPicture");
        var arrayList = new ArrayList<ShoppingItem>();
        arrayList.add(shoppingItem);
        Mockito.when(shoppingItemMapper.GetShoppingItems()).thenReturn(arrayList);
        Mockito.when(redisService.GetRedisLock(anyString(),anyString(),anyLong())).thenReturn(true);
        Mockito.when(redisService.TryGetKeyValue(anyString())).thenReturn(arrayList);

        //Act
        var result = shoppingServiceImpl.GetShoppingItemsByType("123");

        //Assert
        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result.get(0).getId(),1);
        Assertions.assertEquals(result.get(0).getItemName(),"test");
        Assertions.assertEquals(result.get(0).getPrice(),2);
        Assertions.assertEquals(result.get(0).getStockSize(),3);
        Assertions.assertEquals(result.get(0).getItemType(),"123");
        Assertions.assertEquals(result.get(0).getPictureName(),"testPicture");
    }
}
