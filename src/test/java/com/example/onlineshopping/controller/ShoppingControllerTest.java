package com.example.onlineshopping.controller;

import com.example.onlineshopping.entity.ShoppingItem;
import com.example.onlineshopping.service.ShoppingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class ShoppingControllerTest {
    @InjectMocks
    ShoppingController shoppingController;

    @Mock
    ShoppingService shoppingService;

    @org.junit.jupiter.api.Test
    public void TestGetAllShoppingItems()
    {
        //Arrange
        Assertions.assertNotNull(shoppingController);
        Assertions.assertNotNull(shoppingService);
        var shoppingItem = new ShoppingItem();
        shoppingItem.setId(1);
        shoppingItem.setItemName("test");
        shoppingItem.setPrice(2);
        shoppingItem.setStockSize(3);
        shoppingItem.setItemType("123");
        shoppingItem.setPictureName("testPicture");
        var arrayList = new ArrayList<ShoppingItem>();
        arrayList.add(shoppingItem);
        Mockito.when(shoppingService.GetShoppingItems()).thenReturn(arrayList);

        //Act
        var result = shoppingController.GetAllShoppingItems(new ConcurrentModel(),"123");

        //Assert
        Assertions.assertEquals(result,"itemInfo");
    }

    @org.junit.jupiter.api.Test
    public void TestInit(){
        //Arrange

        //Act
        var result = shoppingController.Init();

        //Assert
        Assertions.assertEquals(result,"index");
    }

    @org.junit.jupiter.api.Test
    public void TestSpikeShoppingItems(){
        //Arrange

        //Act
        var result = shoppingController.SpikeShoppingItems(new ConcurrentModel(),1);

        //Assert
        Assertions.assertEquals(result,"itemInfo");
    }
}
