package com.dineplan.model;

import java.io.Serializable;

/**
 * Created by mayur.tailor on 13-09-2016.
 */
public class Department implements Serializable{
    private int id;
    private String name;
    private boolean isSelected;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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
}
