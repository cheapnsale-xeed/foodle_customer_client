package com.xeed.cheapnsale.vo;

public class MenuItems {
    private int type;
    private String itemName;
    private String itemPrice;
    private String imageSrc;

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public MenuItems(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public MenuItems(int type, String itemName, String itemPrice, String imageSrc) {
        this.type = type;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.imageSrc = imageSrc;
    }

    public MenuItems(){

    }
}
