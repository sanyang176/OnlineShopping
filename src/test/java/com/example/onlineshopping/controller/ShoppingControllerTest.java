package com.example.onlineshopping.controller;

import com.example.onlineshopping.entity.ShoppingItem;
import com.example.onlineshopping.mapping.ShoppingItemMapper;
import com.example.onlineshopping.service.ShoppingService;
import com.example.onlineshopping.service.ShoppingServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingControllerTest {
    @InjectMocks
    ShoppingController shoppingController;

    @Mock
    ShoppingService shoppingService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestShoppingService()
    {
        //Arrange
        Assert.assertNotNull(shoppingController);
        Assert.assertNotNull(shoppingService);
        var shoppingItem = new ShoppingItem();
        shoppingItem.setId(1);
        shoppingItem.setItemName("test");
        shoppingItem.setPrice(2);
        shoppingItem.setStockSize(3);
        var arrayList = new ArrayList<ShoppingItem>();
        arrayList.add(shoppingItem);
        Mockito.when(shoppingService.GetShoppingItems()).thenReturn(arrayList);

        //Act
        var result = shoppingController.GetAllShoppingItems(new ConcurrentModel());

        //Assert
        Assert.assertEquals(result,"index");
    }
}
