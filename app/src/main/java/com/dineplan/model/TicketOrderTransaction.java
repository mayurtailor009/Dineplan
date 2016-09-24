package com.dineplan.model;

import java.io.Serializable;

/**
 * Created by Mayur on 24-09-2016.
 */
public class TicketOrderTransaction implements Serializable{

    private int transactionTypeId;
    private float amount;

    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
