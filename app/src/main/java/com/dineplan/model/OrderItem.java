package com.dineplan.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 08/09/16.
 */
public class OrderItem implements Serializable{

    private float price;
    private ArrayList<OrderTag> orderTags;
    private MenuPortion menuPortion;
    private String note;
    private String itemName;
    private String orderType;


    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public MenuPortion getMenuPortion() {
        return menuPortion;
    }

    public void setMenuPortion(MenuPortion menuPortion) {
        this.menuPortion = menuPortion;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ArrayList<OrderTag> getOrderTags() {
        return orderTags;
    }

    public void setOrderTags(ArrayList<OrderTag> orderTags) {
        this.orderTags = orderTags;
    }
}
