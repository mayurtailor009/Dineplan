package com.dineplan.model;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 20/09/16.
 */
public class OrderTicket {


    public OrderTicket(){
        isClosed=true;
        isLocked=false;
    }

    private int id;
    private int locationId;
    private int ticketId;
    private int tenantId;
    private int ticketNumber;
    private String ticketCreatedTime;
    private String lastUpdateTime;
    private String lastOrderTime;
    private String lastPaymentTime;
    private boolean isClosed;
    private boolean isLocked;
    private float remainingAmount;
    private float totalAmount;//Total ammount of order with the taxes
    private String department;
    private String note;
    private String lastModifiedUserName;
    private boolean taxIncluded;
    private int WorkPeriodId;



    private ArrayList<TicketOrderItem> orders;
    private ArrayList<Payment> payments;


    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public void setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }

    public ArrayList<TicketOrderItem> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<TicketOrderItem> orders) {
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getTicketCreatedTime() {
        return ticketCreatedTime;
    }

    public void setTicketCreatedTime(String ticketCreatedTime) {
        this.ticketCreatedTime = ticketCreatedTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastOrderTime() {
        return lastOrderTime;
    }

    public void setLastOrderTime(String lastOrderTime) {
        this.lastOrderTime = lastOrderTime;
    }

    public String getLastPaymentTime() {
        return lastPaymentTime;
    }

    public void setLastPaymentTime(String lastPaymentTime) {
        this.lastPaymentTime = lastPaymentTime;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public float getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(float remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLastModifiedUserName() {
        return lastModifiedUserName;
    }

    public void setLastModifiedUserName(String lastModifiedUserName) {
        this.lastModifiedUserName = lastModifiedUserName;
    }

    public boolean isTaxIncluded() {
        return taxIncluded;
    }

    public void setTaxIncluded(boolean taxIncluded) {
        this.taxIncluded = taxIncluded;
    }

    public int getWorkPeriodId() {
        return WorkPeriodId;
    }

    public void setWorkPeriodId(int workPeriodId) {
        WorkPeriodId = workPeriodId;
    }
}
