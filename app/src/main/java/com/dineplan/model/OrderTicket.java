package com.dineplan.model;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 20/09/16.
 */
public class OrderTicket {


    //TicketTypeName  , TicketTags,TicketLogs
    // public virtual List<TicketTagValue> TTags { get; set; }
    // public virtual bool PreOrder { get; set; }
    //public virtual string TicketEntities { get; set; }

    public OrderTicket(){
        IsClosed=true;
        IsLocked=false;
    }

    private int Id;
    private int LocationId;
    private int TicketId;
    private int TenantId;
    private long TicketNumber;
    private String TicketCreatedTime;
    private String LastUpdateTime;
    private String LastOrderTime;
    private String LastPaymentTime;
    private boolean IsClosed;
    private boolean IsLocked;
    private float RemainingAmount;
    private float TotalAmount;//Total ammount of order with the taxes
    private String DepartmentName;
    private String Note;
    private String LastModifiedUserName;
    private boolean TaxIncluded;
    private int WorkPeriodId;



    private ArrayList<TicketOrderItem> Orders   ;
    private ArrayList<TicketOrderPayment> Payments;

    private ArrayList<TicketStates> TStates;


    private ArrayList<TicketOrderTransaction> Transactions;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int locationId) {
        LocationId = locationId;
    }

    public int getTicketId() {
        return TicketId;
    }

    public void setTicketId(int ticketId) {
        TicketId = ticketId;
    }

    public int getTenantId() {
        return TenantId;
    }

    public void setTenantId(int tenantId) {
        TenantId = tenantId;
    }

    public long getTicketNumber() {
        return TicketNumber;
    }

    public void setTicketNumber(long ticketNumber) {
        TicketNumber = ticketNumber;
    }

    public String getTicketCreatedTime() {
        return TicketCreatedTime;
    }

    public void setTicketCreatedTime(String ticketCreatedTime) {
        TicketCreatedTime = ticketCreatedTime;
    }

    public String getLastUpdateTime() {
        return LastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        LastUpdateTime = lastUpdateTime;
    }

    public String getLastOrderTime() {
        return LastOrderTime;
    }

    public void setLastOrderTime(String lastOrderTime) {
        LastOrderTime = lastOrderTime;
    }

    public String getLastPaymentTime() {
        return LastPaymentTime;
    }

    public void setLastPaymentTime(String lastPaymentTime) {
        LastPaymentTime = lastPaymentTime;
    }

    public boolean isClosed() {
        return IsClosed;
    }

    public void setClosed(boolean closed) {
        IsClosed = closed;
    }

    public boolean isLocked() {
        return IsLocked;
    }

    public void setLocked(boolean locked) {
        IsLocked = locked;
    }

    public float getRemainingAmount() {
        return RemainingAmount;
    }

    public void setRemainingAmount(float remainingAmount) {
        RemainingAmount = remainingAmount;
    }

    public float getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getLastModifiedUserName() {
        return LastModifiedUserName;
    }

    public void setLastModifiedUserName(String lastModifiedUserName) {
        LastModifiedUserName = lastModifiedUserName;
    }

    public boolean isTaxIncluded() {
        return TaxIncluded;
    }

    public void setTaxIncluded(boolean taxIncluded) {
        TaxIncluded = taxIncluded;
    }

    public int getWorkPeriodId() {
        return WorkPeriodId;
    }

    public void setWorkPeriodId(int workPeriodId) {
        WorkPeriodId = workPeriodId;
    }

    public ArrayList<TicketOrderItem> getOrders() {
        return Orders;
    }

    public void setOrders(ArrayList<TicketOrderItem> orders) {
        Orders = orders;
    }

    public ArrayList<TicketOrderPayment> getPayments() {
        return Payments;
    }

    public void setPayments(ArrayList<TicketOrderPayment> payments) {
        Payments = payments;
    }

    public ArrayList<TicketStates> getTStates() {
        return TStates;
    }

    public void setTStates(ArrayList<TicketStates> TStates) {
        this.TStates = TStates;
    }

    public ArrayList<TicketOrderTransaction> getTransactions() {
        return Transactions;
    }

    public void setTransactions(ArrayList<TicketOrderTransaction> transactions) {
        Transactions = transactions;
    }
}
