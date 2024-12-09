package com.example.onlineshopping.mapping;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.onlineshopping.entity.ShoppingItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingItemMapper {

    @DS("master")
    @Select("select * from shoppingitem")
    List<ShoppingItem> GetShoppingItems();

    @DS("master")
    @Update("update shoppingitem set stockSize = #{stock} where id = #{id}")
    void UpdateShoppingItemById(int id, int stock);

    @DS("master")
    @Select("select * from shoppingitem where itemtype = #{type}")
    ShoppingItem GetShoppingItemByType(String type);
}
