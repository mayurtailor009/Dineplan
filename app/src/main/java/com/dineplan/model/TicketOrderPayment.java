package com.dineplan.model;

import java.io.Serializable;

/**
 * Created by Mayur on 24-09-2016.
 */
public class TicketOrderPayment implements Serializable{

    private int paymentTypeId;
    private float tenderedAmount;
    private String terminalName;
    private String paymentUserName;
    private String paymentCreatedTime;
    private float amount;

    public int getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(int paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public float getTenderedAmount() {
        return tenderedAmount;
    }

    public void setTenderedAmount(float tenderedAmount) {
        this.tenderedAmount = tenderedAmount;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getPaymentUserName() {
        return paymentUserName;
    }

    public void setPaymentUserName(String paymentUserName) {
        this.paymentUserName = paymentUserName;
    }

    public String getPaymentCreatedTime() {
        return paymentCreatedTime;
    }

    public void setPaymentCreatedTime(String paymentCreatedTime) {
        this.paymentCreatedTime = paymentCreatedTime;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
