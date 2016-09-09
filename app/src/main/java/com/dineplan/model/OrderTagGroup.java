package com.dineplan.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 07/09/16.
 */
public class OrderTagGroup implements Serializable {
    private int id;
    private String name;
    private int minSelectItems;
    private int maxSelectItems;
    private ArrayList<OrderTag> orderTags;
    private int sortOrder;
    private int OrderTagId;
    private boolean addPriceToOrder;


    public int getOrderTagId() {
        return OrderTagId;
    }

    public void setOrderTagId(int orderTagId) {
        OrderTagId = orderTagId;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isAddPriceToOrder() {
        return addPriceToOrder;
    }

    public void setAddPriceToOrder(boolean addPriceToOrder) {
        this.addPriceToOrder = addPriceToOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinSelectItems() {
        return minSelectItems;
    }

    public void setMinSelectItems(int minSelectItems) {
        this.minSelectItems = minSelectItems;
    }

    public int getMaxSelectItems() {
        return maxSelectItems;
    }

    public void setMaxSelectItems(int maxSelectItems) {
        this.maxSelectItems = maxSelectItems;
    }

    public ArrayList<OrderTag> getOrderTags() {
        return orderTags;
    }

    public void setOrderTags(ArrayList<OrderTag> orderTags) {
        this.orderTags = orderTags;
    }
}
