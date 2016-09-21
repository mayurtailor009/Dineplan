package com.dineplan.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 06/09/16.
 */
public class MenuItem implements Serializable{


    private int id;
    private String name;
    private String aliasCode;
    private String aliasName;
    private String imagePath;
    private String description;
    private boolean isFavorite;
    private int sortOrder;
    private String barCode;
    private int categoryId;
    private float price;
    private ArrayList<OrderTagGroup> orderTagGroups;
    private ArrayList<Tax> taxes;


    public ArrayList<Tax> getTaxes() {
        return taxes;
    }

    public void setTaxes(ArrayList<Tax> taxes) {
        this.taxes = taxes;
    }

    public ArrayList<OrderTagGroup> getOrderTagGroups() {
        return orderTagGroups;
    }


    public void setOrderTagGroups(ArrayList<OrderTagGroup> orderTagGroups) {
        this.orderTagGroups = orderTagGroups;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    private ArrayList<MenuPortion> menuPortions;


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

    public String getAliasCode() {
        return aliasCode;
    }

    public void setAliasCode(String aliasCode) {
        this.aliasCode = aliasCode;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public ArrayList<MenuPortion> getMenuPortions() {
        return menuPortions;
    }

    public void setMenuPortions(ArrayList<MenuPortion> menuPortions) {
        this.menuPortions = menuPortions;
    }
}
