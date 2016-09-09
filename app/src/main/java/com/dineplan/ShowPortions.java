package com.dineplan;

import com.dineplan.model.MenuItem;
import com.dineplan.model.OrderItem;

/**
 * Created by sandeepjoshi on 08/09/16.
 */
public interface ShowPortions {

    public void showPortions(MenuItem menuPortions);
    public void showMenu();

    void AddItem(OrderItem orderItem);

    void addFoodOrder(MenuItem menuItem);
}
