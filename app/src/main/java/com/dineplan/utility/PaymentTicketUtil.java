package com.dineplan.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.dineplan.model.OrderItem;
import com.dineplan.model.OrderTicket;
import com.dineplan.model.Payment;
import com.dineplan.model.PaymentType;
import com.dineplan.model.TicketOrderItem;
import com.dineplan.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 20/09/16.
 */
public class PaymentTicketUtil{

    private SharedPreferences preferences;
    private ArrayList<OrderItem> orderItems;
    private Context context;
    private float totalAmount;
    private ArrayList<Payment> payments;
    private float taxAmount;
    Gson gson;
    private OrderTicket orderTicket;
    public PaymentTicketUtil(Context context, ArrayList<OrderItem> orderItems,PaymentType paymentType,float totalAmount,ArrayList<Payment> payments){
        this.context=context;
        this.totalAmount=totalAmount;
        this.orderItems=orderItems;
        this.payments=payments;
        preferences= context.getSharedPreferences(Constants.PREF_NAME,context.MODE_PRIVATE);;
    }


    public OrderTicket generateTicket(){
        orderTicket=new OrderTicket();
        gson=new Gson();
        User user=gson.fromJson(preferences.getString("user","{}"),User.class);
        orderTicket.setLocationId(preferences.getInt("locationId",0));
        orderTicket.setTenantId(user.getTenantId());
        orderTicket.setTicketCreatedTime(Utils.getCurrentUtcDate());
        orderTicket.setLastUpdateTime(orderTicket.getTicketCreatedTime());
        orderTicket.setLastOrderTime(orderItems.get(orderItems.size()-1).getOrderTime());
        orderTicket.setLastPaymentTime(Utils.getCurrentUtcDate());
        orderTicket.setTotalAmount(totalAmount);
        orderTicket.setDepartment(orderItems.get(0).getDepartment().getName());
        orderTicket.setNote("");//from where we can get notes
        orderTicket.setLastModifiedUserName(user.getUserName());
        if(taxAmount==0)
            orderTicket.setTaxIncluded(false);
        else
            orderTicket.setTaxIncluded(true);
        orderTicket.setWorkPeriodId(preferences.getInt("workPeriodId",0));


        ArrayList<TicketOrderItem> ticketOrderItems=new ArrayList<>();
        for(OrderItem orderItem:orderItems){
            TicketOrderItem ticketOrderItem=new TicketOrderItem();
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
         }




        orderTicket.setPayments(payments);

        orderTicket.setOrders(ticketOrderItems);




        return  orderTicket;
    }


}
