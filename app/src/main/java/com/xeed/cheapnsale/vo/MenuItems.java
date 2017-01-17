package com.xeed.cheapnsale.vo;

public class MenuItems {
    private int type;
    private String itemName;
    private int itemPrice;
    private String imageSrc;
    private int itemCount;
    private int itemTotalPrice;

    public MenuItems(int type, String itemName, int itemPrice, String imageSrc) {
        this.type = type;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.imageSrc = imageSrc;
    }

    public int getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(int itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public int getType() {
        return type;
    }
}
