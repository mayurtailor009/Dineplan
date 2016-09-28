package com.dineplan.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.dineplan.dbHandler.DbHandler;
import com.dineplan.model.OrderItem;
import com.dineplan.model.OrderTag;
import com.dineplan.model.TicketOrderPayment;
import com.dineplan.model.TicketOrderStates;
import com.dineplan.model.TicketOrderTag;
import com.dineplan.model.TicketOrderTax;
import com.dineplan.model.OrderTicket;
import com.dineplan.model.Payment;
import com.dineplan.model.PaymentType;
import com.dineplan.model.Tax;
import com.dineplan.model.TicketOrderItem;
import com.dineplan.model.TicketOrderTransaction;
import com.dineplan.model.TicketStates;
import com.dineplan.model.TransactionType;
import com.dineplan.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 20/09/16.
 */
public class PaymentTicketUtil {

    private SharedPreferences preferences;
    private ArrayList<OrderItem> orderItems;
    private Context context;
    private float totalAmount;
    private ArrayList<Payment> payments;
    private float taxAmount;
    private PaymentType paymentType;
    Gson gson;
    DbHandler dbHandler;
    private OrderTicket orderTicket;

    public PaymentTicketUtil(Context context, ArrayList<OrderItem> orderItems, PaymentType paymentType, float totalAmount, ArrayList<Payment> payments) {
        this.context = context;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
        this.payments = payments;
        this.paymentType = paymentType;
        dbHandler = new DbHandler(context);
        preferences = context.getSharedPreferences(Constants.PREF_NAME, context.MODE_PRIVATE);
        ;
    }


    public OrderTicket generateTicket() {
        orderTicket = new OrderTicket();
        gson = new Gson();
        User user = gson.fromJson(preferences.getString("user", "{}"), User.class);
        orderTicket.setLocationId(preferences.getInt("locationId", 0));
        orderTicket.setTenantId(user.getTenantId());
        orderTicket.setTicketCreatedTime(Utils.getCurrentUtcDate());
        orderTicket.setLastUpdateTime(orderTicket.getTicketCreatedTime());
        orderTicket.setLastOrderTime(orderItems.get(orderItems.size() - 1).getOrderTime());
        orderTicket.setLastPaymentTime(Utils.getCurrentUtcDate());
        orderTicket.setTotalAmount(totalAmount);
        orderTicket.setDepartment(orderItems.get(0).getDepartment().getName());
        orderTicket.setNote("");//from where we can get notes
        orderTicket.setLastModifiedUserName(user.getUserName());
        orderTicket.setTicketId(0);
        if (taxAmount == 0)
            orderTicket.setTaxIncluded(false);
        else
            orderTicket.setTaxIncluded(true);
        orderTicket.setWorkPeriodId(preferences.getInt("workPeriodId", 0));


        ArrayList<TicketOrderItem> ticketOrderItems = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            TicketOrderItem ticketOrderItem = new TicketOrderItem();
            ticketOrderItem.setDepartmentName(orderItem.getDepartment().getName());
            ticketOrderItem.setMenuItemId(orderItem.getItemId());
            ticketOrderItem.setMenuItemName(orderItem.getItemName());
            ticketOrderItem.setPortionName(orderItem.getMenuPortion().getPortionName());
            ticketOrderItem.setPrice(orderItem.getPrice());
            ticketOrderItem.setQuantity(orderItem.getQuantity());
            ticketOrderItem.setNote(orderItem.getNote());
            ticketOrderItem.setCreatingUserName(user.getUserName());
            ticketOrderItem.setOrderCreatedTime(orderItem.getOrderTime());
            ticketOrderItem.setMenuItemPortionId(orderItem.getMenuPortion().getId());

            ticketOrderItem.setTaxes(getOrderTax(orderItem.getTaxes()));
            ticketOrderItem.setOrderTags(getOrderTag(orderItem.getOrderTags()));
            ticketOrderItem.setOrderStates(getOrderState());

            ticketOrderItems.add(ticketOrderItem);
        }


        orderTicket.setPayments(getTicketPayement());

        orderTicket.setOrders(ticketOrderItems);

        orderTicket.setTicketStates(getTicketState());

        orderTicket.setTransactions(getTransactionList());

