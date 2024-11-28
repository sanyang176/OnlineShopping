package com.example.onlineshopping.mapping;

import com.example.onlineshopping.entity.ShoppingItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingItemMapper {

    @Select("select * from shoppingitem")
    List<ShoppingItem> GetShoppingItems();

    @Select("select * from shoppingitem where id = #{id}")
    ShoppingItem GetShoppingItemById(int id);

    @Update("update shoppingitem set stockSize = #{stock} where id = #{id}")
    void UpdateShoppingItemById(int id, int stock);
}
