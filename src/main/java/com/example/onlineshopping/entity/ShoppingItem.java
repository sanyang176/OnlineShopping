package com.example.onlineshopping.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class ShoppingItem {

    private int id;

    private String itemName;

    private int price;

    private int stockSize;

}
