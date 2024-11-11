package com.example.onlineshopping.service;

import com.example.onlineshopping.entity.ShoppingItem;
import com.example.onlineshopping.mapping.ShoppingItemMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingServiceTest {

    @InjectMocks
    ShoppingServiceImpl shoppingServiceImpl;

    @Mock
    ShoppingItemMapper shoppingItemMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestGetShoppingItems()
    {
        //Arrange
        Assert.assertNotNull(shoppingServiceImpl);
        Assert.assertNotNull(shoppingItemMapper);
        var shoppingItem = new ShoppingItem();
        shoppingItem.setId(1);
        shoppingItem.setItemName("test");
        shoppingItem.setPrice(2);
        shoppingItem.setStockSize(3);
        var arrayList = new ArrayList<ShoppingItem>();
        arrayList.add(shoppingItem);
        //Mockito.doReturn(arrayList).when(shoppingItemMapper).GetShoppingItems();
        Mockito.when(shoppingItemMapper.GetShoppingItems()).thenReturn(arrayList);

        //Act
        var result = shoppingServiceImpl.GetShoppingItems();

        //Assert
        Assert.assertEquals(result.size(),1);
        Assert.assertEquals(result.get(0).getId(),1);
        Assert.assertEquals(result.get(0).getItemName(),"test");
        Assert.assertEquals(result.get(0).getPrice(),2);
        Assert.assertEquals(result.get(0).getStockSize(),3);
    }
}
