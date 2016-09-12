package com.dineplan.model;

import java.io.Serializable;

/**
 * Created by Mayur on 12-09-2016.
 */
public class PaymentType implements Serializable{

    private int id;
    private String name;
    private boolean acceptChange;
    private int sortOrder;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isAcceptChange() {
        return acceptChange;
    }

    public void setAcceptChange(boolean acceptChange) {
        this.acceptChange = acceptChange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
