package com.example.onlineshopping.mapping;

import com.example.onlineshopping.entity.ShoppingItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShoppingItemMapper {

    @Select("select * from shoppingitem")
    List<ShoppingItem> GetShoppingItems();
}
