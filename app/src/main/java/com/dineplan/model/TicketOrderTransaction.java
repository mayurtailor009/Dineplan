package com.dineplan.model;

import java.io.Serializable;

/**
 * Created by Mayur on 24-09-2016.
 */
public class TicketOrderTransaction implements Serializable{


    // public int? Id { get; set; }

    private int TransactionTypeId;
    private float Amount;

    public int getTransactionTypeId() {
        return TransactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        TransactionTypeId = transactionTypeId;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }
}
