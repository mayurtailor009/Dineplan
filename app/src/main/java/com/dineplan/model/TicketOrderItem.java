package com.dineplan.model;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 20/09/16.
 */
public class TicketOrderItem {


    //public int? Id { get; set; }
    //public decimal CostPrice { get; set; }
    //public int PortionCount { get; set; }
    //public string PriceTag { get; set; }
    //public string Taxes { get; set; }
    //public string OrderTags { get; set; }

    //public List<OrderTagValue> OTags{ get; set; }
    // public string OrderStates { get; set; }


    // public bool IsPromotionOrder { get; set; }
    //public decimal PromotionAmount { get; set; }

    private int Location_Id;

    private int OrderId;
    private int TicketId;
    private String DepartmentName;
    private int MenuItemId;
    private String MenuItemName;
    private String PortionName;
    private float Price;
    private int Quantity;
    private String Note;
    private boolean Locked;
    private boolean CalculatePrice;
    private boolean IncreaseInventory;
    private boolean DecreaseInventory;
    private int OrderNumber;//what is it
    private String  CreatingUserName;
    private String  OrderCreatedTime;
    private int MenuItemPortionId;
    private ArrayList<TicketOrderTax> TTaxes;
    private ArrayList<TicketOrderTag> OTags;

    private ArrayList<TicketOrderStates> OStates;


    public int getLocation_Id() {
        return Location_Id;
    }

    public void setLocation_Id(int location_Id) {
        Location_Id = location_Id;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getTicketId() {
        return TicketId;
    }

    public void setTicketId(int ticketId) {
        TicketId = ticketId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public int getMenuItemId() {
        return MenuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        MenuItemId = menuItemId;
    }

    public String getMenuItemName() {
        return MenuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        MenuItemName = menuItemName;
    }

    public String getPortionName() {
        return PortionName;
    }

    public void setPortionName(String portionName) {
        PortionName = portionName;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public boolean isLocked() {
        return Locked;
    }

    public void setLocked(boolean locked) {
        Locked = locked;
    }

    public boolean isCalculatePrice() {
        return CalculatePrice;
    }

    public void setCalculatePrice(boolean calculatePrice) {
        CalculatePrice = calculatePrice;
    }

    public boolean isIncreaseInventory() {
        return IncreaseInventory;
    }

    public void setIncreaseInventory(boolean increaseInventory) {
        IncreaseInventory = increaseInventory;
    }

    public boolean isDecreaseInventory() {
        return DecreaseInventory;
    }

    public void setDecreaseInventory(boolean decreaseInventory) {
        DecreaseInventory = decreaseInventory;
    }

    public int getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getCreatingUserName() {
        return CreatingUserName;
    }

    public void setCreatingUserName(String creatingUserName) {
        CreatingUserName = creatingUserName;
    }

    public String getOrderCreatedTime() {
        return OrderCreatedTime;
    }

    public void setOrderCreatedTime(String orderCreatedTime) {
        OrderCreatedTime = orderCreatedTime;
    }

    public int getMenuItemPortionId() {
        return MenuItemPortionId;
    }

    public void setMenuItemPortionId(int menuItemPortionId) {
        MenuItemPortionId = menuItemPortionId;
    }

    public ArrayList<TicketOrderTax> getTaxes() {
        return TTaxes;
    }

    public void setTaxes(ArrayList<TicketOrderTax> taxes) {
        TTaxes = taxes;
    }

    public ArrayList<TicketOrderTag> getOTags() {
        return OTags;
    }

    public void setOTags(ArrayList<TicketOrderTag> OTags) {
        this.OTags = OTags;
    }

    public ArrayList<TicketOrderStates> getOStates() {
        return OStates;
    }

    public void setOStates(ArrayList<TicketOrderStates> OStates) {
        this.OStates = OStates;
    }
}
