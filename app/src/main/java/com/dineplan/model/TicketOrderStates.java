package com.dineplan.model;

import java.io.Serializable;

/**
 * Created by Mayur on 24-09-2016.
 */
public class TicketOrderStates implements Serializable{

    private String D;
    private String OK;
    private String S;
    private String SN;
    private String SV;
    private int U;

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getOK() {
        return OK;
    }

    public void setOK(String OK) {
        this.OK = OK;
    }

    public String getS() {
        return S;
    }

    public void setS(String s) {
        S = s;
    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getSV() {
        return SV;
    }

    public void setSV(String SV) {
        this.SV = SV;
    }

    public int getU() {
        return U;
    }

    public void setU(int u) {
        U = u;
    }
}
