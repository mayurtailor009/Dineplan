package com.dineplan.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 07/09/16.
 */
public class OrderTag implements Serializable {

    private int sortOrder;
    private String name;
    private int id;
    private int price;
    private int tagGroupId;
    private ArrayList<OrderTag> orderTags;


    public OrderTag(){

    }

    public OrderTag(String name){
        this.name=name;
    }

    public ArrayList<OrderTag> getOrderTags() {
        return orderTags;
    }

    public void setOrderTags(ArrayList<OrderTag> orderTags) {
        this.orderTags = orderTags;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTagGroupId() {
        return tagGroupId;
    }

    public void setTagGroupId(int tagGroupId) {
        this.tagGroupId = tagGroupId;
    }
}
