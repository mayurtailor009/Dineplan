package com.dineplan.model;

import java.io.Serializable;

/**
 * Created by Mayur on 12-09-2016.
 */
public class TransactionType implements Serializable{

    private int id;
    private String name;

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
