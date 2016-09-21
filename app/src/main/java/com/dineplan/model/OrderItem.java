package com.dineplan.model;

import com.dineplan.utility.Utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 08/09/16.
 */
public class OrderItem implements Serializable{


    public OrderItem(){
        this.orderTime= Utils.getCurrentUtcDate();
        quantity=1;
    }

    private float price;
    private ArrayList<OrderTag> orderTags;
    private MenuPortion menuPortion;
    private String note;
    private String itemName;
    private int itemId;
    private String orderType="FOR HERE";
    private int quantity;
    private ArrayList<Tax> taxes;
    private float taxAmount;
    private String orderTime;
    private Department department;


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public ArrayList<Tax> getTaxes() {
        return taxes;
    }

    public void setTaxes(ArrayList<Tax> taxes) {
        this.taxes = taxes;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

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


    @Override
    public boolean equals(Object obj) {
       OrderItem orderItem=(OrderItem)obj;
       return (itemId==orderItem.itemId && menuPortion.getId()==orderItem.getMenuPortion().getId());
    }
}