        return orderTicket;
    }

    public ArrayList<TicketOrderTax> getOrderTax(ArrayList<Tax> taxList) {
        ArrayList<TicketOrderTax> orderTaxList = new ArrayList<>();
        ArrayList<TransactionType> transactionTypes = dbHandler.getTransactionTypeList();
        int trasactionTypeId = 0;
        if (transactionTypes != null && transactionTypes.size() > 0) {
            for (TransactionType transactionType : transactionTypes) {
                if (transactionType.getName().equalsIgnoreCase("tax")) {
                    trasactionTypeId = transactionType.getId();
                }
            }
        }
        if (taxList != null) {
            for (Tax tax : taxList) {
                TicketOrderTax orderTax = new TicketOrderTax();
                orderTax.setTT(tax.getId());
                orderTax.setTN(tax.getName());
                orderTax.setRN(0);
                orderTax.setTR(tax.getPercentage());
                orderTax.setAT(trasactionTypeId);
                orderTaxList.add(orderTax);
            }
        }
        return orderTaxList;
    }

    public ArrayList<TicketOrderTag> getOrderTag(ArrayList<OrderTag> orderTags) {
        ArrayList<TicketOrderTag> orderTagList = new ArrayList<>();
        User user = gson.fromJson(preferences.getString("user", "{}"), User.class);

        if(orderTags!=null) {
            for (OrderTag orderTag : orderTags) {
                TicketOrderTag ticketOrderTag = new TicketOrderTag();

                ticketOrderTag.setOI(orderTag.getId());
                ticketOrderTag.setOK("");
                ticketOrderTag.setPR(orderTag.getPrice());
                //ticketOrderTag.setTN();// SET GROUP NAME
                ticketOrderTag.setTV(orderTag.getName());
                ticketOrderTag.setQ(1);
                ticketOrderTag.setUI(user.getUserId());

                orderTagList.add(ticketOrderTag);
            }
        }
        return orderTagList;
    }

    public ArrayList<TicketOrderStates> getOrderState() {
        User user = gson.fromJson(preferences.getString("user", "{}"), User.class);
        ArrayList<TicketOrderStates> orderStateList = new ArrayList<>();

        TicketOrderStates ticketOrderStates = new TicketOrderStates();
        ticketOrderStates.setD(Utils.getCurrentUtcDate());
        ticketOrderStates.setOK("0000000");
        ticketOrderStates.setS("SUBMITED");
        ticketOrderStates.setSN("STATUS");
        ticketOrderStates.setSV("");
        ticketOrderStates.setU(user.getUserId());

        orderStateList.add(ticketOrderStates);

        return orderStateList;
    }

    public ArrayList<TicketStates> getTicketState() {
        ArrayList<TicketStates> ticketStateList = new ArrayList<>();

        TicketStates ticketStates = new TicketStates();
        ticketStates.setD(Utils.getCurrentUtcDate());
        ticketStates.setS("PAID");
        ticketStates.setSN("STATUS");
        ticketStates.setSV("");
        ticketStateList.add(ticketStates);

        return ticketStateList;
    }

    public ArrayList<TicketOrderPayment> getTicketPayement() {
        ArrayList<TicketOrderPayment> ticketPaymentList = new ArrayList<>();
        User user = gson.fromJson(preferences.getString("user", "{}"), User.class);

        TicketOrderPayment ticketOrderPayment = new TicketOrderPayment();
        ticketOrderPayment.setPaymentTypeId(paymentType.getId());
        ticketOrderPayment.setTenderedAmount(totalAmount);
        ticketOrderPayment.setTerminalName("Server");
        ticketOrderPayment.setPaymentUserName(user.getUserName());
        ticketOrderPayment.setPaymentCreatedTime(Utils.getCurrentUtcDate());
        ticketOrderPayment.setAmount(totalAmount);

        ticketPaymentList.add(ticketOrderPayment);


        return ticketPaymentList;
    }

    public ArrayList<TicketOrderTransaction> getTransactionList() {
        ArrayList<TicketOrderTransaction> transactionList = new ArrayList<>();
        float tax = 0;
        for (OrderItem orderItem : orderItems) {
            tax += orderItem.getTaxAmount();
        }
        ArrayList<TransactionType> transactionTypes = dbHandler.getTransactionTypeList();
        if (transactionTypes != null) {
            for (TransactionType transactionType : transactionTypes) {
                TicketOrderTransaction ticketOrderTransaction = new TicketOrderTransaction();

                ticketOrderTransaction.setTransactionTypeId(transactionType.getId());

                if (transactionType.getName().equalsIgnoreCase("Sales")) {
                    ticketOrderTransaction.setAmount(totalAmount - tax);
                } else if (transactionType.getName().equalsIgnoreCase("Tax")) {
                    ticketOrderTransaction.setAmount(tax);
                } else if (transactionType.getName().equalsIgnoreCase("payment")) {
                    ticketOrderTransaction.setAmount(totalAmount);
                }
                transactionList.add(ticketOrderTransaction);
            }
        }

        return transactionList;
    }
}
