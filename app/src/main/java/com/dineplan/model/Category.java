package com.dineplan.model;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 06/09/16.
 */
public class Category {


    public Category(){

    }


    public Category(String name){
        this.name=name;
    }

    private int id;
    private String name;
    private int sortOrder;
    private String imagePath;
    private ArrayList<MenuItem> menuItems;
    private ArrayList<OrderTagGroup> orderTagGroups;


    public ArrayList<OrderTagGroup> getOrderTagGroups() {
        return orderTagGroups;
    }

    public void setOrderTagGroups(ArrayList<OrderTagGroup> orderTagGroups) {
        this.orderTagGroups = orderTagGroups;
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

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
