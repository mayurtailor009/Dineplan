package com.dineplan.model;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 20/09/16.
 */
public class TicketOrderItem {

    private int orderId;
    private int ticketId;
    private String departmentName;
    private int menuItemId;
    private String menuItemName;
    private String portionName;
    private float price;
    private int quantity;
    private String note;
    private boolean locked;
    private boolean calculatePrice;
    private boolean increaseInventory;
    private boolean decreaseInventory;
    private int orderNumber;//what is it
    private String  creatingUserName;
    private String  orderCreatedTime;
    private int menuItemPortionId;
    private ArrayList<TicketOrderTax> taxes;
    private ArrayList<TicketOrderTag> orderTags;

    public TicketOrderItem(){
        orderId=0;
        ticketId=0;
        locked=true;
        calculatePrice=true;
        increaseInventory=false;
        decreaseInventory=true;
        orderNumber=0;

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public String getPortionName() {
        return portionName;
    }

    public void setPortionName(String portionName) {
        this.portionName = portionName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isCalculatePrice() {
        return calculatePrice;
    }

    public void setCalculatePrice(boolean calculatePrice) {
        this.calculatePrice = calculatePrice;
    }

    public boolean isIncreaseInventory() {
        return increaseInventory;
    }

    public void setIncreaseInventory(boolean increaseInventory) {
        this.increaseInventory = increaseInventory;
    }

    public boolean isDecreaseInventory() {
        return decreaseInventory;
    }

    public void setDecreaseInventory(boolean decreaseInventory) {
        this.decreaseInventory = decreaseInventory;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCreatingUserName() {
        return creatingUserName;
    }

    public void setCreatingUserName(String creatingUserName) {
        this.creatingUserName = creatingUserName;
    }

    public String getOrderCreatedTime() {
        return orderCreatedTime;
    }

    public void setOrderCreatedTime(String orderCreatedTime) {
        this.orderCreatedTime = orderCreatedTime;
    }

    public int getMenuItemPortionId() {
        return menuItemPortionId;
    }

    public void setMenuItemPortionId(int menuItemPortionId) {
        this.menuItemPortionId = menuItemPortionId;
    }

    public ArrayList<TicketOrderTax> getTaxes() {
        return taxes;
    }

    public void setTaxes(ArrayList<TicketOrderTax> taxes) {
        this.taxes = taxes;
    }

    public ArrayList<TicketOrderTag> getOrderTags() {
        return orderTags;
    }

    public void setOrderTags(ArrayList<TicketOrderTag> orderTags) {
        this.orderTags = orderTags;
    }

    private ArrayList<TicketOrderStates> orderStates;

    public ArrayList<TicketOrderStates> getOrderStates() {
        return orderStates;
    }

    public void setOrderStates(ArrayList<TicketOrderStates> orderStates) {
        this.orderStates = orderStates;
    }


    //    "orderTags" : [{\"OI\":2,\"OK\":\"000010\",\"PR\":2.00,\"Q\":1,\"TN\":\"SAUCE\",\"TV\":\"STRAWBERRY\",\"UI\":1},{\"OI\":2,\"OK\":\"000020\",\"Q\":1,\"TN\":\"SAUCE\",\"TV\":\"PESTO\",\"UI\":1},{\"OI\":2,\"OK\":\"000030\",\"PR\":1.00,\"Q\":1,\"TN\":\"SAUCE\",\"TV\":\"TOMATO\",\"UI\":1}],
    //        "orderStates" : [{\"D\":\"\\\/Date(1474180957331+0530)\\\/\",\"OK\":\"000000\",\"S\":\"Submitted\",\"SN\":\"Status\",\"SV\":\"\",\"U\":1}],

}
