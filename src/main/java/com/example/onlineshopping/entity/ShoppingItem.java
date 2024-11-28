package com.example.onlineshopping.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class ShoppingItem {

    private int Id;

    private String ItemName;

    private int Price;

    private int StockSize;

    private String PictureName;

    private String ItemType;
}
