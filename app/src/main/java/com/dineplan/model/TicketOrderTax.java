package com.dineplan.model;

import java.io.Serializable;

/**
 * Created by Mayur on 24-09-2016.
 */
public class TicketOrderTax implements Serializable{

    private int AT;
    private int RN;
    private String TN;
    private float TR;
    private int TT;


    public int getAT() {
        return AT;
    }

    public void setAT(int AT) {
        this.AT = AT;
    }

    public int getRN() {
        return RN;
    }

    public void setRN(int RN) {
        this.RN = RN;
    }

    public String getTN() {
        return TN;
    }

    public void setTN(String TN) {
        this.TN = TN;
    }

    public float getTR() {
        return TR;
    }

    public void setTR(float TR) {
        this.TR = TR;
    }

    public int getTT() {
        return TT;
    }

    public void setTT(int TT) {
        this.TT = TT;
    }
}
