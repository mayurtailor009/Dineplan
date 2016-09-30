package com.dineplan.model;

import java.io.Serializable;

/**
 * Created by Mayur on 24-09-2016.
 */
public class TicketOrderPayment implements Serializable{

//public int? Id { get; set; }


    private int PaymentTypeId;
    private String TerminalName;
    private String PaymentCreatedTime;
    private float TenderedAmount;
    private float Amount;
    private String PaymentUserName;


    public int getPaymentTypeId() {
        return PaymentTypeId;
    }

    public void setPaymentTypeId(int paymentTypeId) {
        PaymentTypeId = paymentTypeId;
    }

    public String getTerminalName() {
        return TerminalName;
    }

    public void setTerminalName(String terminalName) {
        TerminalName = terminalName;
    }

    public String getPaymentUserName() {
        return PaymentUserName;
    }

    public void setPaymentUserName(String paymentUserName) {
        PaymentUserName = paymentUserName;
    }

    public String getPaymentCreatedTime() {
        return PaymentCreatedTime;
    }

    public void setPaymentCreatedTime(String paymentCreatedTime) {
        PaymentCreatedTime = paymentCreatedTime;
    }

    public float getTenderedAmount() {
        return TenderedAmount;
    }

    public void setTenderedAmount(float tenderedAmount) {
        TenderedAmount = tenderedAmount;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }
}
