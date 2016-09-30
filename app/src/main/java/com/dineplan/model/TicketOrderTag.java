package com.dineplan.model;

import java.io.Serializable;

/**
 * Created by Mayur on 24-09-2016.
 */
public class TicketOrderTag implements Serializable{

    public int OI;
    public String OK;
    public float PR;
    public String TN;
    public String TV;
    public int Q;
    public int UI;



    public int getOI() {
        return OI;
    }

    public void setOI(int OI) {
        this.OI = OI;
    }

    public String getOK() {
        return OK;
    }

    public void setOK(String OK) {
        this.OK = OK;
    }

    public float getPR() {
        return PR;
    }

    public void setPR(float PR) {
        this.PR = PR;
    }

    public String getTN() {
        return TN;
    }

    public void setTN(String TN) {
        this.TN = TN;
    }

    public String getTV() {
        return TV;
    }

    public void setTV(String TV) {
        this.TV = TV;
    }

    public int getQ() {
        return Q;
    }

    public void setQ(int q) {
        Q = q;
    }

    public int getUI() {
        return UI;
    }

    public void setUI(int UI) {
        this.UI = UI;
    }
}
